package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

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
private SessionFactory sessionFactory = null;


public Connection getConnection() {
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
    properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
    properties.put(Environment.HBM2DDL_AUTO, "create-drop");
    properties.put(Environment.SHOW_SQL, "true");
    properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
    properties.put(Environment.FORMAT_SQL, "true");
    properties.put(Environment.USE_SQL_COMMENTS, "true");
    return properties;
}

public SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
        try {
            Configuration configuration = new Configuration();
            configuration.setProperties(getProperties());
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(getProperties()).build();
            Metadata metadata = new MetadataSources(registry)
                    .addAnnotatedClass(User.class)
                    .buildMetadata();
            sessionFactory = metadata.buildSessionFactory();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return sessionFactory;
}
}