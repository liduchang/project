package cn.javaboy.admin.dao.sys;

import cn.javaboy.admin.common.domain.sys.dto.PositionQueryDTO;
import cn.javaboy.admin.common.domain.sys.dto.PositionRelationAddDTO;
import cn.javaboy.admin.common.domain.sys.dto.PositionRelationQueryDTO;
import cn.javaboy.admin.common.domain.sys.dto.PositionRelationResultDTO;
import cn.javaboy.admin.common.domain.sys.entity.PositionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zzr
 */
@Mapper
public interface PositionDao extends BaseMapper<PositionEntity> {

    /**
     * 查询岗位列表
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<PositionEntity> selectByPage(Page page, @Param("queryDTO") PositionQueryDTO queryDTO);

    /**
     * 查询岗位与人员关系
     *
     * @param positionRelationQueryDTO
     * @return
     */
    List<PositionRelationResultDTO> selectRelation(PositionRelationQueryDTO positionRelationQueryDTO);

    /**
     * 批量查询员工岗位信息
     * @param userIdList
     * @return
     */
    List<PositionRelationResultDTO> selectUserRelation(@Param("userIdList") List<Long> userIdList);

    /**
     * 批量添加岗位 人员 关联关系
     *
     * @param positionRelationAddDTO
     * @return
     */
    Integer insertBatchRelation(@Param("batchDTO") PositionRelationAddDTO positionRelationAddDTO);

    /**
     * 删除指定人员的 岗位关联关系
     *
     * @param userId
     * @return
     */
    Integer deleteRelationByUserId(@Param("userId") Long userId);

}
