package cn.javaboy.admin.common.db.provider;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.util.UtilityElf;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author tangliang
 * @Date 2020/7/13
 * @Version V1.0
 */
public class HikariDataSourceProvider implements DataSourceProvider {

    private ScheduledExecutorService scheduledExecutor;

    private Properties temp;

    private Pattern pattern = Pattern.compile("\\s*([^\\s;]+?)\\s*=\\s*([^;]+)\\s*;?");

    public HikariDataSourceProvider() {
        final ThreadFactory threadFactory = new UtilityElf.DefaultThreadFactory( "Hikari-housekeeper", true);
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, threadFactory, new ThreadPoolExecutor.DiscardPolicy());
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        executor.setRemoveOnCancelPolicy(true);
        scheduledExecutor = executor;
    }

    public HikariDataSourceProvider(Map map) {
        this();
        this.temp = new Properties();
        map.forEach((k, v) -> temp.put(k, v));
        putProp(this.temp, "dataSourceProperties");
        putProp(this.temp, "healthCheckProperties");
    }

    public void setTemp(Properties temp) {
        this.temp = temp;
    }

    @Override
    public DataSource get(String url, String driver, String user, String password) throws Exception {
        Properties properties = new Properties(temp);

        properties.put("jdbcUrl", url);
        properties.put("driverClassName", driver);
        properties.put("username", user);
        properties.put("password", password);

        HikariConfig config = new HikariConfig(properties);
        config.setScheduledExecutor(scheduledExecutor);
        return new HikariDataSource(config);
    }

    @Override
    public void close(DataSource dataSource) {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
    }

    private void putProp(Properties properties, String key) {
        String val = temp.getProperty(key);
        if (!StringUtils.isEmpty(val)) {
            properties.put(key, parseProp(val));
        }
    }

    private Properties parseProp(String s) {
        Properties prop = new Properties();
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            prop.put(matcher.group(1), matcher.group(2));
        }
        return prop;
    }

}
