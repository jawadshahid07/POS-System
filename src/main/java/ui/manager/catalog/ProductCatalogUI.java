package ui.manager.catalog;

import business.productCatalog.Category;
import business.productCatalog.Product;
import dao.CategoryDAO;
import dao.ProductDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class ProductCatalogUI extends JFrame {

    private JComboBox<String> categoryComboBox;
    private JTable productTable;
    private List<Category> categories; // Added to store all categories

    public ProductCatalogUI() {
        setTitle("Product Catalog");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel for the main content
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a panel for the category selection
        JPanel categoryPanel = new JPanel();
        JLabel categoryLabel = new JLabel("Select Category:");

        categories = getCategories(); // Store all categories for later use
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

        // Create a table to display the product catalog
        String[] columnNames = {"Product ID", "Name", "Description", "Quantity", "Price"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        productTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(productTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddDialog();
            }
        });

        JButton deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDeleteDialog();
            }
        });

        JButton editButton = new JButton("Edit Product");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditDialog();
            }
        });

        JButton manageCategoriesButton = new JButton("Manage Categories");
        manageCategoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManageCategoriesDialog();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(manageCategoriesButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        updateTable(); // Initially update the table with all products
    }

    private List<Category> getCategories() {
        CategoryDAO categoryDAO = new CategoryDAO();
        return categoryDAO.getAllCategories();
    }

    private String[] getCategoryNames() {
        String[] categoryNames = new String[categories.size()];
        int i = 0;
        for (Category c : categories) {
            categoryNames[i] = c.getName();
            i++;
        }
        return categoryNames;
    }

    public void updateCategories() {
        categories = getCategories();
        String[] categoryNames = getCategoryNames();
        categoryComboBox.setModel(new DefaultComboBoxModel<>(categoryNames));
    }

    public void updateTable() {
        // Update the table based on the selected category
        String selectedCategory = categoryComboBox.getSelectedItem().toString();
        Product product = new Product();
        List<Product> products = product.getProductsByCategory(selectedCategory);

        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);

        for (Product p : products) {
            model.addRow(new Object[]{p.getCode(), p.getName(), p.getDescription(), p.getStockQuantity(), p.getPrice()});
        }
    }

    private void openAddDialog() {
        AddProductUI addDialog = new AddProductUI(this);
        addDialog.setVisible(true);
        // Handle the result of the add operation here
        updateTable(); // Update the table after adding a new product
    }

    private void openDeleteDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this product?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) productTable.getModel();
                int productId = (int) model.getValueAt(selectedRow, 0);
                //ProductDAO.deleteProduct(productId);

                // Update the table after deletion
                updateTable();
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product to delete.",
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openEditDialog() {
        EditProductUI editDialog = new EditProductUI(this, Objects.requireNonNull(getProductDetails()));
        editDialog.setVisible(true);
        // Handle the result of the edit operation here
        updateTable(); // Update the table after editing a product
    }

    private Object[] getProductDetails() {
        // Get the selected product details from the JTable
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            return new Object[]{
                    productTable.getValueAt(selectedRow, 0),
                    productTable.getValueAt(selectedRow, 1),
                    productTable.getValueAt(selectedRow, 2),
                    productTable.getValueAt(selectedRow, 3)
            };
        }
        return null;
    }

    private void openManageCategoriesDialog() {
        new CategoryManagementDialog(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProductCatalogUI().setVisible(true);
            }
        });
    }
}
