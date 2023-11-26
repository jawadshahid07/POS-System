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

    public void saveOrder(Order order) {
        try (Connection connection = DbConnection.getConnection()) {
            String orderQuery = "INSERT INTO orders (totalAmount, orderDate) VALUES (?, ?)";
            try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStatement.setDouble(1, order.total());
                orderStatement.setString(2, order.getTimestamp());
                orderStatement.executeUpdate();

                try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderID = generatedKeys.getInt(1);

                        for (Item item : order.getItemsList()) {
                            saveOrderedItem(connection, orderID, item);
                        }
                    } else {
                        throw new SQLException("Failed to get the generated orderID.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveOrderedItem(Connection connection, int orderID, Item item) {
        try {
            String orderedItemQuery = "INSERT INTO orderedItems (orderID, productCode, quantityOrdered) VALUES (?, ?, ?)";
            try (PreparedStatement orderedItemStatement = connection.prepareStatement(orderedItemQuery)) {
                orderedItemStatement.setInt(1, orderID);
                orderedItemStatement.setInt(2, item.getProduct().getCode());
                orderedItemStatement.setInt(3, item.getQuantityOrdered());
                orderedItemStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DbConnection.getConnection()) {
            String orderQuery = "SELECT * FROM orders";
            try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery)) {
                try (ResultSet orderResultSet = orderStatement.executeQuery()) {
                    while (orderResultSet.next()) {
                        int orderID = orderResultSet.getInt("orderID");
                        double totalAmount = orderResultSet.getDouble("totalAmount");
                        String timestamp = orderResultSet.getString("orderDate");
                        List<Item> items = getOrderedItemsForOrder(connection, orderID);
                        Order order = new Order(items);
                        order.setTimestamp(timestamp);

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

        String itemQuery = "SELECT * FROM orderedItems WHERE orderID = ?";
        try (PreparedStatement itemStatement = connection.prepareStatement(itemQuery)) {
            itemStatement.setInt(1, orderID);
            try (ResultSet itemResultSet = itemStatement.executeQuery()) {
                while (itemResultSet.next()) {
                    int productId = itemResultSet.getInt("productCode");
                    int quantityOrdered = itemResultSet.getInt("quantityOrdered");

                    Product product = new Product();
                    product = product.getProductById(productId);
                    Item item = new Item(product, quantityOrdered);
                    items.add(item);
                }
            }
        }

        return items;
    }
}
