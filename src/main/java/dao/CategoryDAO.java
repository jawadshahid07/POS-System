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
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/pos_system";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1234";

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "SELECT * FROM categories";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int code = resultSet.getInt("code");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");

                        Category category = new Category(name, description);
                        category.setCode(code);
                        Set<Product> products = getProductsByCategoryCode(code);
                        category.setProducts(products);

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
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
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

    public Set<Product> getProductsByCategoryCode(int categoryCode) {
        Set<Product> products = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "SELECT p.* FROM products p " +
                    "JOIN product_category pc ON p.code = pc.productCode " +
                    "WHERE pc.categoryCode = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, categoryCode);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int code = resultSet.getInt("code");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        int stockQuantity = resultSet.getInt("stockQuantity");
                        double price = resultSet.getDouble("price");

                        Product product = new Product(code, name, description, stockQuantity, price);
                        Set<Category> categories = new ProductDAO().getCategoriesByProductCode(code);
                        product.setCategories(categories);

                        products.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public void removeCategory(int code) {
        String sql = "DELETE FROM categories WHERE code = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, code);

            // Execute the delete operation
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately (logging, throwing custom exceptions, etc.)
        }
    }
}

