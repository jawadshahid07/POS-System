package ui.manager.catalog;

import business.productCatalog.Category;
import business.productCatalog.Product;
import dao.CategoryDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddProductUI extends JDialog {

    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField descriptionField;
    private JComboBox<String> categoryComboBox; // Change to JComboBox
    private ProductCatalogUI parent;

    public AddProductUI(ProductCatalogUI parent) {
        super(parent, "Add Product", true);
        this.parent = parent;
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout());

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Stock Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        // Category selection using JComboBox
        inputPanel.add(new JLabel("Select Category:"));
        categoryComboBox = new JComboBox<>();
        inputPanel.add(categoryComboBox);

        loadCategories();

        panel.add(inputPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        buttonPanel.add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog without adding
            }
        });
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void loadCategories() {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> allCategories = categoryDAO.getAllCategories();

        for (Category category : allCategories) {
            categoryComboBox.addItem(category.getName());
        }
    }

    private void addProduct() {
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        String description = descriptionField.getText();
        String selectedCategory = categoryComboBox.getSelectedItem().toString();
        Category c = new Category();
        Product product = new Product(name, description, quantity, price, c.getCategoryCode(selectedCategory));
        c.addProduct(product);
        parent.updateTable();
        dispose();
    }
}
