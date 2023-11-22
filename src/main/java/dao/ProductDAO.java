package dao;

import business.productCatalog.Category;
import business.productCatalog.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/pos_system";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1234";
    public void addProduct(Product product) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "INSERT INTO products (name, description, stockQuantity, price, categoryCode) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getDescription());
                preparedStatement.setInt(3, product.getStockQuantity());
                preparedStatement.setDouble(4, product.getPrice());
                preparedStatement.setInt(5, product.getCategoryCode());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Product> getProductsByCategoryCode(int categoryCode) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "SELECT * FROM products WHERE categoryCode = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, categoryCode);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int code = resultSet.getInt("code");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        int stockQuantity = resultSet.getInt("stockQuantity");
                        double price = resultSet.getDouble("price");

                        Product product = new Product(name, description, stockQuantity, price, categoryCode);
                        product.setCode(code);
                        products.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}

