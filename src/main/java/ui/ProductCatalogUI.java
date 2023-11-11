package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ProductCatalogUI extends JFrame {

    //private List<Product> productList;
    private JComboBox<String> categoryComboBox;
    private JTable productTable;

    public ProductCatalogUI() {
        setTitle("Product Catalog");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //productList = new ArrayList<>(); // Initialize or load your product data here

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
        String[] columnNames = {"Product ID", "Name", "Price", "Quantity"};
        Object[][] data = new Object[4][4];
        productTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(productTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddDialog();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDeleteDialog();
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditDialog();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private String[] getCategories() {
        // Return your list of categories
        return new String[]{"Category 1", "Category 2", "Category 3"};
    }

    private void updateTable() {
        // Update the table based on the selected category
        // You may need to filter your productList based on the selected category
        // and update the JTable accordingly.
    }

    private void openAddDialog() {
        AddProductUI addDialog = new AddProductUI(this);
        addDialog.setVisible(true);
        // Handle the result of the add operation here
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
                model.removeRow(selectedRow);

                // Optionally, you can also remove the product from your productList
                // productList.remove(selectedRow);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProductCatalogUI catalogScreen = new ProductCatalogUI();
                catalogScreen.setVisible(true);
            }
        });
    }
}
