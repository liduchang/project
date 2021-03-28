package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.dto.PageResultDTO;
import cn.javaboy.admin.common.domain.sys.dto.RoleBatchDTO;
import cn.javaboy.admin.common.domain.sys.dto.RoleQueryDTO;
import cn.javaboy.admin.common.domain.sys.vo.RoleSelectedVO;
import cn.javaboy.admin.common.domain.sys.vo.UserVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/16 10:04
 */
public interface IRoleUserService {

    public ResponseDTO<PageResultDTO<UserVO>> listUserByName(RoleQueryDTO queryDTO);

    public ResponseDTO<List<UserVO>> getAllUserByRoleId(Long roleId);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> removeUserRole(Long userId, Long roleId);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> batchRemoveUserRole(RoleBatchDTO removeDTO);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> batchAddUserRole(RoleBatchDTO addDTO);

    public ResponseDTO<List<RoleSelectedVO>> getRolesByUserId(Long UserId);
}
