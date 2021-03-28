package cn.javaboy.admin.common.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web应用上下文
 * @Author tangliang
 * @Date 2020/1/7
 * @Version V1.0
 */
public interface WebContext {


    /**
     * 获取请求对象
     * @return
     */
    HttpServletRequest getRequest();

    /**
     * 获取响应对象
     * @return
     */
    HttpServletResponse getResponse();
}
