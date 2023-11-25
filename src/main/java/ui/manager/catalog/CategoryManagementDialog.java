package ui.manager.catalog;

import business.productCatalog.Category;
import dao.CategoryDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoryManagementDialog extends JDialog {
    private CategoryDAO categoryDAO;

    private JTable categoryTable;
    private JButton addButton;
    private JButton deleteButton;
    private ProductCatalogUI parent;

    public CategoryManagementDialog(ProductCatalogUI parent) {
        super(parent, "Category Management", true);
        this.categoryDAO = new CategoryDAO();
        this.parent = parent;

        setSize(600, 400);
        setLocationRelativeTo(parent);

        initComponents();
        loadCategories();

        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Category Table
        String[] columnNames = {"Code", "Name", "Description"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        categoryTable = new JTable(model);
        JScrollPane categoryScrollPane = new JScrollPane(categoryTable);
        mainPanel.add(categoryScrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        addButton = new JButton("Add Category");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCategory();
            }
        });
        buttonPanel.add(addButton);

        deleteButton = new JButton("Delete Category");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCategory();
            }
        });
        buttonPanel.add(deleteButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadCategories() {
        List<Category> categories = categoryDAO.getAllCategories();
        DefaultTableModel model = (DefaultTableModel) categoryTable.getModel();
        model.setRowCount(0);

        for (Category category : categories) {
            model.addRow(new Object[]{category.getCode(), category.getName(), category.getDescription()});
        }
    }

    private void addCategory() {
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();

        Object[] message = {
                "Name:", nameField,
                "Description:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(
                this,
                message,
                "Add Category",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            String categoryName = nameField.getText().trim();
            String categoryDescription = descriptionField.getText().trim();

            if (!categoryName.isEmpty()) {
                Category newCategory = new Category(categoryName, categoryDescription);
                if (!newCategory.addCategory(newCategory)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Cannot create duplicate categories!",
                            "Existing Category",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                };
                loadCategories();
                parent.updateCategories();
            }
        }
    }

    private void deleteCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow != -1) {
            Integer selectedCategoryCode = (Integer) categoryTable.getValueAt(selectedRow, 0);
            String selectedCategoryName = (String) categoryTable.getValueAt(selectedRow, 1);
            String selectedCategoryDescription = (String) categoryTable.getValueAt(selectedRow, 2);


            if (selectedCategoryCode != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete the selected category?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    Category c = new Category(selectedCategoryCode, selectedCategoryName, selectedCategoryDescription);
                    c.removeCategory(c);
                    loadCategories();
                    parent.updateCategories();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a category to delete.", "Delete Category", JOptionPane.WARNING_MESSAGE);
        }
    }
}
