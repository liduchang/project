package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.constant.ResourceTypeEnum;
import cn.javaboy.admin.common.domain.sys.dto.ResourceFunctionDTO;
import cn.javaboy.admin.common.domain.sys.dto.ResourceMenuDTO;
import cn.javaboy.admin.common.domain.sys.dto.ValidateList;
import cn.javaboy.admin.common.domain.sys.entity.ResourceEntity;
import cn.javaboy.admin.common.domain.sys.vo.ResourceFunctionVO;
import cn.javaboy.admin.common.domain.sys.vo.ResourceMenuVO;
import cn.javaboy.admin.common.domain.sys.vo.ResourceRequestUrlVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.ResourceResponseCodeConst;
import cn.javaboy.admin.dao.sys.ResourceDao;
import cn.javaboy.admin.dao.sys.RoleResourceDao;
import cn.javaboy.admin.service.sys.IResourceRequestUrlService;
import cn.javaboy.admin.service.sys.IResourceService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 13:54
 */
@Service
public class ResourceService implements IResourceService {

    @Autowired
    private IResourceRequestUrlService resourceRequestUrlService;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private RoleResourceDao roleResourceDao;

    /**
     * 获取系统所有请求路径
     *
     * @return
     */
    public ResponseDTO<List<ResourceRequestUrlVO>> getPrivilegeUrlDTOList() {
        List<ResourceRequestUrlVO> privilegeUrlList = resourceRequestUrlService.getPrivilegeList();
        return ResponseDTO.succData(privilegeUrlList);
    }

    /**
     * 批量保存权限菜单项
     *
     * @param menuList
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> menuBatchSave(List<ResourceMenuDTO> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return ResponseDTO.succ();
        }
        //使用前端发送权限的排序
        for (int i = 0; i < menuList.size(); i++) {
            menuList.get(i).setSort(i);
        }

        List<ResourceEntity> privilegeList = resourceDao.selectByExcludeType(ResourceTypeEnum.POINTS.getValue());
        //若数据库无数据 直接全部保存
        if (CollectionUtils.isEmpty(privilegeList)) {
            List<ResourceEntity> menuSaveEntity = this.buildPrivilegeMenuEntity(menuList);
            resourceDao.batchInsert(menuSaveEntity);
            return ResponseDTO.succ();
        }
        //处理需更新的菜单项
        Map<String, ResourceMenuDTO> storageMap = menuList.stream().collect(Collectors.toMap(ResourceMenuDTO::getMenuKey, e -> e));
        Set<String> menuKeyList = storageMap.keySet();
        List<ResourceEntity> updatePrivilegeList = privilegeList.stream().filter(e -> menuKeyList.contains(e.getKey())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(updatePrivilegeList)) {
            this.rebuildPrivilegeMenuEntity(storageMap, updatePrivilegeList);
            resourceDao.batchUpdate(updatePrivilegeList);
        }
        //处理需删除的菜单项
        List<String> delKeyList = privilegeList.stream().filter(e -> !menuKeyList.contains(e.getKey())).map(ResourceEntity::getKey).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(delKeyList)) {
            resourceDao.delByKeyList(delKeyList);
            //处理需删除的功能点
            resourceDao.delByParentKeyList(delKeyList);
            roleResourceDao.deleteByPrivilegeKey(delKeyList);
        }

        //处理需新增的菜单项
        List<String> dbKeyList = privilegeList.stream().map(ResourceEntity::getKey).collect(Collectors.toList());
        List<ResourceMenuDTO> addPrivilegeList = menuList.stream().filter(e -> !dbKeyList.contains(e.getMenuKey())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(addPrivilegeList)) {
            List<ResourceEntity> menuAddEntity = this.buildPrivilegeMenuEntity(addPrivilegeList);
            resourceDao.batchInsert(menuAddEntity);
        }
        return ResponseDTO.succ();
    }

    /**
     * 构建权限菜单项类别
     *
     * @param menuList
     * @return
     */
    private List<ResourceEntity> buildPrivilegeMenuEntity(List<ResourceMenuDTO> menuList) {
        List<ResourceEntity> privilegeList = Lists.newArrayList();
        ResourceEntity privilegeEntity;
        for (ResourceMenuDTO menuDTO : menuList) {
            privilegeEntity = new ResourceEntity();
            privilegeEntity.setKey(menuDTO.getMenuKey());
            privilegeEntity.setName(menuDTO.getMenuName());
            privilegeEntity.setParentKey(menuDTO.getParentKey());
            privilegeEntity.setType(menuDTO.getType());
            privilegeEntity.setSort(menuDTO.getSort());
            privilegeEntity.setUrl(menuDTO.getUrl());
            privilegeList.add(privilegeEntity);
        }
        return privilegeList;
    }

    /**
     * 更新权限菜单项
     *
     * @param menuMap
     * @param menuEntityList
     */
    private void rebuildPrivilegeMenuEntity(Map<String, ResourceMenuDTO> menuMap, List<ResourceEntity> menuEntityList) {
        for (ResourceEntity menuEntity : menuEntityList) {
            ResourceMenuDTO menuDTO = menuMap.get(menuEntity.getKey());
            menuEntity.setName(menuDTO.getMenuName());
            menuEntity.setParentKey(menuDTO.getParentKey());
            menuEntity.setType(menuDTO.getType());
            menuEntity.setSort(menuDTO.getSort());
        }

    }

    /**
     * 查询所有的权限菜单
     *
     * @return
     */
    public ResponseDTO<List<ResourceMenuVO>> menuQueryAll() {
        List<ResourceEntity> privilegeEntityList = resourceDao.selectByType(ResourceTypeEnum.MENU.getValue());
        if (CollectionUtils.isEmpty(privilegeEntityList)) {
            return ResponseDTO.succData(Lists.newArrayList());
        }

        List<ResourceMenuVO> voList = privilegeEntityList.stream().map(e -> {
            ResourceMenuVO vo = new ResourceMenuVO();
            vo.setMenuKey(e.getKey());
            vo.setMenuName(e.getName());
            vo.setParentKey(e.getParentKey());
            vo.setSort(e.getSort());
            vo.setUrl(e.getUrl());
            return vo;
        }).collect(Collectors.toList());

        return ResponseDTO.succData(voList);
    }


    /**
     * 保存更新功能点
     *
     * @param privilegeFunctionDTO
     * @return
     */
    public ResponseDTO<String> functionSaveOrUpdate(ResourceFunctionDTO privilegeFunctionDTO) {
        String functionKey = privilegeFunctionDTO.getFunctionKey();
        ResourceEntity functionEntity = resourceDao.selectByKey(functionKey);
        if (functionEntity == null) {
            return ResponseDTO.wrap(ResourceResponseCodeConst.POINT_NOT_EXIST);
        }
        functionEntity.setUrl(privilegeFunctionDTO.getUrl());
        functionEntity.setName(privilegeFunctionDTO.getFunctionName());
        functionEntity.setParentKey(privilegeFunctionDTO.getMenuKey());
        functionEntity.setSort(privilegeFunctionDTO.getSort());
        resourceDao.updateById(functionEntity);

        return ResponseDTO.succ();
    }

    /**
     * 查询功能点
     *
     * @param menuKey
     * @return
     */
    public ResponseDTO<List<ResourceFunctionVO>> functionQuery(String menuKey) {
        List<ResourceEntity> functionPrivilegeList = resourceDao.selectByParentKey(menuKey);
        if (CollectionUtils.isEmpty(functionPrivilegeList)) {
            return ResponseDTO.succData(Lists.newArrayList());
        }
        List<ResourceFunctionVO> functionList = Lists.newArrayList();
        for (ResourceEntity functionEntity : functionPrivilegeList) {
            ResourceFunctionVO functionDTO = new ResourceFunctionVO();
            functionDTO.setFunctionKey(functionEntity.getKey());
            functionDTO.setFunctionName(functionEntity.getName());
            functionDTO.setMenuKey(functionEntity.getParentKey());
            functionDTO.setUrl(functionEntity.getUrl());
            functionDTO.setSort(functionEntity.getSort());
            functionList.add(functionDTO);
        }
        return ResponseDTO.succData(functionList);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> batchSaveFunctionList(ValidateList<ResourceFunctionDTO> functionList) {
        String menuKey = functionList.get(0).getMenuKey();
        ResourceEntity privilegeEntity = resourceDao.selectByKey(menuKey);
        if (privilegeEntity == null) {
            return ResponseDTO.wrap(ResourceResponseCodeConst.MENU_NOT_EXIST);
        }

        List<String> functionKeyList = functionList.stream().map(ResourceFunctionDTO::getFunctionKey).collect(Collectors.toList());

        //数据库中存在的数据
        List<ResourceEntity> existFunctionList = resourceDao.selectByKeyList(functionKeyList);
        //校验是否parent key重复
        boolean parentKeyExist = existFunctionList.stream().anyMatch(e -> !menuKey.equals(e.getParentKey()));
        if(parentKeyExist){
            return ResponseDTO.wrap(ResourceResponseCodeConst.ROUTER_KEY_NO_REPEAT);
        }

        existFunctionList = resourceDao.selectByParentKey(menuKey);
        Map<String, ResourceEntity> privilegeEntityMap = existFunctionList.stream().collect(Collectors.toMap(ResourceEntity::getKey, e -> e));
        //如果没有，则保存全部
        if (existFunctionList.isEmpty()) {
            List<ResourceEntity> privilegeEntityList = functionList.stream().map(e -> function2Privilege(e)).collect(Collectors.toList());
            resourceDao.batchInsert(privilegeEntityList);
            return ResponseDTO.succ();
        }

        Set<String> functionKeySet = functionList.stream().map(ResourceFunctionDTO::getFunctionKey).collect(Collectors.toSet());
        //删除的
        List<Long> deleteIdList = existFunctionList.stream().filter(e -> !functionKeySet.contains(e.getKey())).map(ResourceEntity::getId).collect(Collectors.toList());
        List<String> deleteKeyList = existFunctionList.stream().filter(e -> !functionKeySet.contains(e.getKey())).map(ResourceEntity::getKey).collect(Collectors.toList());
        if (!deleteIdList.isEmpty()) {
            resourceDao.deleteBatchIds(deleteIdList);
            roleResourceDao.deleteByPrivilegeKey(deleteKeyList);
        }

        //需要更新的
        ArrayList<ResourceEntity> batchUpdateList = Lists.newArrayList();
        for (ResourceFunctionDTO privilegeFunctionDTO : functionList) {
            ResourceEntity existPrivilege = privilegeEntityMap.get(privilegeFunctionDTO.getFunctionKey());
            if (existPrivilege != null) {
                existPrivilege.setSort(privilegeFunctionDTO.getSort());
                existPrivilege.setName(privilegeFunctionDTO.getFunctionName());
                batchUpdateList.add(existPrivilege);
            }
        }

        if (!batchUpdateList.isEmpty()) {
            resourceDao.batchUpdate(batchUpdateList);
        }

        //新增的
        List<ResourceEntity> batchInsertList = functionList.stream()
                .filter(e -> !privilegeEntityMap.containsKey(e.getFunctionKey()))
                .map(e -> function2Privilege(e))
                .collect(Collectors.toList());

        if (!batchInsertList.isEmpty()) {
            resourceDao.batchInsert(batchInsertList);
        }

        return ResponseDTO.succ();
    }

    private ResourceEntity function2Privilege(ResourceFunctionDTO privilegeFunction) {
        ResourceEntity privilegeEntity = new ResourceEntity();
        privilegeEntity.setKey(privilegeFunction.getFunctionKey());
        privilegeEntity.setName(privilegeFunction.getFunctionName());
        privilegeEntity.setParentKey(privilegeFunction.getMenuKey());
        privilegeEntity.setType(ResourceTypeEnum.POINTS.getValue());
        privilegeEntity.setSort(privilegeFunction.getSort());
        privilegeEntity.setUrl("");
        return privilegeEntity;
    }

}
