package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.anno.OperateLog;
import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.dto.RoleAddDTO;
import cn.javaboy.admin.common.domain.sys.dto.RoleUpdateDTO;
import cn.javaboy.admin.common.domain.sys.vo.RoleVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.service.sys.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {SwaggerTagConst.Admin.MANAGER_ROLE})
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "添加角色", notes = "添加角色")
    @PostMapping("/add")
    @OperateLog
    public ResponseDTO addRole(@Valid @RequestBody RoleAddDTO roleAddDTO) {
        return roleService.addRole(roleAddDTO);
    }

    @ApiOperation(value = "删除角色", notes = "根据id删除角色")
    @GetMapping("/delete/{roleId}")
    @OperateLog
    public ResponseDTO<String> deleteRole(@PathVariable("roleId") Long roleId) {
        return roleService.deleteRole(roleId);
    }

    @ApiOperation(value = "更新角色", notes = "更新角色")
    @PostMapping("/update")
    @OperateLog
    public ResponseDTO<String> updateRole(@Valid @RequestBody RoleUpdateDTO roleUpdateDTO) {
        return roleService.updateRole(roleUpdateDTO);
    }

    @ApiOperation(value = "获取角色数据", notes = "根据id获取角色数据")
    @GetMapping("/get/{roleId}")
    @OperateLog
    public ResponseDTO<RoleVO> getRole(@PathVariable("roleId") Long roleId) {
        return roleService.getRoleById(roleId);
    }

    @ApiOperation(value = "获取所有角色", notes = "获取所有角色数据")
    @GetMapping("/getAll")
    @OperateLog
    public ResponseDTO<List<RoleVO>> getAllRole() {
        return roleService.getAllRole();
    }

}
