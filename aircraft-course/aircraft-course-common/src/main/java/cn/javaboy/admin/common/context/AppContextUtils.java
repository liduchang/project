package cn.javaboy.admin.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * @Author tangliang
 * @Date 2020/1/8
 * @Version V1.0
 */
public class AppContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContextUtils.context = applicationContext;
    }


    /**
     * 	获取 context
     * @return
     */
    public static ApplicationContext getContext() {
        return context;
    }


    /**
     * 	获取spring context 中bean实例
     * @param requiredType
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    /**
     * 	获取spring context 中bean实例
     * @param beanName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }



    /**
     * 	获取spring context 中bean实例
     * @param requiredType
     * @return
     */
    public static <T> Collection<T> getBeans(Class<T> requiredType) {
        return context.getBeansOfType(requiredType).values();
    }

    /**
     * 获取Http request对象
     */
    public static HttpServletRequest getRequest() {
        try {
            for (WebContext webContext : AppContextUtils.getBeans(WebContext.class)) {
                if (webContext.getRequest() != null) {
                    return webContext.getRequest();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取Http response对象
     */
    public static HttpServletResponse getResponse() {
        try {
            for (WebContext webContext : AppContextUtils.getBeans(WebContext.class)) {
                if (webContext.getResponse() != null) {
                    return webContext.getResponse();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }


    /**
     *
     */
    public static String getParameter(String name) {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getParameter(name) ;
    }

}
