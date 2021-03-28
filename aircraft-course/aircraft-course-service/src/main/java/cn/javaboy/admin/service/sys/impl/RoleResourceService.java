package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.sys.dto.RoleResourceDTO;
import cn.javaboy.admin.common.domain.sys.dto.RoleResourceSimpleDTO;
import cn.javaboy.admin.common.domain.sys.dto.RoleResourceTreeVO;
import cn.javaboy.admin.common.domain.sys.entity.ResourceEntity;
import cn.javaboy.admin.common.domain.sys.entity.RoleEntity;
import cn.javaboy.admin.common.domain.sys.entity.RoleResourceEntity;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.RoleResponseCodeConst;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.dao.sys.ResourceDao;
import cn.javaboy.admin.dao.sys.RoleDao;
import cn.javaboy.admin.dao.sys.RoleResourceDao;
import cn.javaboy.admin.service.sys.IResourceUserService;
import cn.javaboy.admin.service.sys.IRoleResourceService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * [ 用户权限 ]
 * @author liduchang
 */
@Service
public class RoleResourceService implements IRoleResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleResourceDao roleResourceDao;

    @Autowired
    private IResourceUserService resourceUserService;

    /**
     * 更新角色权限
     *
     * @param updateDTO
     * @return ResponseDTO
     */
    public ResponseDTO<String> updateRoleResource(RoleResourceDTO updateDTO) {
        Long roleId = updateDTO.getRoleId();
        RoleEntity roleEntity = roleDao.selectById(roleId);
        if (null == roleEntity) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NOT_EXISTS);
        }
        roleResourceDao.deleteByRoleId(roleId);
        List<RoleResourceEntity> roleResourceList = Lists.newArrayList();
        RoleResourceEntity RoleResourceEntity;
        for (String privilegeKey : updateDTO.getPrivilegeKeyList()) {
            RoleResourceEntity = new RoleResourceEntity();
            RoleResourceEntity.setRoleId(roleId);
            RoleResourceEntity.setPrivilegeKey(privilegeKey);
            roleResourceList.add(RoleResourceEntity);
        }
        roleResourceDao.batchInsert(roleResourceList);
        resourceUserService.updateOnlineUserResourceByRoleId(roleId);
        return ResponseDTO.succ();
    }

    public ResponseDTO<RoleResourceTreeVO> listResourceByRoleId(Long roleId) {
        RoleResourceTreeVO roleResourceTreeDTO = new RoleResourceTreeVO();
        roleResourceTreeDTO.setRoleId(roleId);

        List<ResourceEntity> privilegeDTOList = resourceDao.selectAll();
        if (CollectionUtils.isEmpty(privilegeDTOList)) {
            roleResourceTreeDTO.setPrivilege(Lists.newArrayList());
            roleResourceTreeDTO.setSelectedKey(Lists.newArrayList());
            return ResponseDTO.succData(roleResourceTreeDTO);
        }
        //构造权限树
        List<RoleResourceSimpleDTO> privilegeList = this.buildResourceTree(privilegeDTOList);
        //设置选中状态
        List<ResourceEntity> RoleResourceEntityList = roleResourceDao.listByRoleId(roleId);
        List<String> privilegeKeyList = RoleResourceEntityList.stream().map(e -> e.getKey()).collect(Collectors.toList());
        roleResourceTreeDTO.setPrivilege(privilegeList);
        roleResourceTreeDTO.setSelectedKey(privilegeKeyList);
        return ResponseDTO.succData(roleResourceTreeDTO);
    }

    private List<RoleResourceSimpleDTO> buildResourceTree(List<ResourceEntity> resourceEntityList) {
        List<RoleResourceSimpleDTO> resourceTree = Lists.newArrayList();
        List<ResourceEntity> rootResource = resourceEntityList.stream().filter(e -> e.getParentKey() == null).collect(Collectors.toList());
        rootResource.sort(Comparator.comparing(ResourceEntity::getSort));
        if (CollectionUtils.isEmpty(rootResource)) {
            return resourceTree;
        }
        resourceTree = JBBeanUtil.copyList(rootResource, RoleResourceSimpleDTO.class);
        resourceTree.forEach(e -> e.setChildren(Lists.newArrayList()));
        this.buildChildResourceList(resourceEntityList, resourceTree);
        return resourceTree;
    }

    private void buildChildResourceList(List<ResourceEntity> resourceEntityList, List<RoleResourceSimpleDTO> parentMenuList) {
        List<String> parentKeyList = parentMenuList.stream().map(RoleResourceSimpleDTO:: getKey).collect(Collectors.toList());
        List<ResourceEntity> childEntityList = resourceEntityList.stream().filter(e -> parentKeyList.contains(e.getParentKey())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childEntityList)) {
            return;
        }
        Map<String, List<ResourceEntity>> listMap = childEntityList.stream().collect(Collectors.groupingBy(ResourceEntity :: getParentKey));
        for (RoleResourceSimpleDTO roleResourceSimpleDTO : parentMenuList) {
            String key = roleResourceSimpleDTO.getKey();
            List<ResourceEntity> privilegeEntities = listMap.get(key);
            if (CollectionUtils.isEmpty(privilegeEntities)) {
                continue;
            }
            privilegeEntities.sort(Comparator.comparing(ResourceEntity::getSort));
            List<RoleResourceSimpleDTO> resourceList = JBBeanUtil.copyList(privilegeEntities, RoleResourceSimpleDTO.class);
            resourceList.forEach(e -> e.setChildren(Lists.newArrayList()));
            roleResourceSimpleDTO.setChildren(resourceList);
            this.buildChildResourceList(resourceEntityList, resourceList);
        }
    }
}
