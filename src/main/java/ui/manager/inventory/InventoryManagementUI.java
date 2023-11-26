package ui.manager.inventory;

import business.productCatalog.Category;
import business.productCatalog.Product;
import dao.CategoryDAO;
import ui.manager.ManagerMainMenuUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InventoryManagementUI extends JFrame {

    private JComboBox<String> categoryComboBox;
    private JTable productTable;

    public InventoryManagementUI() {
        setTitle("Inventory Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel categoryPanel = new JPanel();
        JLabel categoryLabel = new JLabel("Select Category:");
        categoryComboBox = new JComboBox<>(getCategoryNames());
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);

        mainPanel.add(categoryPanel, BorderLayout.NORTH);
        String[] columnNames = {"Product ID", "Name", "Description", "Quantity", "Price", "Expiration Date", "Alert Quantity"};
        Object[][] data = new Object[0][7];
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        productTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(productTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

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

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ManagerMainMenuUI();
            }
        });

        buttonPanel.add(setAlertButton);
        buttonPanel.add(restockButton);
        buttonPanel.add(showExpiredButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        updateTable();
    }

    private List<Category> getCategories() {
        CategoryDAO categoryDAO = new CategoryDAO();
        return categoryDAO.getAllCategories();
    }
    private String[] getCategoryNames() {
        List<Category> categories = getCategories();
        String[] categoryNames = new String[categories.size() + 1];
        categoryNames[0] = "All Categories";
        int i = 1;
        for (Category c : categories) {
            categoryNames[i] = c.getName();
            i++;
        }
        return categoryNames;
    }
    public void updateTable() {
        String selectedCategory = categoryComboBox.getSelectedItem().toString();
        Product product = new Product();
        List<Product> products = product.getProductsByCategory(selectedCategory);

        DefaultTableModel searchModel = (DefaultTableModel) productTable.getModel();
        searchModel.setRowCount(0);

        for (Product p : products) {
            if (p.getAlertQuantity() == -1) {
                searchModel.addRow(new Object[]{p.getCode(), p.getName(), p.getDescription(), p.getStockQuantity(), p.getPrice(), p.getExpirationDate(), "Not Set"});
            }
            else {
                searchModel.addRow(new Object[]{p.getCode(), p.getName(), p.getDescription(), p.getStockQuantity(), p.getPrice(), p.getExpirationDate(), p.getAlertQuantity()});
            }
        }
    }

    private void setLowStockAlert() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            Object[] productDetails = getProductDetails(selectedRow);
            SetAlertUI setAlertDialog = new SetAlertUI(this, productDetails);
            setAlertDialog.setVisible(true);
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

        ArrayList<Product> expiredItems = filterExpiredProducts();
        ExpiredItemsUI showExpiredItemsUI = new ExpiredItemsUI(this, getExpiredItems(), expiredItems);
        showExpiredItemsUI.setVisible(true);
    }

    private String getExpiredItems() {
        StringBuilder expiredItems = new StringBuilder();
        List<Product> expiredProducts = filterExpiredProducts();

        for (Product p : expiredProducts) {
            expiredItems.append("Product Name: ").append(p.getName()).append("\n");
            expiredItems.append("Expiration Date: ").append(p.getExpirationDate()).append("\n");
            expiredItems.append("------------------------------\n");
        }

        return expiredItems.toString();
    }

    private ArrayList<Product> filterExpiredProducts() {
        Product product = new Product();
        return product.filterExpiredProducts();
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
}
