package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.sys.dto.PageResultDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserLoginLogDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserLoginLogQueryDTO;
import cn.javaboy.admin.common.domain.sys.entity.UserLoginLogEntity;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.common.utils.JBPageUtil;
import cn.javaboy.admin.dao.sys.UserLoginLogDao;
import cn.javaboy.admin.service.sys.IUserLoginLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * [ 用户登录日志 ]
 *
 * @author liduchang
 * @version 1.0
 * @since JDK1.8
 */
@Service
public class UserLoginLogService implements IUserLoginLogService {

    @Autowired
    private UserLoginLogDao userLoginLogDao;

    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-05-15 10:25:21
     */
    public ResponseDTO<PageResultDTO<UserLoginLogDTO>> queryByPage(UserLoginLogQueryDTO queryDTO) {
        Page page = JBPageUtil.convert2QueryPage(queryDTO);
        List<UserLoginLogEntity> entities = userLoginLogDao.queryByPage(page, queryDTO);
        List<UserLoginLogDTO> dtoList = JBBeanUtil.copyList(entities, UserLoginLogDTO.class);
        page.setRecords(dtoList);
        PageResultDTO<UserLoginLogDTO> pageResultDTO = JBPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-05-15 10:25:21
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id) {
        userLoginLogDao.deleteById(id);
        return ResponseDTO.succ();
    }

}
