package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.dto.PageResultDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserLoginLogDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserLoginLogQueryDTO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: liduchang
 * @Date: 2021/3/15 14:03
 */
public interface IUserLoginLogService {

    public ResponseDTO<PageResultDTO<UserLoginLogDTO>> queryByPage(UserLoginLogQueryDTO queryDTO);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id);
}
