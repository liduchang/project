package cn.javaboy.admin.common.db.provider;

import javax.sql.DataSource;

/**
 * 数据源提供者
 * @Author tangliang
 * @Date 2020/7/13
 * @Version V1.0
 */
public interface DataSourceProvider {


    /**
     * 获取数据源
     * @param url
     * @param driver
     * @param user
     * @param password
     * @return
     */
    DataSource get(String url, String driver, String user, String password) throws Exception;

    /**
     * 关闭数据源
     * @param dataSource
     */
    void close(DataSource dataSource);
}
