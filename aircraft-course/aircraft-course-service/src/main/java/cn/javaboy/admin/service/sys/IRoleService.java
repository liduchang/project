package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.dto.RoleAddDTO;
import cn.javaboy.admin.common.domain.sys.dto.RoleUpdateDTO;
import cn.javaboy.admin.common.domain.sys.vo.RoleVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/15 22:32
 */
public interface IRoleService {

    public ResponseDTO addRole(RoleAddDTO roleAddDTO);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO deleteRole(Long roleId);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> updateRole(RoleUpdateDTO roleUpdateDTO);

    public ResponseDTO<RoleVO> getRoleById(Long roleId);

    public ResponseDTO<List<RoleVO>> getAllRole();
}
