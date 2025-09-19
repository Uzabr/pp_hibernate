package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private Connection connection = null;

    public  Connection getConnection()  {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    private static Properties getProperties() {
        Properties properties = new Properties();

        properties.put(Environment.DRIVER, DRIVER);
        properties.put(Environment.URL, URL);
        properties.put(Environment.USER, USER);
        properties.put(Environment.PASS, PASS);
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        return properties;
    }

    public  SessionFactory getSessionFactory() {
        return new Configuration().setProperties(getProperties()).addAnnotatedClass(User.class).buildSessionFactory();
    }
}