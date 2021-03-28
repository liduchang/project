package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.constant.CommonConst;
import cn.javaboy.admin.common.domain.constant.UserStatusEnum;
import cn.javaboy.admin.common.domain.constant.JudgeEnum;
import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import cn.javaboy.admin.common.domain.sys.dto.UserLoginFormDTO;
import cn.javaboy.admin.common.domain.sys.dto.LoginPrivilegeDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserDTO;
import cn.javaboy.admin.common.domain.sys.entity.ResourceEntity;
import cn.javaboy.admin.common.domain.sys.entity.UserLoginLogEntity;
import cn.javaboy.admin.common.domain.sys.vo.KaptchaVO;
import cn.javaboy.admin.common.domain.sys.vo.LoginDetailVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.UserResponseCodeConst;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.common.utils.JBDigestUtil;
import cn.javaboy.admin.common.utils.JBPUtil;
import cn.javaboy.admin.dao.sys.DepartmentDao;
import cn.javaboy.admin.dao.sys.UserDao;
import cn.javaboy.admin.service.sys.ILogService;
import cn.javaboy.admin.service.sys.ILoginService;
import cn.javaboy.admin.service.sys.ILoginTokenService;
import cn.javaboy.admin.service.sys.IResourceUserService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginService implements ILoginService{

    private static final String VERIFICATION_CODE_REDIS_PREFIX = "vc_%s";

    @Autowired
    private UserDao userDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private IResourceUserService resourceUserService;
    @Autowired
    private ILoginTokenService loginTokenService;
    @Autowired
    private ILogService logService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 登陆
     * @param loginForm 登录用户的信息
     * @return 登录用户基本信息
     */
    public ResponseDTO<LoginDetailVO> login(@Valid UserLoginFormDTO loginForm, HttpServletRequest request) {
        if (loginForm.getCode()==null||!loginForm.getCode().equals(redisTemplate.opsForValue().get("verCode"))){
            return ResponseDTO.wrap(UserResponseCodeConst.VERIFICATION_CODE_ERROR);
        }
        String loginPwd = JBDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, loginForm.getLoginPwd());
        UserDTO userDTO = userDao.login(loginForm.getLoginName(), loginPwd);
        // 用户名和密码不正确
        if (null == userDTO) return ResponseDTO.wrap(UserResponseCodeConst.LOGIN_FAILED);
        // 账号不可用
        if (UserStatusEnum.DISABLED.equalsValue(userDTO.getIsDisabled())) return ResponseDTO.wrap(UserResponseCodeConst.IS_DISABLED);
        // jwt token赋值
        String compactJws = loginTokenService.generateToken(userDTO);
        LoginDetailVO loginDTO = JBBeanUtil.copy(userDTO, LoginDetailVO.class);

        //获取前端功能权限
        loginDTO.setPrivilegeList(initUserResource(userDTO.getId()));
        loginDTO.setXAccessToken(compactJws);
        loginDTO.setDepartmentName(departmentDao.selectById(userDTO.getDepartmentId()).getName());

        //判断是否为超管
        Boolean isSuperman = resourceUserService.isSuperman(loginDTO.getId());
        loginDTO.setIsSuperMan(isSuperman);
        //登陆操作日志
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        UserLoginLogEntity logEntity =
                UserLoginLogEntity.builder()
                        .userId(userDTO.getId())
                        .userName(userDTO.getActualName())
                        .remoteIp(JBPUtil.getRemoteIp(request))
                        .remotePort(request.getRemotePort())
                        .remoteBrowser(userAgent.getBrowser().getName())
                        .remoteOs(userAgent.getOperatingSystem().getName())
                        .loginStatus(JudgeEnum.YES.getValue()).build();
        logService.addLog(logEntity);
        return ResponseDTO.succData(loginDTO);
    }

    /**
     * 退出登陆，清除token缓存
     * @param requestToken
     * @return 退出登陆是否成功，bool
     */
    public ResponseDTO<Boolean> logoutByToken(RequestTokenBO requestToken) {
        resourceUserService.removeCache(requestToken.getRequestUserId());
        // 清除验证码
        redisTemplate.delete("verCode");
        return ResponseDTO.succ();
    }

    /**
     * 获取验证码
     * @return
     */
    public ResponseDTO<KaptchaVO> verificationCode() {
        KaptchaVO kaptchaVO = new KaptchaVO();
        String uuid = buildVerificationCodeRedisKey(UUID.randomUUID().toString());
        String kaptchaText = defaultKaptcha.createText();
        String base64Code = "";
        BufferedImage image = defaultKaptcha.createImage(kaptchaText);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            base64Code = Base64.encodeBase64String(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("verificationCode exception .{}", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.error("verificationCode outputStream close exception .{}", e);
                }
            }
        }
        kaptchaVO.setUuid(uuid);
        kaptchaVO.setCode("data:image/png;base64," + base64Code);
        log.info("验证码为：{}",kaptchaText);
        redisTemplate.opsForValue().set("verCode", kaptchaText, 3600, TimeUnit.SECONDS);
        return ResponseDTO.succData(kaptchaVO);
    }

    private String buildVerificationCodeRedisKey(String uuid) {
        return String.format(VERIFICATION_CODE_REDIS_PREFIX, uuid);
    }

    /**
     * 初始化用户权限
     * @param userId
     * @return
     */
    public List<LoginPrivilegeDTO> initUserResource(Long userId) {
        List<ResourceEntity> resourceList = resourceUserService.getResourcesByUserId(userId);
        resourceUserService.updateCacheResource(userId, resourceList);
        return JBBeanUtil.copyList(resourceList, LoginPrivilegeDTO.class);
    }

    public LoginDetailVO getSession(RequestTokenBO requestUser) {
        LoginDetailVO loginDTO = JBBeanUtil.copy(requestUser.getUserBO(), LoginDetailVO.class);
        List<ResourceEntity> privilegeEntityList = resourceUserService.getUserAllResource(requestUser.getRequestUserId());
        //======  开启缓存   ======
        if (privilegeEntityList == null) {
            List<LoginPrivilegeDTO> loginPrivilegeDTOS = initUserResource(requestUser.getRequestUserId());
            loginDTO.setPrivilegeList(loginPrivilegeDTOS);
        } else {
            loginDTO.setPrivilegeList(JBBeanUtil.copyList(privilegeEntityList, LoginPrivilegeDTO.class));
        }

        //======  不开启缓存   ======
//        List<LoginPrivilegeDTO> loginPrivilegeDTOS = initEmployeePrivilege(requestUser.getRequestUserId());
//        loginDTO.setPrivilegeList(loginPrivilegeDTOS);

        //判断是否为超管
        Boolean isSuperman = resourceUserService.isSuperman(loginDTO.getId());
        loginDTO.setIsSuperMan(isSuperman);
        return loginDTO;
    }
}
