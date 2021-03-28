package cn.javaboy.admin.common.mybaties.oracle;

import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

@RegisterMapper
public interface MyOracleMapper<T> {

    @InsertProvider(type = MyOracleProvider.class, method = "dynamicSQL")
    int insertList(List<? extends T> list);
}
