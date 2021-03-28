package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.anno.OperateLog;
import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.dto.PageResultDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserLoginLogDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserLoginLogQueryDTO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.service.sys.IUserLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = {SwaggerTagConst.Admin.MANAGER_USER_LOGIN_LOG})
@RequestMapping("/api/log")
public class UserLoginLogController {

    @Autowired
    private IUserLoginLogService userLoginLogService;

    @ApiOperation(value = "分页查询用户登录日志", notes = "@author liduchang")
    @PostMapping("/userLoginLog/page/query")
    @OperateLog
    public ResponseDTO<PageResultDTO<UserLoginLogDTO>> queryByPage(@RequestBody UserLoginLogQueryDTO queryDTO) {
        return userLoginLogService.queryByPage(queryDTO);
    }

    @ApiOperation(value = "删除用户登录日志", notes = "@author liduchang")
    @GetMapping("/userLoginLog/delete/{id}")
    @OperateLog
    public ResponseDTO<String> delete(@PathVariable("id") Long id) {
        return userLoginLogService.delete(id);
    }

}
