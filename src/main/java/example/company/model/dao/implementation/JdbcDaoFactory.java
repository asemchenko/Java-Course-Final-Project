package example.company.model.dao.implementation;

import example.company.model.dao.DaoFactory;
import example.company.model.dao.UserDao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoFactory implements DaoFactory {
    private Connection connection;
    private UserDao userDao;

    public JdbcDaoFactory(Connection connection) {
        this.connection = connection;
    }

    private static Connection getConnectionFromPool() {
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/hotello");
            return ds.getConnection();
        } catch (NamingException | SQLException e) {
            // FIXME как то это не оч
            throw new RuntimeException(e);
        }
    }

    public static DaoFactory getFactory() {
        return new JdbcDaoFactory(getConnectionFromPool());
    }

    @Override
    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new JdbcUserDao(getCurrentConnection());
        }
        return userDao;
    }

    private Connection getCurrentConnection() {
        if (connection == null) {
            connection = getConnectionFromPool();
        }
        return connection;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
