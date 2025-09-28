package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

private SessionFactory sessionFactory = new Util().getSessionFactory();
private static final String createTable = "CREATE TABLE IF NOT EXISTS user (" +
        "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
        "name VARCHAR(255), " +
        "age INT)";

private static final String dropTable = "DROP USER IF EXISTS user";
private static final String cleanTable = "delete from User";

public UserDaoHibernateImpl() {

}

@Override
public void createUsersTable() {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        session.createSQLQuery(createTable).executeUpdate();
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();

    }
}

@Override
public void dropUsersTable() {
    try (Session session = sessionFactory.openSession()) {
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery(dropTable).executeUpdate();
        transaction.commit();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@Override
public void saveUser(String name, String lastName, byte age) {
    User user = new User();
    user.setName(name);
    user.setLastName(lastName);
    user.setAge(age);
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    }

}

@Override
public void removeUserById(long id) {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}

@Override
public List<User> getAllUsers() {
    Session session = sessionFactory.openSession();
    return session.createQuery("from User", User.class).list();
}

@Override
public void cleanUsersTable() {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        session.createQuery(cleanTable).executeUpdate();
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
}
}
