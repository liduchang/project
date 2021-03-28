package cn.javaboy.admin.support.reload;

import cn.javaboy.admin.common.domain.support.reload.entity.ReloadItemEntity;
import cn.javaboy.admin.common.domain.support.reload.entity.ReloadResultEntity;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.dao.support.ReloadItemDao;
import cn.javaboy.admin.dao.support.ReloadResultDao;
import cn.javaboy.admin.support.reload.domain.entity.ReloadItem;
import cn.javaboy.admin.support.reload.domain.entity.SmartReloadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Smart Reload 业务
 *
 * @author listen
 * @date 2018/02/10 09:18
 */
@Component
public class SmartReloadCommand extends AbstractSmartReloadCommand4Spring {

    @Autowired
    private ReloadItemDao reloadItemDao;

    @Autowired
    private ReloadResultDao reloadResultDao;

    /**
     * 读取数据库中SmartReload项
     *
     * @return List<ReloadItem>
     */
    @Override
    public List<ReloadItem> readReloadItem() {
        List<ReloadItemEntity> reloadItemEntityList = reloadItemDao.selectList(null);
        return JBBeanUtil.copyList(reloadItemEntityList, ReloadItem.class);
    }

    /**
     * 保存reload结果
     *
     * @param smartReloadResult
     */
    @Override
    public void handleReloadResult(SmartReloadResult smartReloadResult) {
        ReloadResultEntity reloadResultEntity = JBBeanUtil.copy(smartReloadResult, ReloadResultEntity.class);
        reloadResultDao.insert(reloadResultEntity);
    }
}
