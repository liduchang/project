package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.constant.JudgeEnum;
import cn.javaboy.admin.common.domain.constant.ResourceTypeEnum;
import cn.javaboy.admin.common.domain.constant.SysParamEnum;
import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import cn.javaboy.admin.common.domain.sys.dto.SysParamDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserDTO;
import cn.javaboy.admin.common.domain.sys.entity.ResourceEntity;
import cn.javaboy.admin.common.exception.BusinessException;
import cn.javaboy.admin.common.utils.JBStringUtil;
import cn.javaboy.admin.dao.sys.ResourceDao;
import cn.javaboy.admin.dao.sys.RoleResourceDao;
import cn.javaboy.admin.dao.sys.RoleUserDao;
import cn.javaboy.admin.service.sys.IResourceUserService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 16:17
 */
@Service
public class ResourceUserService implements IResourceUserService{

    /**
     * 后台用户权限缓存 <id,<controllerName,methodName></>></>
     */
    private ConcurrentMap<Long, Map<String, List<String>>> userResources = new ConcurrentLinkedHashMap.Builder<Long, Map<String, List<String>>>().maximumWeightedCapacity(1000).build();
    private ConcurrentMap<Long, List<ResourceEntity>> userResourceListMap = new ConcurrentLinkedHashMap.Builder<Long, List<ResourceEntity>>().maximumWeightedCapacity(1000).build();

    @Autowired
    private SysParamService sysParamService;

    @Autowired
    private RoleUserDao roleUserDao;

    @Autowired
    private RoleResourceDao roleResourceDao;

    @Autowired
    private ResourceDao resourceDao;

    /**
     * 移除某人缓存中的权限
     *
     * @param userId
     */
    public void removeCache(Long userId) {
        this.userResources.remove(userId);
    }

    /**
     * 检查某人是否有访问某个方法的权限
     *
     * @param requestTokenBO
     * @param controllerName
     * @param methodName
     * @return
     */
    public Boolean checkUserHaveResource(RequestTokenBO requestTokenBO, String controllerName, String methodName) {
        if (StringUtils.isEmpty(controllerName) || StringUtils.isEmpty(methodName)) {
            return false;
        }
        Boolean isSuperman = requestTokenBO.getUserBO().getIsSuperman();
        if (isSuperman) {
            return true;
        }
        Map<String, List<String>> resources = this.getResources(requestTokenBO.getRequestUserId());
        List<String> urlList = resources.get(controllerName.toLowerCase());
        if (CollectionUtils.isEmpty(urlList)) {
            return false;
        }
        return urlList.contains(methodName);
    }

    public List<ResourceEntity> getUserAllResource(Long userId) {
        return userResourceListMap.get(userId);
    }

    /**
     * 判断是否为超级管理员
     * @param userId
     * @return
     */
    public Boolean isSuperman(Long userId) {
        SysParamDTO systemConfig = sysParamService.getCacheByKey(SysParamEnum.Key.USER_SUPERMAN);
        if (systemConfig == null) {
            throw new BusinessException("缺少系统配置项[" + SysParamEnum.Key.USER_SUPERMAN.name() + "]");
        }
        List<Long> superManIdsList = JBStringUtil.splitConverToLongList(systemConfig.getConfigValue(), ",");
        return superManIdsList.contains(userId);
    }

    /**
     * 根据用户ID 获取 权限信息
     * @param userId
     * @return
     */
    public List<ResourceEntity> getResourcesByUserId(Long userId) {
        List<ResourceEntity> privilegeEntities = null;
        // 如果是超管的话
        Boolean isSuperman = this.isSuperman(userId);
        if (isSuperman) {
            privilegeEntities = resourceDao.selectAll();
        } else {
            privilegeEntities = loadResourceFromDb(userId);
        }

        if (privilegeEntities == null) {
            privilegeEntities = Lists.newArrayList();
        }

        this.updateCacheResource(userId, privilegeEntities);
        return privilegeEntities;
    }



    /**
     * 获取某人所能访问的方法
     *
     * @param userId
     * @return
     */
    private Map<String, List<String>> getResources(Long userId) {
        Map<String, List<String>> privileges = userResources.get(userId);
        if (privileges != null) {
            return privileges;
        }
        List<ResourceEntity> privilegeEntities = this.loadResourceFromDb(userId);
        return updateCacheResource(userId, privilegeEntities);
    }

    private List<ResourceEntity> loadResourceFromDb(Long userId) {
        List<Long> roleIdList = roleUserDao.selectRoleIdByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        List<ResourceEntity> privilegeEntities = roleResourceDao.listByRoleIds(roleIdList, JudgeEnum.YES.getValue());
        if (privilegeEntities != null) {
            return privilegeEntities;
        }
        return Collections.emptyList();
    }

    public Map<String, List<String>> updateCacheResource(Long userId, List<ResourceEntity> privilegeEntities) {
        userResourceListMap.put(userId, privilegeEntities);
        List<String> privilegeList = new ArrayList<>();
        Map<String, List<String>> resourceMap = new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(privilegeEntities)) {
            List<List<String>> setList =
                    privilegeEntities.stream().filter(e -> e.getType().equals(ResourceTypeEnum.POINTS.getValue())).map(ResourceEntity::getUrl).collect(Collectors.toList()).stream().map(e -> JBStringUtil.splitConvertToList(e, ",")).collect(Collectors.toList());
            setList.forEach(privilegeList::addAll);
        }
        privilegeList.forEach(item -> {
            List<String> path = JBStringUtil.splitConvertToList(item, "\\.");
            String controllerName = path.get(0).toLowerCase();
            String methodName = path.get(1);
            List<String> methodNameList = resourceMap.get(controllerName);
            if (null == methodNameList) {
                methodNameList = new ArrayList();
            }
            if (!methodNameList.contains(methodName)) {
                methodNameList.add(methodName);
            }
            resourceMap.put(controllerName, methodNameList);
        });

        userResources.put(userId, resourceMap);
        return resourceMap;
    }

    public void updateOnlineUserResourceByRoleId(Long roleId) {
        List<UserDTO> roleUserList = roleUserDao.selectUserByRoleId(roleId);
        List<Long> userIdList = roleUserList.stream().map(e -> e.getId()).collect(Collectors.toList());

        for (Long empId : userResources.keySet()) {
            if (userIdList.contains(empId)) {
                getResourcesByUserId(empId);
            }
        }
    }
}
