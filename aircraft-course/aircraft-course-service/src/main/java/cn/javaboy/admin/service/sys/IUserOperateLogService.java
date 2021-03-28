package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.dto.PageResultDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserOperateLogDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserOperateLogQueryDTO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: liduchang
 * @Date: 2021/3/15 14:33
 */
public interface IUserOperateLogService {

    public ResponseDTO<PageResultDTO<UserOperateLogDTO>> queryByPage(UserOperateLogQueryDTO queryDTO);

    public ResponseDTO<String> add(UserOperateLogDTO addDTO);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(UserOperateLogDTO updateDTO);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id);

    public ResponseDTO<UserOperateLogDTO> detail(Long id);
}
