package cn.javaboy.admin.dao.sys;

import cn.javaboy.admin.common.domain.sys.dto.RoleQueryDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserDTO;
import cn.javaboy.admin.common.domain.sys.entity.RoleUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/3/27 0027 下午 13:00
 * @since JDK1.8
 */
@Mapper
public interface RoleUserDao extends BaseMapper<RoleUserEntity> {

    /**
     * 根据员工id 查询所有的角色
     * @param userId
     * @return
     */
    List<Long> selectRoleIdByUserId(@Param("userId") Long userId);

    /**
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<UserDTO> selectUserByNamePage(Page page, @Param("queryDTO") RoleQueryDTO queryDTO);

    /**
     *
     * @param roleId
     * @return
     */
    List<UserDTO> selectUserByRoleId(@Param("roleId") Long roleId);
    /**
     * 根据员工信息删除
     * @param userId
     */
    void deleteByUserId(@Param("userId") Long userId);

    /**
     * 删除某个角色的所有关系
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据员工和 角色删除关系
     * @param userId
     * @param roleId
     */
    void deleteByUserIdRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 批量删除某个角色下的某批用户的关联关系
     * @param roleId
     * @param userIds
     */
    void batchDeleteUserRole(@Param("roleId") Long roleId, @Param("userIds") List<Long> userIds);

    /**
     * 批量新增
     * @param roleRelationList
     */
    void batchInsert(List<RoleUserEntity> roleRelationList);
}
