package dao;

import business.productCatalog.Category;
import business.productCatalog.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = DbConnection.getConnection()) {
            String query = "SELECT * FROM categories";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int code = resultSet.getInt("code");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");

                        Category category = new Category(name, description);
                        category.setCode(code);

                        categories.add(category);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public void addCategory(Category category) {
        try (Connection connection = DbConnection.getConnection()) {
            String query = "INSERT INTO categories (name, description) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, category.getName());
                preparedStatement.setString(2, category.getDescription());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCategoryCodeByName(String categoryName) {
        int code = 0;
        try (Connection connection = DbConnection.getConnection()) {
            String query = "SELECT * FROM categories WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, categoryName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        code = resultSet.getInt("code");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return code;
    }

    public void removeCategory(int code) {
        String sql = "DELETE FROM categories WHERE code = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, code);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

