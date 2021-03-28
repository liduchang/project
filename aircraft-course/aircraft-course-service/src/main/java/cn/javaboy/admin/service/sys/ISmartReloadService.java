package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.support.reload.dto.ReloadItemUpdateDTO;
import cn.javaboy.admin.common.domain.support.reload.vo.ReloadItemVO;
import cn.javaboy.admin.common.domain.support.reload.vo.ReloadResultVO;
import cn.javaboy.admin.common.resp.ResponseDTO;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/12 14:04
 */
public interface ISmartReloadService {

    @PostConstruct
    public void init();

    public ResponseDTO<List<ReloadResultVO>> listReloadItemResult(String tag);

    public void register(Object reload);

    public ResponseDTO<List<ReloadItemVO>> listAllReloadItem();

    public ResponseDTO<String> updateByTag(ReloadItemUpdateDTO updateDTO);

}
