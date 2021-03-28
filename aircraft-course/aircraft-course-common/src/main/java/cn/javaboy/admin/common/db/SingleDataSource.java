package cn.javaboy.admin.common.db;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.datasource.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 根据 {@link SingleConnectionDataSource }重写，且获取连接时，当连接关闭后会重新初始化连接。
 * 支持多线程环境，当连接未关闭时，其它线程获取连接会被阻塞。
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see #getConnection()
 * @see java.sql.Connection#close()
 * @see DataSourceUtils#releaseConnection
 */
public class SingleDataSource extends DriverManagerDataSource implements SmartDataSource, DisposableBean {

    /** Create a close-suppressing proxy?. */
    private boolean suppressClose;

    /** Override auto-commit state?. */
    @Nullable
    private Boolean autoCommit;

    /** Wrapped Connection. */
    @Nullable
    private Connection target;

    /** Proxy Connection. */
    @Nullable
    private Connection connection;

    /**
     * 连接正在使用中
     */
    private volatile boolean useing;
    private volatile Thread useThread;


    /** Synchronization monitor for the shared Connection. */
    private final Object connectionMonitor = new Object();


    /**
     * Constructor for bean-style configuration.
     */
    public SingleDataSource() {
    }

    /**
     * Create a new SingleDataSource with the given standard
     * DriverManager parameters.
     * @param url the JDBC URL to use for accessing the DriverManager
     * @param username the JDBC username to use for accessing the DriverManager
     * @param password the JDBC password to use for accessing the DriverManager
     * @param suppressClose if the returned Connection should be a
     * close-suppressing proxy or the physical Connection
     * @see java.sql.DriverManager#getConnection(String, String, String)
     */
    public SingleDataSource(String url, String username, String password, boolean suppressClose) {
        super(url, username, password);
        this.suppressClose = suppressClose;
    }

    /**
     * Create a new SingleDataSource with the given standard
     * DriverManager parameters.
     * @param url the JDBC URL to use for accessing the DriverManager
     * @param suppressClose if the returned Connection should be a
     * close-suppressing proxy or the physical Connection
     * @see java.sql.DriverManager#getConnection(String, String, String)
     */
    public SingleDataSource(String url, boolean suppressClose) {
        super(url);
        this.suppressClose = suppressClose;
    }

    /**
     * Create a new SingleDataSource with a given Connection.
     * @param target underlying target Connection
     * @param suppressClose if the Connection should be wrapped with a Connection that
     * suppresses {@code close()} calls (to allow for normal {@code close()}
     * usage in applications that expect a pooled Connection but do not know our
     * SmartDataSource interface)
     */
    public SingleDataSource(Connection target, boolean suppressClose) {
        Assert.notNull(target, "Connection must not be null");
        this.target = target;
        this.suppressClose = suppressClose;
        this.connection = (suppressClose ? getCloseSuppressingConnectionProxy(target) : target);
    }


    /**
     * Set whether the returned Connection should be a close-suppressing proxy
     * or the physical Connection.
     */
    public void setSuppressClose(boolean suppressClose) {
        this.suppressClose = suppressClose;
    }

    /**
     * Return whether the returned Connection will be a close-suppressing proxy
     * or the physical Connection.
     */
    protected boolean isSuppressClose() {
        return this.suppressClose;
    }

    /**
     * Set whether the returned Connection's "autoCommit" setting should be overridden.
     */
    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = (autoCommit);
    }

    /**
     * Return whether the returned Connection's "autoCommit" setting should be overridden.
     * @return the "autoCommit" value, or {@code null} if none to be applied
     */
    @Nullable
    protected Boolean getAutoCommitValue() {
        return this.autoCommit;
    }


    @Override
    public Connection getConnection() throws SQLException {
        synchronized (this.connectionMonitor) {
            if (this.connection == null) {
                // No underlying Connection -> lazy init via DriverManager.
                initConnection();
            }

            if (suppressClose && useing && Thread.currentThread() != useThread) {
                try {
                    System.out.println("wait");
                    this.connectionMonitor.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

            this.useing = true;
            this.useThread = Thread.currentThread();

            // 如果连接关闭了，重新建立连接
            if (this.connection.isClosed()) {
                initConnection();
            }

            return this.connection;
        }
    }

    /**
     * Specifying a custom username and password doesn't make sense
     * with a single Connection. Returns the single Connection if given
     * the same username and password; throws an SQLException else.
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        if (ObjectUtils.nullSafeEquals(username, getUsername()) &&
                ObjectUtils.nullSafeEquals(password, getPassword())) {
            return getConnection();
        }
        else {
            throw new SQLException("SingleDataSource does not support custom username and password");
        }
    }

    /**
     * This is a single Connection: Do not close it when returning to the "pool".
     */
    @Override
    public boolean shouldClose(Connection con) {
        synchronized (this.connectionMonitor) {
            return (con != this.connection && con != this.target) || useing;
        }
    }

    /**
     * Close the underlying Connection.
     * The provider of this DataSource needs to care for proper shutdown.
     * <p>As this bean implements DisposableBean, a bean factory will
     * automatically invoke this on destruction of its cached singletons.
     */
    @Override
    public void destroy() {
        synchronized (this.connectionMonitor) {
            closeConnection();
        }
    }


    /**
     * Initialize the underlying Connection via the DriverManager.
     */
    public void initConnection() throws SQLException {
        if (getUrl() == null) {
            throw new IllegalStateException("'url' property is required for lazily initializing a Connection");
        }
        synchronized (this.connectionMonitor) {
            closeConnection();
            this.target = getConnectionFromDriver(getUsername(), getPassword());
            prepareConnection(this.target);
            if (logger.isDebugEnabled()) {
                logger.debug("Established shared JDBC Connection: " + this.target);
            }
            this.connection = (isSuppressClose() ? getCloseSuppressingConnectionProxy(this.target) : this.target);
        }
    }

    /**
     * Reset the underlying shared Connection, to be reinitialized on next access.
     */
    public void resetConnection() {
        synchronized (this.connectionMonitor) {
            closeConnection();
            this.target = null;
            this.connection = null;
        }
    }

    /**
     * Prepare the given Connection before it is exposed.
     * <p>The default implementation applies the auto-commit flag, if necessary.
     * Can be overridden in subclasses.
     * @param con the Connection to prepare
     * @see #setAutoCommit
     */
    protected void prepareConnection(Connection con) throws SQLException {
        Boolean autoCommit = getAutoCommitValue();
        if (autoCommit != null && con.getAutoCommit() != autoCommit) {
            con.setAutoCommit(autoCommit);
        }
    }

    /**
     * Close the underlying shared Connection.
     */
    private void closeConnection() {
        this.useing = false;
        if (this.target != null) {
            try {
                this.target.close();
            }
            catch (Throwable ex) {
                logger.info("Could not close shared JDBC Connection", ex);
            }
        }
    }

    /**
     * Wrap the given Connection with a proxy that delegates every method call to it
     * but suppresses close calls.
     * @param target the original Connection to wrap
     * @return the wrapped Connection
     */
    protected Connection getCloseSuppressingConnectionProxy(Connection target) {
        return (Connection) Proxy.newProxyInstance(
                ConnectionProxy.class.getClassLoader(),
                new Class<?>[] {ConnectionProxy.class},
                new SingleDataSource.CloseSuppressingInvocationHandler(target, this));
    }


    /**
     * Invocation handler that suppresses close calls on JDBC Connections.
     */
    private static class CloseSuppressingInvocationHandler implements InvocationHandler {

        private final Connection target;
        private final SingleDataSource dataSource;

        public CloseSuppressingInvocationHandler(Connection target, SingleDataSource dataSource) {
            this.target = target;
            this.dataSource = dataSource;
        }

        @Override
        @Nullable
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Invocation on ConnectionProxy interface coming in...

            if (method.getName().equals("equals")) {
                // Only consider equal when proxies are identical.
                return (proxy == args[0]);
            }
            else if (method.getName().equals("hashCode")) {
                // Use hashCode of Connection proxy.
                return System.identityHashCode(proxy);
            }
            else if (method.getName().equals("unwrap")) {
                if (((Class<?>) args[0]).isInstance(proxy)) {
                    return proxy;
                }
            }
            else if (method.getName().equals("isWrapperFor")) {
                if (((Class<?>) args[0]).isInstance(proxy)) {
                    return true;
                }
            }
            else if (method.getName().equals("close")) {
                // 通知其它线程
//                System.out.println("CloseSuppressingInvocationHandler.invoke close");
                synchronized (dataSource.connectionMonitor) {
                    dataSource.useing = false;
                    dataSource.connectionMonitor.notify();
                }
                // Handle close method: don't pass the call on.
                return null;
            }
            else if (method.getName().equals("isClosed")) {
                return target.isClosed();
            }
            else if (method.getName().equals("getTargetConnection")) {
                // Handle getTargetConnection method: return underlying Connection.
                return this.target;
            }

            // Invoke method on target Connection.
            try {
                return method.invoke(this.target, args);
            }
            catch (InvocationTargetException ex) {
                throw ex.getTargetException();
            }
        }
    }

}

