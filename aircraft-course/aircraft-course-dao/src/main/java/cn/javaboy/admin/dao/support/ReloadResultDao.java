package cn.javaboy.admin.dao.support;

import cn.javaboy.admin.common.domain.support.reload.entity.ReloadResultEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * t_reload_result 数据表dao
 *
 * @author listen
 * @date 2018/02/10 09:23
 */
@Component
@Mapper
public interface ReloadResultDao extends BaseMapper<ReloadResultEntity> {


    List<ReloadResultEntity> query(@Param("tag") String tag);
}
