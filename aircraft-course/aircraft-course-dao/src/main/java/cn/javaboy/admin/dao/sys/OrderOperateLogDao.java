package cn.javaboy.admin.dao.sys;

import cn.javaboy.admin.common.domain.sys.entity.OrderOperateLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 各种单据操作记录
 * Mapper 接口
 * </p>
 *
 * @author anders
 * @since 2018-01-09
 */
@Mapper
public interface OrderOperateLogDao extends BaseMapper<OrderOperateLogEntity> {

    List<OrderOperateLogEntity> listOrderOperateLogsByOrderTypeAndOrderId(@Param("orderId") Long orderId, @Param("orderTypeList") List<Integer> orderTypeList);

    List<OrderOperateLogEntity> listOrderOperateLogsByOrderTypeAndOrderIds(@Param("orderIds") List<Long> orderIds, @Param("orderTypeList") List<Integer> orderTypeList);

    void batchInsert(List<OrderOperateLogEntity> list);

}
