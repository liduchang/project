package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.support.reload.dto.ReloadItemUpdateDTO;
import cn.javaboy.admin.common.domain.support.reload.entity.ReloadItemEntity;
import cn.javaboy.admin.common.domain.support.reload.entity.ReloadResultEntity;
import cn.javaboy.admin.common.domain.support.reload.vo.ReloadItemVO;
import cn.javaboy.admin.common.domain.support.reload.vo.ReloadResultVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.ResponseCodeConst;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.dao.support.ReloadItemDao;
import cn.javaboy.admin.dao.support.ReloadResultDao;
import cn.javaboy.admin.service.sys.ISmartReloadService;
import cn.javaboy.admin.support.reload.SmartReloadCommand;
import cn.javaboy.admin.support.reload.SmartReloadManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liduchang
 * @Date: 2021/3/12 14:04
 */
@Slf4j
@Service
public class SmartReloadService implements ISmartReloadService {

    @Autowired
    private SmartReloadManager smartReloadManager;

    @Autowired
    private SmartReloadCommand smartReloadCommand;

    @Autowired
    private ReloadItemDao reloadItemDao;

    @Autowired
    private ReloadResultDao reloadResultDao;

    @Value("${smart-reload.time-interval}")
    private Long timeInterval;

    @PostConstruct
    public void init() {
        smartReloadManager.addCommand(smartReloadCommand, 10, timeInterval, TimeUnit.SECONDS);
    }

    /**
     * 注册到SmartReload里
     */
    public void register(Object reload) {
        smartReloadManager.register(reload);
    }

    /**
     * 获取所有 initDefines 项
     *
     * @return
     */
    public ResponseDTO<List<ReloadItemVO>> listAllReloadItem() {
        List<ReloadItemEntity> reloadItemEntityList = reloadItemDao.selectList(null);
        List<ReloadItemVO> reloadItemDTOList = JBBeanUtil.copyList(reloadItemEntityList, ReloadItemVO.class);
        return ResponseDTO.succData(reloadItemDTOList);
    }

    /**
     * 根据 tag
     * 获取所有 initDefines 运行结果
     *
     * @return ResponseDTO
     */
    public ResponseDTO<List<ReloadResultVO>> listReloadItemResult(String tag) {
        ReloadResultEntity query = new ReloadResultEntity();
        query.setTag(tag);
        List<ReloadResultEntity> reloadResultEntityList = reloadResultDao.query(tag);
        List<ReloadResultVO> reloadResultDTOList = JBBeanUtil.copyList(reloadResultEntityList, ReloadResultVO.class);
        return ResponseDTO.succData(reloadResultDTOList);
    }

    /**
     * 通过标签更新标识符
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updateByTag(ReloadItemUpdateDTO updateDTO) {
        ReloadItemEntity entity = new ReloadItemEntity();
        entity.setTag(updateDTO.getTag());
        ReloadItemEntity reloadItemEntity = reloadItemDao.selectById(entity.getTag());
        if (null == reloadItemEntity) {
            return ResponseDTO.wrap(ResponseCodeConst.NOT_EXISTS);
        }
        reloadItemEntity.setIdentification(updateDTO.getIdentification());
        reloadItemEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        reloadItemEntity.setArgs(updateDTO.getArgs());
        reloadItemDao.updateById(reloadItemEntity);
        return ResponseDTO.succ();
    }
}
