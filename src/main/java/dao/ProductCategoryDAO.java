package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductCategoryDAO {
    private static final String JDBC_URL = "jdbc:mysql://your-mysql-host:your-mysql-port/your-database";
    private static final String JDBC_USER = "your-mysql-username";
    private static final String JDBC_PASSWORD = "your-mysql-password";

    public void addProductToCategory(int productCode, int categoryCode) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "INSERT INTO product_category (productCode, categoryCode) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productCode);
                preparedStatement.setInt(2, categoryCode);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeProductFromCategory(int productCode, int categoryCode) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "DELETE FROM product_category WHERE productCode = ? AND categoryCode = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productCode);
                preparedStatement.setInt(2, categoryCode);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

