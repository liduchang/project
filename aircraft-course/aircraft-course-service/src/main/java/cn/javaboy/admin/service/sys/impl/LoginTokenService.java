package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.constant.UserStatusEnum;
import cn.javaboy.admin.common.domain.constant.JudgeEnum;
import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import cn.javaboy.admin.common.domain.sys.bo.UserBO;
import cn.javaboy.admin.common.domain.sys.dto.UserDTO;
import cn.javaboy.admin.service.sys.ILoginTokenService;
import cn.javaboy.admin.service.sys.IUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 13:47
 */
@Slf4j
@Service
public class LoginTokenService implements ILoginTokenService {

    /**
     * 过期时间一天
     */
    private static final int EXPIRE_SECONDS = 1 * 24 * 3600;
    /**
     * jwt加密字段
     */
    private static final String CLAIM_ID_KEY = "id";

    @Value("${jwt.key}")
    private String jwtKey;

    @Autowired
    private IUserService userService;

    /**
     * 功能描述: 生成JWT TOKEN
     * @param userDTO
     * @return
     * @auther liduchang
     */
    public String generateToken(UserDTO userDTO) {
        Long id = userDTO.getId();
        /**将token设置为jwt格式*/
        String baseToken = UUID.randomUUID().toString();
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        LocalDateTime localDateTimeExpire = localDateTimeNow.plusSeconds(EXPIRE_SECONDS);
        Date from = Date.from(localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant());
        Date expire = Date.from(localDateTimeExpire.atZone(ZoneId.systemDefault()).toInstant());

        Claims jwtClaims = Jwts.claims().setSubject(baseToken);
        jwtClaims.put(CLAIM_ID_KEY, id);
        String compactJws = Jwts.builder().setClaims(jwtClaims).setNotBefore(from).setExpiration(expire).signWith(SignatureAlgorithm.HS512, jwtKey).compact();

        UserBO userBO = userService.getById(id);
        RequestTokenBO tokenBO = new RequestTokenBO(userBO);

        return compactJws;
    }

    /**
     * 功能描述: 根据登陆token获取登陆信息
     * @param
     * @return
     * @auther liduchang
     */
    public RequestTokenBO getUserTokenInfo(String token) {
        Long userId = -1L;
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
            String idStr = claims.get(CLAIM_ID_KEY).toString();
            userId = Long.valueOf(idStr);
        } catch (Exception e) {
            log.error("getUserTokenInfo error:{}", e);
            return null;
        }

        UserBO userBO = userService.getById(userId);

        if (userBO == null) return null;
        if (UserStatusEnum.DISABLED.getValue().equals(userBO.getIsDisabled())) return null;
        if (JudgeEnum.YES.equals(userBO.getIsLeave())) return null;
        if (JudgeEnum.YES.equals(userBO.getIsDelete())) return null;
        return new RequestTokenBO(userBO);
    }
}
