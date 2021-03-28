package cn.javaboy.admin.common.db.provider;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author tangliang
 * @Date 2020/7/13
 * @Version V1.0
 */
@SuppressWarnings("unchecked")
public class DruidDataSourceProvider implements DataSourceProvider {

    private Map temp;

    public DruidDataSourceProvider() {
    }

    public DruidDataSourceProvider(Map map) {
        this.temp = new HashMap();
    }

    public void setTemp(Map temp) {
        this.temp = temp;
    }

    @Override
    public DataSource get(String url, String driver, String user, String password) throws Exception {
        Map properties = new HashMap(temp);

        properties.put("url", url);
        properties.put("driverClassName", driver);
        properties.put("username", user);
        properties.put("password", password);

        return DruidDataSourceFactory.createDataSource(properties);
    }

    @Override
    public void close(DataSource dataSource) {
        if (dataSource instanceof DruidDataSource) {
            ((DruidDataSource) dataSource).close();
        }
    }


}
