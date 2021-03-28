package cn.javaboy.admin.dao.sys;

import cn.javaboy.admin.common.domain.sys.dto.SysParamQueryDTO;
import cn.javaboy.admin.common.domain.sys.entity.SysParamEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 14:04
 */
@Mapper
public interface SysParamDao extends BaseMapper<SysParamEntity> {

    /**
     * 查询所有系统配置（分页）
     *
     * @param page
     * @return
     */
    List<SysParamEntity> selectSystemSettingList(Page page, @Param("queryDTO") SysParamQueryDTO queryDTO);

    /**
     * 根据key查询获取数据
     *
     * @param key
     * @return
     */
    SysParamEntity getByKey(@Param("key") String key);

    /**
     * 根据key查询获取数据  排除掉某個id的数据
     * @param key
     * @param excludeId
     * @return
     */
    SysParamEntity getByKeyExcludeId(@Param("key") String key,@Param("excludeId") Long excludeId);
    /**
     * 查询所有系统配置
     *
     * @return
     */
    List<SysParamEntity> selectAll();

    /**
     * 根据分组查询所有系统配置
     * @param group
     * @return
     */
    List<SysParamEntity> getListByGroup(String group);


    SysParamEntity  selectByKeyAndGroup(@Param("configKey") String configKey, @Param("group") String group);
}
