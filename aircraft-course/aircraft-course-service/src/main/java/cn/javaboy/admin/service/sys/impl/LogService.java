package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.sys.entity.OrderOperateLogEntity;
import cn.javaboy.admin.common.domain.sys.entity.UserLoginLogEntity;
import cn.javaboy.admin.common.domain.sys.entity.UserOperateLogEntity;
import cn.javaboy.admin.common.utils.JBThreadFactory;
import cn.javaboy.admin.dao.sys.OrderOperateLogDao;
import cn.javaboy.admin.dao.sys.UserLoginLogDao;
import cn.javaboy.admin.dao.sys.UserOperateLogDao;
import cn.javaboy.admin.service.sys.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liduchang
 * @Date: 2021/3/12 15:24
 */
@Slf4j
@Service
public class LogService implements ILogService {

    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private UserLoginLogDao userLoginLogDao;

    @Autowired
    private OrderOperateLogDao orderOperateLogDao;

    @Autowired
    private UserOperateLogDao userOperateLogDao;

    @PostConstruct
    void init() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2000), JBThreadFactory.create("LogAspect"));
        }
    }

    @PreDestroy
    void destroy() {
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
            threadPoolExecutor = null;
        }
    }

    public void addLog(Object object) {
        try {
            if (object instanceof UserLoginLogEntity) {
                threadPoolExecutor.execute(() -> userLoginLogDao.insert((UserLoginLogEntity) object));
            }
            if (object instanceof OrderOperateLogEntity) {
                threadPoolExecutor.execute(() -> orderOperateLogDao.insert((OrderOperateLogEntity) object));
            }
            if (object instanceof UserOperateLogEntity) {
                threadPoolExecutor.execute(() -> userOperateLogDao.insert((UserOperateLogEntity) object));
            }
        } catch (Throwable e) {
            log.error("userLogAfterAdvice:{}", e);
        }
    }
}
