package cn.javaboy.admin.dao.sys;

import cn.javaboy.admin.common.domain.sys.entity.RoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * [  ]
 *
 * @author liduchang
 * @version 1.0
 * @since JDK1.8
 */
@Mapper
@Component
public interface RoleDao extends BaseMapper<RoleEntity> {

    RoleEntity getByRoleName(@Param("roleName") String name);

}
