package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.anno.OperateLog;
import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.dto.PageResultDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserOperateLogDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserOperateLogQueryDTO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.service.sys.IUserOperateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = {SwaggerTagConst.Admin.MANAGER_USER_OPERATE_LOG})
@RequestMapping("/api/log")
public class UserOperateLogController {

    @Autowired
    private IUserOperateLogService userOperateLogService;

    @ApiOperation(value = "分页查询",notes = "@author liduchang")
    @PostMapping("/userOperateLog/page/query")
    @OperateLog
    public ResponseDTO<PageResultDTO<UserOperateLogDTO>> queryByPage(@RequestBody UserOperateLogQueryDTO queryDTO) {
        return userOperateLogService.queryByPage(queryDTO);
    }

    @ApiOperation(value="删除",notes = "@author liduchang")
    @GetMapping("/userOperateLog/delete/{id}")
    @OperateLog
    public ResponseDTO<String> delete(@PathVariable("id") Long id){
        return userOperateLogService.delete(id);
    }

    @ApiOperation(value="详情",notes = "@author liduchang")
    @GetMapping("/userOperateLog/detail/{id}")
    @OperateLog
    public ResponseDTO<UserOperateLogDTO> detail(@PathVariable("id") Long id){
        return userOperateLogService.detail(id);
    }
}
