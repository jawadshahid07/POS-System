package dao;

import business.orderProcessing.Item;
import business.orderProcessing.Order;
import business.productCatalog.Category;
import business.productCatalog.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/pos_system";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1234";

    public void saveOrder(Order order) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String query = "INSERT INTO orders (totalAmount, orderDate) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    preparedStatement.setDouble(1, order.total());
                    preparedStatement.setString(2, order.getTimestamp());
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String orderQuery = "SELECT * FROM orders";
            try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery)) {
                try (ResultSet orderResultSet = orderStatement.executeQuery()) {
                    while (orderResultSet.next()) {
                        int orderID = orderResultSet.getInt("orderID");
                        double totalAmount = orderResultSet.getDouble("totalAmount");
                        String timestamp = orderResultSet.getString("orderDate");

                        // Fetch items for each order
                        List<Item> items = getOrderedItemsForOrder(connection, orderID);

                        // Create Order object
                        Order order = new Order(items);
                        order.setTimestamp(timestamp);

                        // Add Order object to the list
                        orders.add(order);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private List<Item> getOrderedItemsForOrder(Connection connection, int orderID) throws SQLException {
        List<Item> items = new ArrayList<>();

        // Query to fetch items for a specific order
        String itemQuery = "SELECT * FROM orderedItems WHERE orderID = ?";
        try (PreparedStatement itemStatement = connection.prepareStatement(itemQuery)) {
            itemStatement.setInt(1, orderID);
            try (ResultSet itemResultSet = itemStatement.executeQuery()) {
                while (itemResultSet.next()) {
                    String productName = itemResultSet.getString("productName");
                    int quantityOrdered = itemResultSet.getInt("quantityOrdered");

                    // Fetch product details
                    Product product = new Product();
                    product = product.getProductByName(productName);

                    // Create Item object
                    Item item = new Item(product, quantityOrdered);
                    items.add(item);
                }
            }
        }

        return items;
    }
}
