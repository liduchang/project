package cn.javaboy.admin.dao.sys;

import cn.javaboy.admin.common.domain.sys.entity.RoleResourceEntity;
import cn.javaboy.admin.common.domain.sys.entity.ResourceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liiduchang
 * @version 1.0
 * @since JDK1.8
 */
@Mapper
public interface RoleResourceDao extends BaseMapper<RoleResourceEntity> {

    /**
     * 根据角色id删除
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 删除权限所关联的角色信息
     * @param privilegeKeyList
     */
    void deleteByPrivilegeKey(@Param("privilegeKeyList") List<String> privilegeKeyList);


    /**
     * 批量添加
     * @param rolePrivilegeList
     */
    void batchInsert(List<RoleResourceEntity> rolePrivilegeList);

    /**
     * 查询某批角色的权限
     * @param roleIds
     * @return
     */
    List<ResourceEntity> listByRoleIds(@Param("roleIds") List<Long> roleIds, @Param("normalStatus") Integer normalStatus);

    /**
     * 查询某个角色的权限
     * @param roleId
     * @return
     */
    List<ResourceEntity> listByRoleId(@Param("roleId") Long roleId);
}
