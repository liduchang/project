package cn.javaboy.admin.common.db.provider;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库资源池
 * @Author tangliang
 * @Date 2020/7/8
 * @Version V1.0
 */
public class DataSourcePool implements DataSourceProvider {

    /**
     * 代理
     */
    protected DataSourceProvider proxy;

    /* key: url + user
     */
    protected volatile Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public void setProxy(DataSourceProvider proxy) {
        this.proxy = proxy;
    }

    @Override
    public DataSource get(String url, String driver, String user, String password) throws Exception {
        String key = url + ":" + user;
        if (dataSourceMap.containsKey(key)) {
            return dataSourceMap.get(key);
        }

        synchronized (this) {
            if (dataSourceMap.containsKey(key)) {
                return dataSourceMap.get(key);
            }
            DataSource dataSource = proxy.get(url, driver, user, password);
            dataSourceMap.put(key, dataSource);
            return dataSource;
        }
    }

    @Override
    public void close(DataSource dataSource) {
        proxy.close(dataSource);
    }

    public void clear() {
        Map<String, DataSource> oldMap = this.dataSourceMap ;
        this.dataSourceMap = new ConcurrentHashMap<>();
        oldMap.forEach((k, ds) -> proxy.close(ds));
    }
}
