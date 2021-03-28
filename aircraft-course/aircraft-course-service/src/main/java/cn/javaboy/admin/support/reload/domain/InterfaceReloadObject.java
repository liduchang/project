package cn.javaboy.admin.support.reload.domain;

import cn.javaboy.admin.support.reload.domain.entity.ReloadItem;
import cn.javaboy.admin.support.reload.domain.entity.SmartReloadResult;
import cn.javaboy.admin.support.reload.interfaces.SmartReloadable;

/**
 * Reload 处理程序的实现类
 * 用于处理以接口实现的处理类
 *
 * @author zhuoda
 */
public class InterfaceReloadObject extends AbstractSmartReloadObject {

    private SmartReloadable object;

    public InterfaceReloadObject(SmartReloadable object) {
        super();
        this.object = object;
    }

    @Override
    public SmartReloadResult reload(ReloadItem reloadItem) {
        SmartReloadResult reloadResult = new SmartReloadResult();
        reloadResult.setArgs(reloadItem.getArgs());
        reloadResult.setIdentification(reloadItem.getIdentification());
        reloadResult.setTag(reloadItem.getTag());
        try {
            boolean res = object.reload(reloadItem);
            reloadResult.setResult(res);
        } catch (Throwable e) {
            reloadResult.setException(getStackTrace(e));
        }
        return reloadResult;
    }

}
