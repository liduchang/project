package cn.javaboy.admin.support.reload.interfaces;

/**
 * SmartReloadThreadLogger 日志类
 */
public interface SmartReloadThreadLogger {

    void error(String string);

    void error(String string, Throwable e);

}
