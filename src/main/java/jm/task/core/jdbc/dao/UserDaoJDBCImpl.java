package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection conn = new Util().getConnection();

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS USER (USER_ID INT NOT NULL AUTO_INCREMENT, NAME VARCHAR(40) NOT NULL, LAST_NAME VARCHAR(60) NOT NULL, AGE TINYINT NOT NULL, PRIMARY KEY(USER_ID))";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS USER";
    private static final String INSERT_TABLE = "INSERT INTO USER (NAME, LAST_NAME, AGE) VALUES (?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM USER WHERE USER_ID = ?";
    private static final String SELECT_USER = "SELECT NAME, LAST_NAME, AGE FROM USER";
    private static final String CLEAN_TABLE = "DELETE FROM USER";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_TABLE);
        } catch (SQLException er) {
            er.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(DROP_TABLE);
        } catch (SQLException er) {
            er.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pst = conn.prepareStatement(INSERT_TABLE)) {
            conn.setAutoCommit(false);
            pst.setString(1, name);
            pst.setString(2, lastName);
            pst.setByte(3, age);
            pst.executeUpdate();
            conn.commit();
        } catch (SQLException er) {
            er.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement prst = conn.prepareStatement(DELETE_USER)) {
            conn.setAutoCommit(false);
            prst.setLong(1, id);
            prst.executeUpdate();
            conn.commit();
        } catch (SQLException er) {
            er.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement prst = conn.prepareStatement(SELECT_USER); ResultSet rst = prst.executeQuery()) {
            while (rst.next()) {
                users.add(new User(rst.getString("name"), rst.getString("last_name"), rst.getByte("age")));
            }
        } catch (SQLException er) {
            er.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement stm = conn.createStatement()) {
            stm.execute(CLEAN_TABLE);
        } catch (SQLException er) {
            er.printStackTrace();
        }
    }
}
