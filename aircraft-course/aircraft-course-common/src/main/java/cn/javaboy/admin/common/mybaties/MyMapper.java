package cn.javaboy.admin.common.mybaties;

import cn.javaboy.admin.common.mybaties.oracle.MyOracleMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public interface MyMapper<T> extends Mapper<T>, MyOracleMapper<T> {

    /**
     * 获取目标类
     *
     * @return
     */
    default Class<T> targetClass() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getInterfaces()[0].getGenericInterfaces()[0];
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    /**
     * 根据ID列表获取查询对象
     *
     * @param ids
     * @return
     */
    default List<T> findByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        Example example = new Example(targetClass());
        example.createCriteria().andIn("id", ids);
        return selectByExample(example);
    }

    /**
     * 根据ID列表更新对象
     *
     * @param record
     * @param ids
     */
    default void updateByIdsSelective(T record, List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        Example example = new Example(targetClass());
        example.createCriteria().andIn("id", ids);
        updateByExampleSelective(record, example);
    }

}
