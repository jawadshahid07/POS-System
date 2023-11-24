package ui.manager.catalog;

import business.productCatalog.Category;
import business.productCatalog.Product;
import dao.CategoryDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditProductUI extends JDialog {

    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField descriptionField;
    private JComboBox categoryComboBox;
    private ProductCatalogUI parent;

    public EditProductUI(ProductCatalogUI parent, Object[] productDetails) {
        super(parent, "Edit Product", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField(productDetails[1].toString());
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        descriptionField = new JTextField(productDetails[2].toString());
        panel.add(descriptionField);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField(productDetails[3].toString());
        panel.add(quantityField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField(productDetails[4].toString());
        panel.add(priceField);

        panel.add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>();
        panel.add(categoryComboBox);

        loadCategories();

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String description = descriptionField.getText();
                String selectedCategory = categoryComboBox.getSelectedItem().toString();
                Category c = new Category();
                Product product = new Product(Integer.parseInt(productDetails[0].toString()), name, description, quantity, price, c.getCategoryCode(selectedCategory));
                c.editProduct(product);
                parent.updateTable();
                dispose();
            }
        });
        panel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton);

        add(panel);
    }

    private void loadCategories() {
        Category c = new Category();
        List<Category> allCategories = c.loadCategories();

        for (Category category : allCategories) {
            categoryComboBox.addItem(category.getName());
        }
    }
}

