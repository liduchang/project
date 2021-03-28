package cn.javaboy.admin.common.anno;

import java.lang.annotation.*;

/**
 * [ 用户操作日志 ]
 *
 * @author liduchang
 * @version 1.0
 * @date
 * @since JDK1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface OperateLog {

}
