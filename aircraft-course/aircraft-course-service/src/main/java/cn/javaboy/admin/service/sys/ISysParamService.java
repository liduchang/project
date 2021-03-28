package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.constant.SysParamEnum;
import cn.javaboy.admin.common.domain.sys.dto.*;
import cn.javaboy.admin.common.domain.sys.vo.SysParamVO;
import cn.javaboy.admin.common.resp.ResponseDTO;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 14:00
 */
public interface ISysParamService {

    public ResponseDTO<PageResultDTO<SysParamVO>> getSysParamPage(SysParamQueryDTO queryDTO);

    public ResponseDTO<SysParamVO> selectByKey(String configKey);

    public <T> T selectByKey2Obj(String configKey, Class<T> clazz);

    public SysParamDTO getCacheByKey(SysParamEnum.Key key);

    public ResponseDTO<String> addSysParam(SysParamAddDTO configAddDTO);

    public ResponseDTO<String> updateSysParam(SysParamUpdateDTO updateDTO);

    public ResponseDTO<List<SysParamVO>> getListByGroup(String group);

    public List<SysParamDTO> getListByGroup(SysParamEnum.Group group);

}
