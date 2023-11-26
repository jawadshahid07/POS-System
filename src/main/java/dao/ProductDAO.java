package dao;

import business.orderProcessing.Order;
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
            String query = "INSERT INTO products (name, description, stockQuantity, price, categoryCode, alertQuantity, dateTracked) VALUES (?, ?, ?, ?, ?, ? , ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getDescription());
                preparedStatement.setInt(3, product.getStockQuantity());
                preparedStatement.setDouble(4, product.getPrice());
                preparedStatement.setInt(5, product.getCategoryCode());
                preparedStatement.setInt(6, product.getAlertQuantity());
                preparedStatement.setString(7, product.getExpirationDate());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(int code) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "DELETE FROM products WHERE code = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, code);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editProduct(Product product) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "UPDATE products SET name = ?, description = ?, stockQuantity = ?, price = ?, categoryCode = ?, alertQuantity = ? WHERE code = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getDescription());
                preparedStatement.setInt(3, product.getStockQuantity());
                preparedStatement.setDouble(4, product.getPrice());
                preparedStatement.setInt(5, product.getCategoryCode());
                preparedStatement.setInt(6, product.getAlertQuantity());
                preparedStatement.setInt(7, product.getCode());
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
                        int alertQuantity = resultSet.getInt("alertQuantity");
                        String dateTrackedString = resultSet.getString("dateTracked");

                        Product product = new Product(name, description, stockQuantity, price, categoryCode);
                        product.setCode(code);
                        product.setAlertQuantity(alertQuantity);
                        product.setExpirationDate(dateTrackedString);
                        products.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


    public List<Product> getProductsBySearchNameCategoryCode(String searchText, int categoryCode) {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "SELECT * FROM products WHERE name LIKE ? AND categoryCode = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Use '%' as a wildcard to match any characters before and after the search text
                preparedStatement.setString(1, "%" + searchText + "%");
                preparedStatement.setInt(2, categoryCode);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int code = resultSet.getInt("code");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        int stockQuantity = resultSet.getInt("stockQuantity");
                        double price = resultSet.getDouble("price");
                        int alertQuantity = resultSet.getInt("alertQuantity");
                        String dateTrackedString = resultSet.getString("dateTracked");

                        Product product = new Product(name, description, stockQuantity, price, categoryCode);
                        product.setCode(code);
                        product.setAlertQuantity(alertQuantity);
                        product.setExpirationDate(dateTrackedString);
                        products.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

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
                        int categoryCode = resultSet.getInt("categoryCode");
                        int alertQuantity = resultSet.getInt("alertQuantity");
                        String dateTrackedString = resultSet.getString("dateTracked");

                        Product product = new Product(name, description, stockQuantity, price, categoryCode);
                        product.setCode(code);
                        product.setAlertQuantity(alertQuantity);
                        product.setExpirationDate(dateTrackedString);
                        products.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product getProductById(int code) {
        Product product = new Product();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "SELECT * FROM products WHERE code = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, code);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        int stockQuantity = resultSet.getInt("stockQuantity");
                        double price = resultSet.getDouble("price");
                        int categoryCode = resultSet.getInt("categoryCode");
                        int alertQuantity = resultSet.getInt("alertQuantity");
                        String dateTrackedString = resultSet.getString("dateTracked");

                        product = new Product(name, description, stockQuantity, price, categoryCode);
                        product.setAlertQuantity(alertQuantity);
                        product.setExpirationDate(dateTrackedString);
                        product.setCode(code);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public Product getProductByName(String productName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String productQuery = "SELECT * FROM products WHERE name = ?";
            try (PreparedStatement productStatement = connection.prepareStatement(productQuery)) {
                productStatement.setString(1, productName);
                try (ResultSet productResultSet = productStatement.executeQuery()) {
                    if (productResultSet.next()) {
                        int code = productResultSet.getInt("code");
                        String name = productResultSet.getString("name");
                        String description = productResultSet.getString("description");
                        int stockQuantity = productResultSet.getInt("stockQuantity");
                        double price = productResultSet.getDouble("price");
                        int categoryCode = productResultSet.getInt("categoryCode");

                        Product product = new Product(name, description, stockQuantity, price, categoryCode);
                        product.setCode(code);
                        return product;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}

