package cn.javaboy.admin.dao.sys;

import cn.javaboy.admin.common.domain.sys.entity.DataScopeRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @since JDK1.8
 */
@Mapper
@Component
public interface DataScopeRoleDao extends BaseMapper<DataScopeRoleEntity> {

    /**
     * 获取某个角色的设置信息
     * @param roleId
     * @return
     */
    List<DataScopeRoleEntity> listByRoleId(@Param("roleId") Long roleId);

    /**
     * 获取某批角色的所有数据范围配置信息
     * @param roleIdList
     * @return
     */
    List<DataScopeRoleEntity> listByRoleIdList(@Param("roleIdList") List<Long> roleIdList);

    /**
     * 删除某个角色的设置信息
     * @param roleId
     * @return
     */
    void deleteByRoleId(@Param("roleId") Long roleId);


    /**
     * 批量添加设置信息
     * @param list
     */
    void batchInsert(@Param("list") List<DataScopeRoleEntity> list);
}
