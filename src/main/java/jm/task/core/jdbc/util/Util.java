package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USER = "testuser";
    private static final String PASS = "testpass";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public Connection getConnection()  {
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private static Properties getProperties() {
        Properties properties = new Properties();

        properties.put(Environment.DRIVER, DRIVER);
        properties.put(Environment.URL, URL);
        properties.put(Environment.USER, USER);
        properties.put(Environment.PASS, PASS);
        return properties;
    }

    public static SessionFactory getSessionFactory() {
        return new Configuration().setProperties(getProperties()).buildSessionFactory();
    }
}