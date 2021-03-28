package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.dto.DataScopeBatchSetRoleDTO;
import cn.javaboy.admin.common.domain.sys.dto.DataScopeDTO;
import cn.javaboy.admin.common.domain.sys.vo.DataScopeAndViewTypeVO;
import cn.javaboy.admin.common.domain.sys.vo.DataScopeSelectVO;
import cn.javaboy.admin.common.domain.sys.vo.DataScopeViewTypeVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/16 11:34
 */
public interface IDataScopeService {

    public ResponseDTO<List<DataScopeAndViewTypeVO>> dataScopeList();

    public List<DataScopeViewTypeVO> getViewType();

    public List<DataScopeDTO> getDataScopeType();

    public ResponseDTO<List<DataScopeSelectVO>> dataScopeListByRole(Long roleId);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> dataScopeBatchSet(DataScopeBatchSetRoleDTO batchSetRoleDTO);
}
