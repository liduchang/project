package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import cn.javaboy.admin.common.domain.sys.bo.UserBO;
import cn.javaboy.admin.common.domain.sys.dto.*;
import cn.javaboy.admin.common.domain.sys.vo.UserVO;
import cn.javaboy.admin.common.resp.ResponseDTO;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 10:39
 */
public interface IUserService {

    public List<UserVO> getAllUser();

    public UserBO getById(Long id);

    public ResponseDTO<PageResultDTO<UserVO>> selectUserList(UserQueryDTO queryDTO);

    public ResponseDTO<String> addUser(UserAddDTO userAddDto, RequestTokenBO requestToken);

    public ResponseDTO<String> updateStatus(Long userId, Integer status);

    public ResponseDTO<String> batchUpdateStatus(UserBatchUpdateStatusDTO batchUpdateStatusDTO);

    public ResponseDTO<String> updateUser(UserUpdateDTO updateDTO);

    public ResponseDTO<String> deleteUserById(Long userId);

    public ResponseDTO<String> updateRoles(UserUpdateRolesDTO updateRolesDTO);

    public ResponseDTO<String> updatePwd(UserUpdatePwdDTO updatePwdDTO, RequestTokenBO requestToken);

    public ResponseDTO resetPasswd(Integer userId);

    public ResponseDTO<List<UserVO>> getUserByDeptId(Long departmentId);

    
}
