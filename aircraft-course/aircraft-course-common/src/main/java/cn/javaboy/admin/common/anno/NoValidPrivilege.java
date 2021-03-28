package cn.javaboy.admin.common.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * [ 不需要权限验证 ]
 *
 * @version 1.0
 * @since JDK1.8
 * @author liduchang
 * @date
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NoValidPrivilege {

}
