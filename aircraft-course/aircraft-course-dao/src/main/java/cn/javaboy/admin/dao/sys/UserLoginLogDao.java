package cn.javaboy.admin.dao.sys;

import cn.javaboy.admin.common.domain.sys.dto.UserLoginLogQueryDTO;
import cn.javaboy.admin.common.domain.sys.entity.UserLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * [ 用户登录日志 ]
 *
 * @author yandanyang
 * @version 1.0
 * @since JDK1.8
 */
@Mapper
public interface UserLoginLogDao extends BaseMapper<UserLoginLogEntity> {

    /**
     * 分页查询
     * @param queryDTO
     * @return UserLoginLogEntity
    */
    List<UserLoginLogEntity> queryByPage(Page page, @Param("queryDTO") UserLoginLogQueryDTO queryDTO);

    /**
     * 根据id删除
     * @param id
     * @return
    */
    void deleteById(@Param("id") Long id);

    /**
     * 批量删除
     * @param idList
     * @return
    */
    void deleteByIds(@Param("idList") List<Long> idList);
}
