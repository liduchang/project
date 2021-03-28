package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.anno.NoValidPrivilege;
import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.dto.DataScopeBatchSetRoleDTO;
import cn.javaboy.admin.common.domain.sys.vo.DataScopeAndViewTypeVO;
import cn.javaboy.admin.common.domain.sys.vo.DataScopeSelectVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.service.sys.IDataScopeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {SwaggerTagConst.Admin.MANAGER_DATA_SCOPE})
@RestController
@RequestMapping("/api/dataScope")
public class DataScopeController {

    @Autowired
    private IDataScopeService dataScopeService;

    @ApiOperation(value = "获取当前系统所配置的所有数据范围")
    @GetMapping("/list")
    @NoValidPrivilege
    public ResponseDTO<List<DataScopeAndViewTypeVO>> dataScopeList() {
        return dataScopeService.dataScopeList();
    }

    @ApiOperation(value = "获取某角色所设置的数据范围")
    @GetMapping("/listByRole/{roleId}")
    @NoValidPrivilege
    public ResponseDTO<List<DataScopeSelectVO>> dataScopeListByRole(@PathVariable Long roleId) {
        return dataScopeService.dataScopeListByRole(roleId);
    }

    @ApiOperation(value = "批量设置某角色数据范围")
    @PostMapping("/batchSet")
    @NoValidPrivilege
    public ResponseDTO<String> dataScopeBatchSet(@RequestBody @Valid DataScopeBatchSetRoleDTO batchSetRoleDTO) {
        return dataScopeService.dataScopeBatchSet(batchSetRoleDTO);
    }

}
