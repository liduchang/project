package cn.javaboy.admin.support.aspect;

import cn.javaboy.admin.common.anno.OperateLog;
import cn.javaboy.admin.common.domain.constant.JudgeEnum;
import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import cn.javaboy.admin.common.domain.sys.entity.UserOperateLogEntity;
import cn.javaboy.admin.common.utils.JBRequestTokenUtil;
import cn.javaboy.admin.common.utils.JBStringUtil;
import cn.javaboy.admin.service.sys.impl.LogService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

/**
 * [  操作日志记录处理,对所有OperateLog注解的Controller进行操作日志监控 ]
 *
 * @author liduchang
 * @version 1.0
 * @date
 * @since JDK1.8
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private LogService logService;

//    @Pointcut("execution(* cn.javaboy.admin.controller.*Controller.*(..)))")
    @Pointcut("@annotation(cn.javaboy.admin.common.anno.OperateLog)")
    public void logPointCut() {}

    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            OperateLog operateLog = this.getAnnotationLog(joinPoint);
            if (operateLog == null) {
                return;
            }
            RequestTokenBO requestToken = JBRequestTokenUtil.getRequestUser();
            if (requestToken == null) {
                return;
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String operateMethod = className + "." + methodName;
            Object[] args = joinPoint.getArgs();
            StringBuilder sb = new StringBuilder();
            for (Object obj : args) {
                sb.append(obj.getClass().getSimpleName());
                sb.append("[");
                sb.append(JSON.toJSONString(obj));
                sb.append("]");
            }
            String params = sb.toString();
            String failReason = null;
            Integer result = JudgeEnum.YES.getValue();
            if (e != null) {
                result = JudgeEnum.NO.getValue();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw, true);
                e.printStackTrace(pw);
                failReason = sw.toString();
                pw.flush();
                pw.close();
                sw.flush();
                sw.close();
            }
            UserOperateLogEntity operateLogEntity =
                UserOperateLogEntity.builder().userId(requestToken.getRequestUserId()).userName(requestToken.getUserBO().getActualName()).url(request.getRequestURI()).method(operateMethod).param(params).failReason(failReason).result(result).build();
            ApiOperation apiOperation = this.getApiOperation(joinPoint);
            if (apiOperation != null) {
                operateLogEntity.setContent(apiOperation.value());
            }
            Api api = this.getApi(joinPoint);
            if (api != null) {
                String[] tags = api.tags();
                operateLogEntity.setModule(JBStringUtil.join(tags, ","));
            }
            logService.addLog(operateLogEntity);
        } catch (Exception exp) {
            log.error("保存操作日志异常:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    private OperateLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        OperateLog annotation = method.getAnnotation(OperateLog.class);
//        OperateLog classAnnotation = AnnotationUtils.findAnnotation(method.getDeclaringClass(), OperateLog.class);
        if (method != null) {
            return annotation;
        }
        return null;
    }

    /**
     * swagger API
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private Api getApi(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Api classAnnotation = AnnotationUtils.findAnnotation(method.getDeclaringClass(), Api.class);

        if (method != null) {
            return classAnnotation;
        }
        return null;
    }

    /**
     * swagger ApiOperation
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private ApiOperation getApiOperation(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(ApiOperation.class);
        }
        return null;
    }

}
