package dao;

import business.productCatalog.Category;
import business.productCatalog.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductDAO {
    private static final String JDBC_URL = "jdbc:mysql://your-mysql-host:your-mysql-port/your-database";
    private static final String JDBC_USER = "your-mysql-username";
    private static final String JDBC_PASSWORD = "your-mysql-password";

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "SELECT * FROM products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int code = resultSet.getInt("code");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        int stockQuantity = resultSet.getInt("stockQuantity");
                        double price = resultSet.getDouble("price");

                        Product product = new Product(name, description, stockQuantity, price);
                        Set<Category> categories = getCategoriesByProductCode(code);
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

    public void addProduct(Product product) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "INSERT INTO products (name, description, stockQuantity, price, categoryCode) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getDescription());
                preparedStatement.setInt(3, product.getStockQuantity());
                preparedStatement.setDouble(4, product.getPrice());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Category> getCategoriesByProductCode(int productCode) {
        Set<Category> categories = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "SELECT c.* FROM categories c " +
                    "JOIN product_category pc ON c.code = pc.categoryCode " +
                    "WHERE pc.productCode = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productCode);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int code = resultSet.getInt("code");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");

                        Category category = new Category(name, description);
                        categories.add(category);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}

