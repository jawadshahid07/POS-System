package ui.manager.catalog;

import business.productCatalog.Category;
import dao.CategoryDAO;
import dao.ProductCategoryDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddProductUI extends JDialog {

    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField descriptionField;
    private JPanel categoryPanel;
    private ProductCatalogUI parent;

    public AddProductUI(ProductCatalogUI parent) {
        super(parent, "Add Product", true);
        this.parent = parent;
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout());

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
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

        // Category selection
        categoryPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane categoryScrollPane = new JScrollPane(categoryPanel);
        inputPanel.add(new JLabel("Select Categories:"));
        inputPanel.add(categoryScrollPane);

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
            JCheckBox checkBox = new JCheckBox(category.getName());
            categoryPanel.add(checkBox);
        }
    }

    private List<String> getSelectedCategories() {
        List<String> selectedCategories = new ArrayList<>();
        Component[] components = categoryPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    selectedCategories.add(checkBox.getText());
                }
            }
        }
        return selectedCategories;
    }

    private void addProduct() {
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        String description = descriptionField.getText();
        List<String> selectedCategories = getSelectedCategories();
        Category.addProduct(name, description, quantity, price, selectedCategories);
    }
}
