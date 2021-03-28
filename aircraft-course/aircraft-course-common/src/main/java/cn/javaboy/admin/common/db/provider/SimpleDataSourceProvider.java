package cn.javaboy.admin.common.db.provider;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author tangliang
 * @Date 2020/7/13
 * @Version V1.0
 */
public class SimpleDataSourceProvider implements DataSourceProvider {


    @Override
    public DataSource get(String url, String driver, String user, String password) throws Exception {
        Class<? extends Driver> driverClass = (Class<? extends Driver>) Class.forName(driver);
        SimpleDataSource dataSource = new SimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClass(driverClass);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Override
    public void close(DataSource dataSource) {

    }

    class SimpleDataSource extends SimpleDriverDataSource {

        @Override
        protected Connection getConnectionFromDriver(Properties props) throws SQLException {
            // 设置socket timeout
            if (getUrl().contains("oracle")) {
                // 去获取数据库链接的时候，发现还没有达到最大链接数，还需要重新创建链接的创建时间
                props.setProperty("oracle.net.CONNECT_TIMEOUT", "20000");
                // 这个参数实际上是从socket读取数据的时间，总体上说这个时间应该包含了query-timeout的时间。如果这个时间配置得比query-timeout短的话就会出现query-timeout的设置值无效
                props.setProperty("oracle.jdbc.ReadTimeout", "20000");
            }
            return super.getConnectionFromDriver(props);
        }
    }
}
