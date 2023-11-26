package dao;

import business.userAuth.Manager;
import business.userAuth.Role;
import business.userAuth.SalesAssistant;
import business.userAuth.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public UserDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String username) {
        User user = null;

        try (Connection connection = DbConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String storedUsername = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        String role = resultSet.getString("role");

                        // Create a User object
                        user = new User(id, name, storedUsername, password, createRole(role));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private Role createRole(String role) {
        switch (role) {
            case "SalesAssistant":
                return new SalesAssistant();
            case "Manager":
                return new Manager();
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}


