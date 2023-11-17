package ui.manager.inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryManagementUI extends JFrame {

    private JComboBox<String> categoryComboBox;
    private JTable productTable;

    public InventoryManagementUI() {
        setTitle("Inventory Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel for the main content
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a panel for the category selection
        JPanel categoryPanel = new JPanel();
        JLabel categoryLabel = new JLabel("Select Category:");
        categoryComboBox = new JComboBox<>(getCategories());
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);

        mainPanel.add(categoryPanel, BorderLayout.NORTH);

        // Create a table to display the product catalog
        String[] columnNames = {"Product ID", "Name", "Price", "Quantity", "Expiration Date", "Alert Quantity"};
        Object[][] data = new Object[0][6];
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        productTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(productTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create buttons for setting alerts, restocking, and handling expired items
        JPanel buttonPanel = new JPanel();
        JButton setAlertButton = new JButton("Set Alert");
        setAlertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLowStockAlert();
            }
        });

        JButton restockButton = new JButton("Restock");
        restockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restockItem();
            }
        });

        JButton showExpiredButton = new JButton("Show Expired Items");
        showExpiredButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExpiredItems();
            }
        });

        buttonPanel.add(setAlertButton);
        buttonPanel.add(restockButton);
        buttonPanel.add(showExpiredButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private String[] getCategories() {
        // Return your list of categories
        return new String[]{"Category 1", "Category 2", "Category 3"};
    }

    private void updateTable() {
        // Implement logic to update the table based on the selected category
        // For demonstration purposes, let's assume you have some data
        Object[][] data = {
                {"1", "Product 1", "10.00", "50", "2023-12-31", "100"},
                {"2", "Product 2", "5.00", "80", "2023-11-30", "150"},
                {"3", "Product 3", "8.50", "30", "2024-01-15", "80"}
                // Add more rows as needed
        };
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setDataVector(data, new Object[]{"Product ID", "Name", "Price", "Quantity", "Expiration Date", "Alert Quantity"});
    }

    private void setLowStockAlert() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            Object[] productDetails = getProductDetails(selectedRow);
            SetAlertUI setAlertDialog = new SetAlertUI(this, productDetails);
            setAlertDialog.setVisible(true);
            // You may update the table after saving the alert
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product to set an alert.",
                    "Alert Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void restockItem() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            Object[] productDetails = getProductDetails(selectedRow);
            RestockItemsUI restockDialog = new RestockItemsUI(this, productDetails);
            restockDialog.setVisible(true);
            // You may update the table after restocking
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product to restock.",
                    "Restock Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showExpiredItems() {
        ExpiredItemsUI showExpiredItemsUI = new ExpiredItemsUI(this, getExpiredItems());
        showExpiredItemsUI.setVisible(true);
        // You may update the table after showing expired items
    }

    private String getExpiredItems() {
        String expiredItems = "";
        return expiredItems;
    }

    private Object[] getProductDetails(int selectedRow) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        return new Object[]{
                model.getValueAt(selectedRow, 0),
                model.getValueAt(selectedRow, 1),
                model.getValueAt(selectedRow, 2),
                model.getValueAt(selectedRow, 3),
                model.getValueAt(selectedRow, 4),
                model.getValueAt(selectedRow, 5)
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InventoryManagementUI inventoryScreen = new InventoryManagementUI();
                inventoryScreen.setVisible(true);
            }
        });
    }
}
