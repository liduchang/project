package cn.javaboy.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: liduchang
 * @Date: 2021/3/10 11:04
 */
@Configuration
@MapperScan("cn.javaboy.admin.dao.*")
public class DaoConfiguration {
}
