package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

        private Connection conn = new Util().getConnection();

    private final String createTable = "CREATE TABLE IF NOT EXISTS USER (USER_ID INT NOT NULL AUTO_INCREMENT, NAME VARCHAR(40) NOT NULL, LAST_NAME VARCHAR(60) NOT NULL, AGE TINYINT NOT NULL, PRIMARY KEY(USER_ID))";
    private final String dropTable = "DROP TABLE IF EXISTS USER";
    private final String insertData = "INSERT INTO USER (NAME, LAST_NAME, AGE) VALUES (?, ?, ?)";
    private final String statement = "DELETE FROM USER WHERE USER_ID = ?";
    private final String selectUser = "SELECT NAME, LAST_NAME, AGE FROM USER";
    private final String cleanTable = "DELETE FROM USER";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTable);
        } catch (SQLException er) {
            er.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(dropTable);
        } catch (SQLException er) {
            er.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pst = conn.prepareStatement(insertData)) {
            conn.setAutoCommit(false);
            pst.setString(1, name);
            pst.setString(2, lastName);
            pst.setByte(3, age);
            pst.executeUpdate();
            conn.commit();
        } catch (SQLException  er) {
            er.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement prst = conn.prepareStatement(statement)) {
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
        try (PreparedStatement prst = conn.prepareStatement(selectUser); ResultSet rst = prst.executeQuery()) {
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
            stm.execute(cleanTable);
        } catch (SQLException er) {
            er.printStackTrace();
        }
    }
}
