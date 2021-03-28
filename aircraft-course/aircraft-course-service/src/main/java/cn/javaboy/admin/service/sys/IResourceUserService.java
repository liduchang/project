package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import cn.javaboy.admin.common.domain.sys.entity.ResourceEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 16:17
 */
public interface IResourceUserService {

    public void removeCache(Long userId);

    public Boolean checkUserHaveResource(RequestTokenBO requestTokenBO, String controllerName, String methodName);

    public List<ResourceEntity> getUserAllResource(Long userId);

    public Boolean isSuperman(Long userId);

    public List<ResourceEntity> getResourcesByUserId(Long userId);

    public Map<String, List<String>> updateCacheResource(Long userId, List<ResourceEntity> resourceEntities);

    public void updateOnlineUserResourceByRoleId(Long roleId);
}
