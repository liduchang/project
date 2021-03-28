package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.dto.RoleResourceDTO;
import cn.javaboy.admin.common.domain.sys.dto.RoleResourceTreeVO;
import cn.javaboy.admin.common.resp.ResponseDTO;

/**
 * @Author: liduchang
 * @Date: 2021/3/15 23:10
 */
public interface IRoleResourceService {

    public ResponseDTO<String> updateRoleResource(RoleResourceDTO updateDTO);

    public ResponseDTO<RoleResourceTreeVO> listResourceByRoleId(Long roleId);

}
