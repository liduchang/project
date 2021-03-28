package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.dto.*;
import cn.javaboy.admin.common.domain.sys.vo.PositionResultVO;
import cn.javaboy.admin.common.resp.ResponseDTO;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/12 10:02
 */
public interface IPositionService {

    public ResponseDTO<PageResultDTO<PositionResultVO>> queryPositionByPage(PositionQueryDTO queryDTO);

    public ResponseDTO<String> addPosition(PositionAddDTO addDTO);

    public ResponseDTO<String> updatePosition(PositionUpdateDTO updateDTO);

    public ResponseDTO<PositionResultVO> queryPositionById(Long id);

    public ResponseDTO<String> removePosition(Long id);

    public ResponseDTO<String> addPositionRelation(PositionRelationAddDTO positionRelAddDTO);

    public ResponseDTO<String> removePositionRelation(Long userId);

    public List<PositionRelationResultDTO> queryPositionByUserId(Long userId);
}
