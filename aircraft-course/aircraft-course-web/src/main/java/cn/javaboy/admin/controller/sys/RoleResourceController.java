package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.anno.OperateLog;
import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.dto.RoleResourceDTO;
import cn.javaboy.admin.common.domain.sys.dto.RoleResourceTreeVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.service.sys.IRoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@OperateLog
@RestController
@Api(tags = {SwaggerTagConst.Admin.MANAGER_ROLE_PRIVILEGE})
@RequestMapping("/api/resource")
public class RoleResourceController {

    @Autowired
    private IRoleResourceService roleResourceService;

    @ApiOperation(value = "更新角色权限", notes = "更新角色权限")
    @PostMapping("/updateRolePrivilege")
    public ResponseDTO<String> updateRolePrivilege(@Valid @RequestBody RoleResourceDTO updateDTO) {
        return roleResourceService.updateRoleResource(updateDTO);
    }

    @ApiOperation(value = "获取角色可选的功能权限", notes = "获取角色可选的功能权限")
    @GetMapping("/listPrivilegeByRoleId/{roleId}")
    public ResponseDTO<RoleResourceTreeVO> listPrivilegeByRoleId(@PathVariable("roleId") Long roleId) {
        return roleResourceService.listResourceByRoleId(roleId);
    }

}
