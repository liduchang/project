package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.anno.OperateLog;
import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.dto.ResourceFunctionDTO;
import cn.javaboy.admin.common.domain.sys.dto.ResourceMenuDTO;
import cn.javaboy.admin.common.domain.sys.dto.ValidateList;
import cn.javaboy.admin.common.domain.sys.vo.ResourceFunctionVO;
import cn.javaboy.admin.common.domain.sys.vo.ResourceMenuVO;
import cn.javaboy.admin.common.domain.sys.vo.ResourceRequestUrlVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.service.sys.IResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@OperateLog
@RestController
@Api(tags = {SwaggerTagConst.Admin.MANAGER_PRIVILEGE})
@RequestMapping("/api/resource")
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @GetMapping("/getAllUrl")
    @ApiOperation(value = "获取所有请求路径", notes = "获取所有请求路径")
    public ResponseDTO<List<ResourceRequestUrlVO>> getAllUrl() {
        return resourceService.getPrivilegeUrlDTOList();
    }

    @ApiOperation(value = "菜单批量保存")
    @PostMapping("/menu/batchSaveMenu")
    public ResponseDTO<String> menuBatchSave(@Valid @RequestBody ValidateList<ResourceMenuDTO> menuList) {
        return resourceService.menuBatchSave(menuList);
    }


    @ApiOperation(value = "查询所有菜单项")
    @PostMapping("/menu/queryAll")
    public ResponseDTO<List<ResourceMenuVO>> queryAll() {
        return resourceService.menuQueryAll();
    }


    @ApiOperation(value = "保存更新功能点")
    @PostMapping("/function/saveOrUpdate")
    public ResponseDTO<String> functionSaveOrUpdate(@Valid @RequestBody ResourceFunctionDTO privilegeFunctionDTO) {
        return resourceService.functionSaveOrUpdate(privilegeFunctionDTO);
    }

    @ApiOperation(value = "批量保存功能点")
    @PostMapping("/function/batchSave")
    public ResponseDTO<String>  batchSaveFunctionList(@Valid @RequestBody ValidateList<ResourceFunctionDTO> functionList) {
        return resourceService.batchSaveFunctionList(functionList);
    }


    @ApiOperation(value = "查询菜单功能点", notes = "更新")
    @PostMapping("/function/query/{menuKey}")
    public ResponseDTO<List<ResourceFunctionVO>> functionQuery(@PathVariable String menuKey) {
        return resourceService.functionQuery(menuKey);
    }


}
