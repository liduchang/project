package cn.javaboy.admin.common.exception;

import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.ResponseCodeConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * [ 全局异常拦截 ]
 *
 * @author liduchang
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 添加全局异常处理流程
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseDTO exceptionHandler(Exception e) {
        log.error("error:", e);

        // http 请求方式错误
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return ResponseDTO.wrap(ResponseCodeConst.REQUEST_METHOD_ERROR);
        }
        // 参数类型错误
        if (e instanceof TypeMismatchException) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM);
        }
        // json 格式错误
        if (e instanceof HttpMessageNotReadableException) {
            return ResponseDTO.wrap(ResponseCodeConst.JSON_FORMAT_ERROR);
        }
        // 参数校验未通过
        if (e instanceof MethodArgumentNotValidException) {
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
            List<String> msgList = fieldErrors.stream().map(FieldError :: getDefaultMessage).collect(Collectors.toList());
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, String.join(",", msgList));
        }
        if (e instanceof BusinessException) {
            return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_ERROR);
        }

        return ResponseDTO.wrap(ResponseCodeConst.SYSTEM_ERROR);
    }
}
