package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.anno.NoValidPrivilege;
import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import cn.javaboy.admin.common.domain.sys.dto.*;
import cn.javaboy.admin.common.domain.sys.vo.UserVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.utils.JBRequestTokenUtil;
import cn.javaboy.admin.service.sys.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = {SwaggerTagConst.Admin.MANAGER_USER})
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/query")
    @ApiOperation(value = "员工管理查询", notes = "员工管理查询 @author lidoudou")
    public ResponseDTO<PageResultDTO<UserVO>> query(@RequestBody UserQueryDTO query) {
        return userService.selectUserList(query);
    }

    @GetMapping("/get/all")
    @ApiOperation(value = "查询所有员工基本信息，用于选择框", notes = "查询所有员工基本信息，用于选择框")
    @NoValidPrivilege
    public ResponseDTO<List<UserVO>> getAll() {
        return ResponseDTO.succData(userService.getAllUser());
    }

    @ApiOperation(value = "添加员工", notes = "@author yandanyang")
    @PostMapping("/add")
    public ResponseDTO<String> addUser(@Valid @RequestBody UserAddDTO emp) {
        RequestTokenBO requestToken = JBRequestTokenUtil.getRequestUser();
        return userService.addUser(emp, requestToken);
    }

    @ApiOperation(value = "禁用单个员工", notes = "@author yandanyang")
    @GetMapping("/updateStatus/{userId}/{status}")
    public ResponseDTO<String> updateStatus(@PathVariable("userId") Long userId, @PathVariable("status") Integer status) {
        return userService.updateStatus(userId, status);
    }

    @ApiOperation(value = "批量禁用", notes = "@author yandanyang")
    @PostMapping("/batchUpdateStatus")
    public ResponseDTO<String> batchUpdateStatus(@Valid @RequestBody UserBatchUpdateStatusDTO batchUpdateStatusDTO) {
        return userService.batchUpdateStatus(batchUpdateStatusDTO);
    }

    @ApiOperation(value = "更新员工信息", notes = "@author yandanyang")
    @PostMapping("/update")
    public ResponseDTO<String> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDto) {
        return userService.updateUser(userUpdateDto);
    }

    @ApiOperation(value = "删除员工信息", notes = "@author yandanyang")
    @PostMapping("/delete/{userId}")
    public ResponseDTO<String> deleteUserById(@PathVariable("userId") Long userId) {
        return userService.deleteUserById(userId);
    }

    @ApiOperation(value = "单个员工角色授权", notes = "@author yandanyang")
    @PostMapping("/updateRoles")
    public ResponseDTO<String> updateRoles(@Valid @RequestBody UserUpdateRolesDTO updateRolesDTO) {
        return userService.updateRoles(updateRolesDTO);
    }

    @ApiOperation(value = "修改密码", notes = "@author yandanyang")
    @PostMapping("/updatePwd")
    public ResponseDTO<String> updatePwd(@Valid @RequestBody UserUpdatePwdDTO updatePwdDTO) {
        RequestTokenBO requestToken = JBRequestTokenUtil.getRequestUser();
        return userService.updatePwd(updatePwdDTO, requestToken);
    }

    @ApiOperation(value = "通过部门id获取当前部门的人员&没有部门的人", notes = "@author yandanyang")
    @GetMapping("/listUserByDeptId/{deptId}")
    public ResponseDTO<List<UserVO>> listUserByDeptId(@PathVariable Long deptId) {
        return userService.getUserByDeptId(deptId);
    }

    @ApiOperation(value = "员工重置密码", notes = "@author lizongliang")
    @GetMapping("/resetPasswd/{userId}")
    public ResponseDTO resetPasswd(@PathVariable("userId") Integer userId) {
        return userService.resetPasswd(userId);
    }

}
