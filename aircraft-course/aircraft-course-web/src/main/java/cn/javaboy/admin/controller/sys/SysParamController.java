package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.anno.OperateLog;
import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.dto.PageResultDTO;
import cn.javaboy.admin.common.domain.sys.dto.SysParamAddDTO;
import cn.javaboy.admin.common.domain.sys.dto.SysParamQueryDTO;
import cn.javaboy.admin.common.domain.sys.dto.SysParamUpdateDTO;
import cn.javaboy.admin.common.domain.sys.vo.SysParamVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.service.sys.ISysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {SwaggerTagConst.Admin.MANAGER_SYSTEM_CONFIG})
@OperateLog
@RestController
@RequestMapping("/api/param")
public class SysParamController {

    @Autowired
    private ISysParamService sysParamService;

    @ApiOperation(value = "分页查询所有系统配置", notes = "分页查询所有系统配置")
    @PostMapping("/getListPage")
    public ResponseDTO<PageResultDTO<SysParamVO>> getSysParamPage(@RequestBody @Valid SysParamQueryDTO queryDTO) {
        return sysParamService.getSysParamPage(queryDTO);
    }

    @ApiOperation(value = "添加配置参数", notes = "添加配置参数")
    @PostMapping("/add")
    public ResponseDTO<String> addSysParam(@RequestBody @Valid SysParamAddDTO configAddDTO) {
        return sysParamService.addSysParam(configAddDTO);
    }

    @ApiOperation(value = "修改配置参数", notes = "修改配置参数")
    @PostMapping("/update")
    public ResponseDTO<String> updateSysParam(@RequestBody @Valid SysParamUpdateDTO updateDTO) {
        return sysParamService.updateSysParam(updateDTO);
    }

    @ApiOperation(value = "根据分组查询所有系统配置", notes = "根据分组查询所有系统配置")
    @GetMapping("/getListByGroup")
    public ResponseDTO<List<SysParamVO>> getListByGroup(String group) {
        return sysParamService.getListByGroup(group);
    }

    @ApiOperation(value = "通过key获取对应的信息", notes = "通过key获取对应的信息")
    @GetMapping("/selectByKey")
    public ResponseDTO<SysParamVO> selectByKey(String configKey) {
        return sysParamService.selectByKey(configKey);
    }

}
