package cn.javaboy.admin;

import cn.javaboy.admin.common.mybaties.MybatisSqlExecutor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 10:54
 */

@Configuration
public class MybatisConfiguration {

    @ConditionalOnMissingBean(MybatisSqlExecutor.class)
    @Bean
    public MybatisSqlExecutor sqlExecutor(SqlSessionTemplate sqlSessionTemplate) {
        return new MybatisSqlExecutor(sqlSessionTemplate);
    }
}
