package cn.javaboy.admin.common.utils;

import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public class JBRequestTokenUtil {

    private static final String USER_KEY = "smart_admin_user";

    private static ThreadLocal<RequestTokenBO> RequestUserThreadLocal = new ThreadLocal<>();

    public static void setUser(HttpServletRequest request, RequestTokenBO requestToken) {
        request.setAttribute(USER_KEY, requestToken);
        RequestUserThreadLocal.set(requestToken);
    }

    public static RequestTokenBO getRequestUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (request != null) {
                return (RequestTokenBO) request.getAttribute(USER_KEY);
            }
        }
        return null;
    }

}
