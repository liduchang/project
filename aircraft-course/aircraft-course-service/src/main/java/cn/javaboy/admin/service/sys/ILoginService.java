package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import cn.javaboy.admin.common.domain.sys.dto.UserLoginFormDTO;
import cn.javaboy.admin.common.domain.sys.dto.LoginPrivilegeDTO;
import cn.javaboy.admin.common.domain.sys.vo.KaptchaVO;
import cn.javaboy.admin.common.domain.sys.vo.LoginDetailVO;
import cn.javaboy.admin.common.resp.ResponseDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 11:48
 */
public interface ILoginService {

    public ResponseDTO<LoginDetailVO> login(@Valid UserLoginFormDTO loginForm, HttpServletRequest request);

    public ResponseDTO<Boolean> logoutByToken(RequestTokenBO requestToken);

    public ResponseDTO<KaptchaVO> verificationCode();

    public List<LoginPrivilegeDTO> initUserResource(Long employeeId);

    public LoginDetailVO getSession(RequestTokenBO requestUser);

}
