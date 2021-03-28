package cn.javaboy.admin.dao.support;
import cn.javaboy.admin.common.domain.support.reload.entity.ReloadItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * t_reload_item 数据表dao
 *
 * @author listen
 * @date 2018/02/10 09:23
 */
@Component
@Mapper
public interface ReloadItemDao extends BaseMapper<ReloadItemEntity> {}
