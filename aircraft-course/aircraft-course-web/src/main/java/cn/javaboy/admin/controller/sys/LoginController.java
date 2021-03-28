package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.anno.NoNeedLogin;
import cn.javaboy.admin.common.anno.NoValidPrivilege;
import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import cn.javaboy.admin.common.domain.sys.dto.UserLoginFormDTO;
import cn.javaboy.admin.common.domain.sys.vo.KaptchaVO;
import cn.javaboy.admin.common.domain.sys.vo.LoginDetailVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.LoginResponseCodeConst;
import cn.javaboy.admin.common.utils.JBRequestTokenUtil;
import cn.javaboy.admin.service.sys.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = {SwaggerTagConst.Admin.MANAGER_USER_LOGIN})
@RestController
@RequestMapping("/api/sys")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("/session/login")
    @ApiOperation(value = "登录", notes = "登录")
    @NoNeedLogin
    public ResponseDTO<LoginDetailVO> login(@Valid @RequestBody UserLoginFormDTO loginForm, HttpServletRequest request) {
        return loginService.login(loginForm, request);
    }

    @GetMapping("/session/get")
    @ApiOperation(value = "获取session", notes = "获取session")
    @NoValidPrivilege
    public ResponseDTO<LoginDetailVO> getSession() {
        RequestTokenBO requestUser = JBRequestTokenUtil.getRequestUser();
        return ResponseDTO.succData(loginService.getSession(requestUser));
    }

    @GetMapping("/session/logOut")
    @ApiOperation(value = "退出登陆", notes = "退出登陆")
    @NoValidPrivilege
    public ResponseDTO<Boolean> logOut() {
        RequestTokenBO requestToken = JBRequestTokenUtil.getRequestUser();
        if (null == requestToken) {
            return ResponseDTO.wrap(LoginResponseCodeConst.LOGIN_ERROR);
        }
        return loginService.logoutByToken(requestToken);
    }

    @GetMapping("/session/verificationCode")
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @NoNeedLogin
    public ResponseDTO<KaptchaVO> verificationCode() {
        return loginService.verificationCode();
    }

}
