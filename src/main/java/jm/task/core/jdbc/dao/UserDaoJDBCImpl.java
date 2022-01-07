package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.util.List;

import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;

public class UserDaoJDBCImpl implements UserDao {
    public Connection connection;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement statement;

        String s = "CREATE TABLE IF NOT EXISTS table_us" +
                "(ID BIGINT NOT NULL AUTO_INCREMENT," +
                " NAME VARCHAR(45) NOT NULL ," +
                " LAST_NAME VARCHAR(45) NOT NULL, " +
                " AGE TINYINT NOT NULL," +
                "PRIMARY KEY (ID))";
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(s);

            System.out.println("!Таблица создана!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void dropUsersTable() {
        String s = "DROP TABLE IF EXISTS table_us";

        Statement statement;
        try {
            connection = Util.getConnection();
            statement = connection.prepareStatement(s);
            statement.executeUpdate(s);
            System.out.println("Пользователь успешно дропнут");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String s = "INSERT INTO table_us (NAME, LAST_NAME, AGE) VALUES (?, ?, ?);";
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("Пользователь с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement;
        String s = "DELETE FROM table_us WHERE id = ?";
        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь с таким id  < " + id + " > уничтожен ");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String s = "SELECT * FROM table_us";
        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement(s);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                String lastName = resultSet.getString("LAST_NAME");
                byte age = resultSet.getByte("AGE");

                User user = new User(name, lastName, age);
                user.setId(id);
                userList.add(user);
                System.out.println("все юзера успешно вошли в чат таблицы");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return userList;
    }

    public void cleanUsersTable() {
        PreparedStatement preparedStatement;
        String s = "TRUNCATE TABLE table_us";
        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.executeUpdate();
            System.out.println("!Таблица с юзерами почищена! ^_^ ");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
