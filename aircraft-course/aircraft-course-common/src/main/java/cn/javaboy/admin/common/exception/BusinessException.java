package cn.javaboy.admin.common.exception;
/**
 * 
 * [ 业务逻辑异常,全局异常拦截后统一返回ResponseCodeConst.SYSTEM_ERROR ]
 * @version 1.0
 * @since JDK1.8
 * @author liduchang
 * @date
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
