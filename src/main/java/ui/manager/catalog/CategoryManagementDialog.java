package ui.manager.catalog;

import business.productCatalog.Category;
import dao.CategoryDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoryManagementDialog extends JDialog {
    private CategoryDAO categoryDAO;

    private DefaultListModel<Category> categoryListModel;
    private JList<Category> categoryList;
    private JButton addButton;
    private JButton deleteButton;

    public CategoryManagementDialog(JFrame parent) {
        super(parent, "Category Management", true);
        this.categoryDAO = new CategoryDAO();

        setSize(400, 300);
        setLocationRelativeTo(parent);

        initComponents();
        loadCategories();

        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Category List
        categoryListModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryListModel);
        JScrollPane categoryScrollPane = new JScrollPane(categoryList);
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
        categoryListModel.clear();
        for (Category category : categories) {
            categoryListModel.addElement(category);
        }
    }

    private void addCategory() {
        String categoryName = JOptionPane.showInputDialog(this, "Enter Category Name:");
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            Category newCategory = new Category(0, categoryName, "");
            categoryDAO.addCategory(newCategory);
            loadCategories();
        }
    }

    private void deleteCategory() {
        Category selectedCategory = categoryList.getSelectedValue();
        if (selectedCategory != null) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the selected category?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                categoryDAO.removeCategory(selectedCategory);
                loadCategories();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a category to delete.", "Delete Category", JOptionPane.WARNING_MESSAGE);
        }
    }
}

