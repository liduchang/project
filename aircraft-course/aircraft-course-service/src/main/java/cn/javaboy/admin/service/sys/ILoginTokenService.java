package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import cn.javaboy.admin.common.domain.sys.dto.UserDTO;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 13:47
 */
public interface ILoginTokenService {

    String generateToken(UserDTO userDTO);

    public RequestTokenBO getUserTokenInfo(String token);
}
