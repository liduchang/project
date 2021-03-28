package cn.javaboy.admin.dao.sys;

import cn.javaboy.admin.common.domain.sys.entity.ResourceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 
 * [  ]
 * 
 * @version 1.0
 * @since JDK1.8
 * @author yandanyang
 * @date
 */
@Mapper
public interface ResourceDao extends BaseMapper<ResourceEntity> {

    /**
     * 根据权限key删除
     * @param keyList
     */
    void delByKeyList(@Param("keyList") List<String> keyList);
    /**
     * 根据权限parentkey删除
     * @param keyList
     */
    void delByParentKeyList(@Param("keyList") List<String> keyList);

    /**
     * 批量保存
     * @param privilegeList
     */
    void batchInsert(List<ResourceEntity> privilegeList);

    /**
     * 批量更新
     * @param privilegeList
     */
    void batchUpdate(@Param("updateList") List<ResourceEntity> privilegeList);

    /**
     * 根据父节点key查询
     * @param parentKey
     * @return
     */
    List<ResourceEntity> selectByParentKey(@Param("parentKey") String parentKey);

    /**
     * 根据父节点key查询
     * @param keyList
     * @return
     */
    List<ResourceEntity> selectByKeyList(@Param("keyList") List<String> keyList);

    /**
     * 根据权限key查询
     * @param key
     * @return
     */
    ResourceEntity selectByKey(@Param("key") String key);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    List<ResourceEntity> selectByExcludeType(@Param("type") Integer type);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    List<ResourceEntity> selectByType(@Param("type") Integer type);

    /**
     * 查询所有权限
     * @return
     */
    List<ResourceEntity> selectAll();




}
