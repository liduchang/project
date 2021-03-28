package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.sys.dto.PageResultDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserOperateLogDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserOperateLogQueryDTO;
import cn.javaboy.admin.common.domain.sys.entity.UserOperateLogEntity;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.common.utils.JBPageUtil;
import cn.javaboy.admin.dao.sys.UserOperateLogDao;
import cn.javaboy.admin.service.sys.IUserOperateLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * [用户操作日志管理类]
 * @author liduchang
 * @version 1.0
 * @since JDK1.8
 */
@Service
public class UserOperateLogService implements IUserOperateLogService {

    @Autowired
    private UserOperateLogDao userOperateLogDao;

    /**
     * @author liduchang
     * @description 分页查询
     * @date 2019-05-15 11:32:14
     */
    public ResponseDTO<PageResultDTO<UserOperateLogDTO>> queryByPage(UserOperateLogQueryDTO queryDTO) {
        Page page = JBPageUtil.convert2QueryPage(queryDTO);
        List<UserOperateLogEntity> entities = userOperateLogDao.queryByPage(page, queryDTO);
        List<UserOperateLogDTO> dtoList = JBBeanUtil.copyList(entities, UserOperateLogDTO.class);
        page.setRecords(dtoList);
        PageResultDTO<UserOperateLogDTO> pageResultDTO = JBPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * @author liduchang
     * @description 添加
     * @date 2019-05-15 11:32:14
     */
    public ResponseDTO<String> add(UserOperateLogDTO addDTO) {
        UserOperateLogEntity entity = JBBeanUtil.copy(addDTO, UserOperateLogEntity.class);
        userOperateLogDao.insert(entity);
        return ResponseDTO.succ();
    }

    /**
     * @author liduchang
     * @description 编辑
     * @date 2019-05-15 11:32:14
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(UserOperateLogDTO updateDTO) {
        UserOperateLogEntity entity = JBBeanUtil.copy(updateDTO, UserOperateLogEntity.class);
        userOperateLogDao.updateById(entity);
        return ResponseDTO.succ();
    }

    /**
     * @author liduchang
     * @description 删除
     * @date 2019-05-15 11:32:14
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long id) {
        userOperateLogDao.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * @author liduchang
     * @description 根据ID查询
     * @date 2019-05-15 11:32:14
     */
    public ResponseDTO<UserOperateLogDTO> detail(Long id) {
        UserOperateLogEntity entity = userOperateLogDao.selectById(id);
        UserOperateLogDTO dto = JBBeanUtil.copy(entity, UserOperateLogDTO.class);
        return ResponseDTO.succData(dto);
    }
}
