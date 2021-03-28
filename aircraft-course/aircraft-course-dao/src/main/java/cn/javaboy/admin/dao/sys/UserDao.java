package cn.javaboy.admin.dao.sys;

import cn.javaboy.admin.common.domain.sys.dto.UserDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserQueryDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserQueryExportDTO;
import cn.javaboy.admin.common.domain.sys.entity.UserEntity;
import cn.javaboy.admin.common.domain.sys.vo.UserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 用户接口
 * @author liduchang
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    /**
     * 查询员工列表
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<UserDTO> selectUserList(Page page, @Param("queryDTO") UserQueryDTO queryDTO);

    /**
     * 不带分页查询用户列表
     * @param queryDTO
     * @return
     */
    List<UserDTO> selectUserList(@Param("queryDTO") UserQueryExportDTO queryDTO);

    /**
     * 批量更新禁用状态
     *
     * @param userIds
     * @param isDisabled
     */
    void batchUpdateStatus(@Param("userIds") List<Long> userIds, @Param("isDisabled") Integer isDisabled);

    /**
     * 登录
     *
     * @param loginName
     * @param loginPwd
     * @return
     */
    UserDTO login(@Param("loginName") String loginName, @Param("loginPwd") String loginPwd);

    /**
     * 通过登录名查询
     *
     * @param loginName
     * @param isDisabled
     * @return
     */
    UserDTO getByLoginName(@Param("loginName") String loginName, @Param("isDisabled") Integer isDisabled);

    /**
     * 通过手机号查询
     *
     * @param phone
     * @param isDisabled
     * @return
     */
    UserDTO getByPhone(@Param("phone") String phone, @Param("isDisabled") Integer isDisabled);

    /**
     * 获取所有员工
     *
     * @return
     */
    List<UserDTO> listAll();

    /**
     * 获取某个部门员工数
     *
     * @param depId
     * @param deleteFlag 可以null
     * @return
     */
    Integer countByDepartmentId(@Param("depId") Long depId, @Param("deleteFlag") Boolean deleteFlag);

    /**
     * 获取一批员工
     *
     * @param userIds
     * @return
     */
    List<UserDTO> getUserByIds(@Param("ids") Collection<Long> userIds);


    UserDTO getUserById(@Param("id") Long userId);

    /**
     * 获取某个部门的员工
     *
     * @param departmentId
     * @return
     */
    List<UserVO> getUserIdByDeptId(@Param("departmentId") Long departmentId);

    /**
     * 获取某批部门的员工
     *
     * @param departmentIds
     * @return
     */
    List<UserDTO> getUserIdByDeptIds(@Param("departmentIds") List<Long> departmentIds);


    /**
     * 用户重置密码
     * @param userId
     * @param password
     * @return
     */
    Integer updatePassword(@Param("userId") Integer userId, @Param("password") String password);


    /**
     * 查询所有员工
     *
     * @return
     */
    List<UserVO> selectAll();

}