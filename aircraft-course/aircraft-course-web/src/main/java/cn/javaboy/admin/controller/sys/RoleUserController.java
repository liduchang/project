package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.anno.OperateLog;
import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.dto.PageResultDTO;
import cn.javaboy.admin.common.domain.sys.dto.RoleBatchDTO;
import cn.javaboy.admin.common.domain.sys.dto.RoleQueryDTO;
import cn.javaboy.admin.common.domain.sys.vo.RoleSelectedVO;
import cn.javaboy.admin.common.domain.sys.vo.UserVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.service.sys.IRoleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {SwaggerTagConst.Admin.MANAGER_ROLE_USER})
@RestController
@RequestMapping("/api/role")
public class RoleUserController {

    @Autowired
    private IRoleUserService roleUserService;

    @ApiOperation(value = "获取角色成员-员工列表", notes = "获取角色成员-员工列表（分页）")
    @PostMapping("/listUser")
    @OperateLog
    public ResponseDTO<PageResultDTO<UserVO>> listUserByName(@Valid @RequestBody RoleQueryDTO queryDTO) {
        return roleUserService.listUserByName(queryDTO);
    }

    @ApiOperation(value = "根据角色id获取角色员工列表(无分页)", notes = "根据角色id获取角色成员-员工列表")
    @GetMapping("/listAllUser/{roleId}")
    @OperateLog
    public ResponseDTO<List<UserVO>> listAllUserRoleId(@PathVariable Long roleId) {
        return roleUserService.getAllUserByRoleId(roleId);
    }

    @ApiOperation(value = "从角色成员列表中移除员工", notes = "从角色成员列表中移除员工")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "员工id", paramType = "query", required = true), @ApiImplicitParam(name = "roleId", value = "角色id", paramType = "query",
        required = true)})
    @GetMapping("/removeUser")
    @OperateLog
    public ResponseDTO<String> removeUser(Long userId, Long roleId) {
        return roleUserService.removeUserRole(userId, roleId);
    }

    @ApiOperation(value = "从角色成员列表中批量移除员工", notes = "从角色成员列表中批量移除员工")
    @PostMapping("/removeUserList")
    @OperateLog
    public ResponseDTO<String> removeUserList(@Valid @RequestBody RoleBatchDTO removeDTO) {
        return roleUserService.batchRemoveUserRole(removeDTO);
    }

    @ApiOperation(value = "角色成员列表中批量添加员工", notes = "角色成员列表中批量添加员工")
    @PostMapping("/addUserList")
    @OperateLog
    public ResponseDTO<String> addUserList(@Valid @RequestBody RoleBatchDTO addDTO) {
        return roleUserService.batchAddUserRole(addDTO);
    }

    @ApiOperation(value = "通过员工id获取所有角色以及员工具有的角色", notes = "通过员工id获取所有角色以及员工具有的角色")
    @GetMapping("/getRoles/{userId}")
    @OperateLog
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "员工id", paramType = "path", required = true)})
    public ResponseDTO<List<RoleSelectedVO>> getRoleByUserId(@PathVariable Long userId) {
        return roleUserService.getRolesByUserId(userId);
    }
}
