package cn.javaboy.admin.common.mybaties;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * mybatis sql执行器
 *
 * @Author tangliang
 * @Date 2020/3/9
 * @Version V1.0
 */
public class MybatisSqlExecutor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SqlSession sqlSession;

    public MybatisSqlExecutor(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    public <T> List<T> selectList(String mapperId, Object param) {
        return sqlSession.selectList(mapperId, param);
    }


    public void registerSelectMapper(String sql, String mapperId, Class<?> resultType) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append(sql);
        sb.append("</script>");
        Configuration configuration = sqlSession.getConfiguration();
        LanguageDriver languageDriver = configuration.getDefaultScriptingLanguageInstance();  // 2. languageDriver 是帮助我们实现dynamicSQL的关键
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sb.toString(), Map.class);  //  泛型化入参

        // 加强逻辑 ： 一定要防止 MappedStatement 重复问题
        synchronized (configuration) {
            // 防止并发插入多次
            if (configuration.hasStatement(mapperId)) {
                configuration.getMappedStatementNames().remove(mapperId);
            }

            List<ResultMap> resultMaps = Arrays.asList(new ResultMap
                    .Builder(configuration,"defaultResultMap", resultType, new ArrayList<>(0))
                    .build());

            // 构建一个 select 类型的ms ，通过制定SqlCommandType.SELECT
            // 注意！！-》 这里可以指定你想要的任何配置，比如cache,CALLABLE,
            MappedStatement ms = new MappedStatement
                    .Builder(configuration, mapperId, sqlSource, SqlCommandType.SELECT)   // -》 注意，这里是SELECT,其他的UPDATE\INSERT 还需要指定成别的
                    .resultMaps(resultMaps)
                    .build();

            configuration.addMappedStatement(ms); // 加入到此中去
        }
    }

    public void close() {
        sqlSession.close();
    }
}
