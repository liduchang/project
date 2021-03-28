package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.sys.dto.*;
import cn.javaboy.admin.common.domain.sys.entity.PositionEntity;
import cn.javaboy.admin.common.domain.sys.vo.PositionResultVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.PositionResponseCodeConst;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.common.utils.JBPageUtil;
import cn.javaboy.admin.dao.sys.PositionDao;
import cn.javaboy.admin.service.sys.IPositionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: liduchang
 * @Date: 2021/3/12 10:02
 */
@Service
public class PositionService implements IPositionService {

    @Autowired
    private PositionDao positionDao;

    /**
     * 查询岗位
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<PositionResultVO>> queryPositionByPage(PositionQueryDTO queryDTO) {
        Page page = JBPageUtil.convert2QueryPage(queryDTO);
        List<PositionEntity> entityList = positionDao.selectByPage(page, queryDTO);
        page.setRecords(entityList.stream().map(e -> JBBeanUtil.copy(e, PositionResultVO.class)).collect(Collectors.toList()));
        PageResultDTO<PositionResultVO> pageResultDTO = JBPageUtil.convert2PageResult(page);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 新增岗位
     *
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> addPosition(PositionAddDTO addDTO) {
        PositionEntity positionEntity = JBBeanUtil.copy(addDTO, PositionEntity.class);
        positionDao.insert(positionEntity);
        return ResponseDTO.succ();
    }

    /**
     * 修改岗位
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updatePosition(PositionUpdateDTO updateDTO) {
        PositionEntity positionEntity = JBBeanUtil.copy(updateDTO, PositionEntity.class);
        positionDao.updateById(positionEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public ResponseDTO<PositionResultVO> queryPositionById(Long id) {
        return ResponseDTO.succData(JBBeanUtil.copy(positionDao.selectById(id), PositionResultVO.class));
    }

    /**
     * 删除岗位
     */
    public ResponseDTO<String> removePosition(Long id) {
        //查询是否还有人关联该岗位
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setPositionId(id);
        List<PositionRelationResultDTO> dtoList = positionDao.selectRelation(positionRelationQueryDTO);
        if (CollectionUtils.isNotEmpty(dtoList)) {
            return ResponseDTO.wrap(PositionResponseCodeConst.REMOVE_DEFINE);
        }
        positionDao.deleteById(id);
        return ResponseDTO.succ();
    }

    /**
     * 添加岗位关联关系
     *
     * @param positionRelAddDTO
     * @return
     */
    public ResponseDTO<String> addPositionRelation(PositionRelationAddDTO positionRelAddDTO) {
        positionDao.insertBatchRelation(positionRelAddDTO);
        return ResponseDTO.succ();
    }

    /**
     * 删除指定用户的岗位关联关系
     *
     * @param userId
     * @return
     */
    public ResponseDTO<String> removePositionRelation(Long userId) {
        positionDao.deleteRelationByUserId(userId);
        return ResponseDTO.succ();
    }

    /**
     * 根据员工ID查询 所关联的岗位信息
     *
     * @param userId
     * @return
     */
    public List<PositionRelationResultDTO> queryPositionByUserId(Long userId) {
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setUserId(userId);
        List<PositionRelationResultDTO> positionRelationList = positionDao.selectRelation(positionRelationQueryDTO);
        return positionRelationList;
    }
}
