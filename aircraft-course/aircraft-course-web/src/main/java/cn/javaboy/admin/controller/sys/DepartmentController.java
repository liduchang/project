package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.dto.DepartmentCreateDTO;
import cn.javaboy.admin.common.domain.sys.dto.DepartmentUpdateDTO;
import cn.javaboy.admin.common.domain.sys.vo.DepartmentVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.service.sys.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {SwaggerTagConst.Admin.MANAGER_DEPARTMENT})
@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "查询部门树形列表", notes = "查询部门列表")
    @GetMapping("/list")
    public ResponseDTO<List<DepartmentVO>> listDepartment() {
        return departmentService.listDepartment();
    }

    @ApiOperation(value = "查询部门及员工列表", notes = "查询部门及员工列表")
    @GetMapping("/listUser")
    public ResponseDTO<List<DepartmentVO>> listDepartmentUser() {
        return departmentService.listAllDepartmentUser(null);
    }

    @ApiOperation(value = "根据部门名称查询部门及员工列表", notes = "根据部门名称查询部门及员工列表")
    @GetMapping("/listUserByDepartmentName")
    public ResponseDTO<List<DepartmentVO>> listDepartmentUser(String departmentName) {
        return departmentService.listAllDepartmentUser(departmentName);
    }

    @ApiOperation(value = "添加部门", notes = "添加部门")
    @PostMapping("/add")
    public ResponseDTO<String> addDepartment(@Valid @RequestBody DepartmentCreateDTO departmentCreateDTO) {
        return departmentService.addDepartment(departmentCreateDTO);
    }

    @ApiOperation(value = "更新部门信息", notes = "更新部门信息")
    @PostMapping("/update")
    public ResponseDTO<String> updateDepartment(@Valid @RequestBody DepartmentUpdateDTO departmentUpdateDTO) {
        return departmentService.updateDepartment(departmentUpdateDTO);
    }

    @ApiOperation(value = "删除部门", notes = "删除部门")
    @PostMapping("/delete/{deptId}")
    public ResponseDTO<String> delDepartment(@PathVariable Long deptId) {
        return departmentService.delDepartment(deptId);
    }

    @ApiOperation(value = "获取部门信息", notes = "获取部门")
    @GetMapping("/query/{deptId}")
    public ResponseDTO<DepartmentVO> getDepartment(@PathVariable Long deptId) {
        return departmentService.getDepartmentById(deptId);
    }

    @ApiOperation(value = "查询部门列表", notes = "查询部门列表")
    @GetMapping("/listAll")
    public ResponseDTO<List<DepartmentVO>> listAll() {
        return departmentService.listAll();
    }


    @ApiOperation(value = "上下移动")
    @GetMapping("/upOrDown/{deptId}/{swapId}")
    public ResponseDTO<String> upOrDown(@PathVariable Long deptId, @PathVariable Long swapId) {
        return departmentService.upOrDown(deptId, swapId);
    }

    @ApiOperation(value = "升级")
    @GetMapping("/upgrade/{deptId}")
    public ResponseDTO<String> upgrade(@PathVariable Long deptId) {
        return departmentService.upgrade(deptId);
    }

    @ApiOperation(value = "降级")
    @GetMapping("/downgrade/{deptId}/{preId}")
    public ResponseDTO<String> downgrade(@PathVariable Long deptId, @PathVariable Long preId) {
        return departmentService.downgrade(deptId, preId);
    }


}
