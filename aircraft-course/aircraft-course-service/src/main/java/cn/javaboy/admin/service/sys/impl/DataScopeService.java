package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.constant.DataScopeTypeEnum;
import cn.javaboy.admin.common.domain.constant.DataScopeViewTypeEnum;
import cn.javaboy.admin.common.domain.sys.dto.DataScopeBatchSetDTO;
import cn.javaboy.admin.common.domain.sys.dto.DataScopeBatchSetRoleDTO;
import cn.javaboy.admin.common.domain.sys.dto.DataScopeDTO;
import cn.javaboy.admin.common.domain.sys.entity.DataScopeRoleEntity;
import cn.javaboy.admin.common.domain.sys.vo.DataScopeAndViewTypeVO;
import cn.javaboy.admin.common.domain.sys.vo.DataScopeSelectVO;
import cn.javaboy.admin.common.domain.sys.vo.DataScopeViewTypeVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.ResponseCodeConst;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.dao.sys.DataScopeRoleDao;
import cn.javaboy.admin.service.sys.IDataScopeService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @since JDK1.8
 */
@Service
public class DataScopeService implements IDataScopeService {

    @Autowired
    private DataScopeRoleDao dataScopeRoleDao;

    /**
     * 获取所有可以进行数据范围配置的信息
     *
     * @return
     */
    public ResponseDTO<List<DataScopeAndViewTypeVO>> dataScopeList() {
        List<DataScopeDTO> dataScopeList = this.getDataScopeType();
        List<DataScopeAndViewTypeVO> dataScopeAndTypeList = JBBeanUtil.copyList(dataScopeList, DataScopeAndViewTypeVO.class);
        List<DataScopeViewTypeVO> typeList = this.getViewType();
        dataScopeAndTypeList.forEach(e -> {
            e.setViewTypeList(typeList);
        });
        return ResponseDTO.succData(dataScopeAndTypeList);
    }

    /**
     * 获取当前系统存在的数据可见范围
     *
     * @return
     */
    public List<DataScopeViewTypeVO> getViewType() {
        List<DataScopeViewTypeVO> viewTypeList = Lists.newArrayList();
        DataScopeViewTypeEnum[] enums = DataScopeViewTypeEnum.class.getEnumConstants();
        DataScopeViewTypeVO dataScopeViewTypeDTO;
        for (DataScopeViewTypeEnum viewTypeEnum : enums) {
            dataScopeViewTypeDTO = DataScopeViewTypeVO.builder().viewType(viewTypeEnum.getValue()).viewTypeLevel(viewTypeEnum.getLevel()).viewTypeName(viewTypeEnum.getDesc()).build();
            viewTypeList.add(dataScopeViewTypeDTO);
        }
        Comparator<DataScopeViewTypeVO> comparator = (h1, h2) -> h1.getViewTypeLevel().compareTo(h2.getViewTypeLevel());
        viewTypeList.sort(comparator);
        return viewTypeList;
    }

    public List<DataScopeDTO> getDataScopeType() {
        List<DataScopeDTO> dataScopeTypeList = Lists.newArrayList();
        DataScopeTypeEnum[] enums = DataScopeTypeEnum.class.getEnumConstants();
        DataScopeDTO dataScopeDTO;
        for (DataScopeTypeEnum typeEnum : enums) {
            dataScopeDTO =
                DataScopeDTO.builder().dataScopeType(typeEnum.getValue()).dataScopeTypeDesc(typeEnum.getDesc()).dataScopeTypeName(typeEnum.getName()).dataScopeTypeSort(typeEnum.getSort()).build();
            dataScopeTypeList.add(dataScopeDTO);
        }
        Comparator<DataScopeDTO> comparator = (h1, h2) -> h1.getDataScopeTypeSort().compareTo(h2.getDataScopeTypeSort());
        dataScopeTypeList.sort(comparator);
        return dataScopeTypeList;
    }

    /**
     * 获取某个角色的数据范围设置信息
     *
     * @param roleId
     * @return
     */
    public ResponseDTO<List<DataScopeSelectVO>> dataScopeListByRole(Long roleId) {

        List<DataScopeRoleEntity> dataScopeRoleEntityList = dataScopeRoleDao.listByRoleId(roleId);
        if (CollectionUtils.isEmpty(dataScopeRoleEntityList)) {
            return ResponseDTO.succData(Lists.newArrayList());
        }
        List<DataScopeSelectVO> dataScopeSelects = JBBeanUtil.copyList(dataScopeRoleEntityList, DataScopeSelectVO.class);
        return ResponseDTO.succData(dataScopeSelects);
    }

    /**
     * 批量设置某个角色的数据范围设置信息
     *
     * @param batchSetRoleDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> dataScopeBatchSet(DataScopeBatchSetRoleDTO batchSetRoleDTO) {
        List<DataScopeBatchSetDTO> batchSetList = batchSetRoleDTO.getBatchSetList();
        if (CollectionUtils.isEmpty(batchSetList)) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "缺少配置信息");
        }
        List<DataScopeRoleEntity> dataScopeRoleEntityList = JBBeanUtil.copyList(batchSetList, DataScopeRoleEntity.class);
        dataScopeRoleEntityList.forEach(e -> e.setRoleId(batchSetRoleDTO.getRoleId()));
        dataScopeRoleDao.deleteByRoleId(batchSetRoleDTO.getRoleId());
        dataScopeRoleDao.batchInsert(dataScopeRoleEntityList);
        return ResponseDTO.succ();
    }

}
