package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Util util = new Util();
    private final String createTable = "CREATE TABLE IF NOT EXISTS USER (USER_ID INT NOT NULL AUTO_INCREMENT, NAME VARCHAR(40) NOT NULL, LAST_NAME VARCHAR(60) NOT NULL, AGE TINYINT NOT NULL, PRIMARY KEY(USER_ID))";
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction trs = session.beginTransaction();
        session.createNativeQuery(createTable).executeUpdate();
        trs.commit();
        session.close();
        sessionFactory.close();
    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
