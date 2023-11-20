package ui.assistant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssistantUI extends JFrame {

    private JTextField searchField;
    private JButton searchButton;
    private JTable searchResultsTable;
    private JTable cartTable;
    private JLabel totalCostLabel;
    private JButton addToCartButton;
    private JButton processOrderButton;

    public AssistantUI() {
        setTitle("Assistant Interface");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel topPanel = new JPanel(new GridLayout(1,2));
        JPanel bottomPanel = new JPanel(new GridLayout(2,2));
        JPanel middlePanel = new JPanel(new GridLayout(1,2));

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Search Panel
        JPanel searchPanel = new JPanel(new GridLayout(1,2));
        JLabel searchLabel = new JLabel("Search Product:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProduct();
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        bottomPanel.add(searchPanel);

        // Total Cost Label
        totalCostLabel = new JLabel("Total Cost: $0.00", JLabel.CENTER);
        bottomPanel.add(totalCostLabel);

        // Search Results Table
        String[] searchColumnNames = {"Product ID", "Name", "Price", "Quantity"};
        Object[][] searchData = new Object[0][4];
        DefaultTableModel searchModel = new DefaultTableModel(searchData, searchColumnNames);
        searchResultsTable = new JTable(searchModel);
        JScrollPane searchScrollPane = new JScrollPane(searchResultsTable);
        middlePanel.add(searchScrollPane);

        // labels for tables
        JLabel searchTableLabel = new JLabel("Search Results:", JLabel.CENTER);
        topPanel.add(searchTableLabel);
        JLabel cartTableLabel = new JLabel("Cart:", JLabel.CENTER);
        topPanel.add(cartTableLabel);

        // Add to Cart Button
        addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });
        bottomPanel.add(addToCartButton);

        // Cart Table
        String[] cartColumnNames = {"Product ID", "Name", "Price", "Quantity"};
        Object[][] cartData = new Object[0][4];
        DefaultTableModel cartModel = new DefaultTableModel(cartData, cartColumnNames);
        cartTable = new JTable(cartModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        middlePanel.add(cartScrollPane);
        mainPanel.add(middlePanel, BorderLayout.CENTER);

        // Process Order Button
        processOrderButton = new JButton("Process Order");
        processOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processOrder();
            }
        });
        bottomPanel.add(processOrderButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        add(mainPanel);
        setVisible(true);
    }

    private void searchProduct() {
        // Implement logic to search for products based on the entered text
        // Update the searchResultsTable with search results
        // For demonstration, let's assume some data
        Object[][] searchData = {
                {"1", "Product A", "10.00", "50"},
                {"2", "Product B", "5.00", "80"},
                {"3", "Product C", "8.50", "30"}
                // Add more rows as needed
        };
        DefaultTableModel searchModel = (DefaultTableModel) searchResultsTable.getModel();
        searchModel.setDataVector(searchData, new Object[]{"Product ID", "Name", "Price", "Quantity"});
    }

    private void addToCart() {
        // Implement logic to add the selected product to the cart
        JOptionPane.showMessageDialog(
                this,
                "Product added to cart.",
                "Add to Cart",
                JOptionPane.INFORMATION_MESSAGE
        );

        // For demonstration, let's assume some data
        Object[] productDetails = {"1", "Product A", "10.00", "1"};
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();
        cartModel.addRow(productDetails);

        // Update total cost
        updateTotalCost();
    }

    private void processOrder() {
        // Implement logic to process the items in the cart and generate an invoice
        JOptionPane.showMessageDialog(
                this,
                "Order processed successfully. Invoice generated.",
                "Process Order",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Clear the cart
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();
        cartModel.setRowCount(0);

        // Reset total cost
        updateTotalCost();
    }

    private void updateTotalCost() {
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();
        int rowCount = cartModel.getRowCount();
        double totalCost = 0.0;

        for (int i = 0; i < rowCount; i++) {
            double price = Double.parseDouble(cartModel.getValueAt(i, 2).toString());
            int quantity = Integer.parseInt(cartModel.getValueAt(i, 3).toString());
            totalCost += price * quantity;
        }

        totalCostLabel.setText("Total Cost: $" + String.format("%.2f", totalCost));
    }
}
