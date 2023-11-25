package ui.manager.catalog;

import business.productCatalog.Category;
import business.productCatalog.Product;
import dao.CategoryDAO;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

public class AddProductUI extends JDialog {

    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField descriptionField;
    private JComboBox<Integer> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<String> categoryComboBox;
    private ProductCatalogUI parent;

    public AddProductUI(ProductCatalogUI parent) {
        super(parent, "Add Product", true);
        this.parent = parent;
        setSize(500, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout());

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
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

        inputPanel.add(new JLabel("Expiration Date"));
        JPanel datePanel = new JPanel();
        yearComboBox = new JComboBox<>(getYears());
        datePanel.add(yearComboBox);
        monthComboBox = new JComboBox<>(getMonths());
        datePanel.add(monthComboBox);
        dayComboBox = new JComboBox<>(getDays());
        datePanel.add(dayComboBox);

        inputPanel.add(datePanel);

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

    private Integer[] getYears() {
        // You can customize this method to provide the desired range of years
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Integer[] years = new Integer[10];
        for (int i = 0; i < 10; i++) {
            years[i] = currentYear + i;
        }
        return years;
    }

    private String[] getMonths() {
        // You can customize this method to provide the names or numbers of months
        return new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    }

    private Integer[] getDays() {
        // You can customize this method to provide the range of days
        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1;
        }
        return days;
    }

    private void loadCategories() {
        Category c = new Category();
        List<Category> allCategories = c.loadCategories();

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

        int year = (int) yearComboBox.getSelectedItem();
        String month = (String) monthComboBox.getSelectedItem();
        int day = (int) dayComboBox.getSelectedItem();

        // Create a Date object using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMMM-dd");
        String dateString = String.format("%04d-%02d-%02d", year, getMonthNumber(month), day);

        Category c = new Category();
        Product product = new Product(name, description, quantity, price, c.getCategoryCode(selectedCategory));
        product.setAlertQuantity(-1);
        product.setExpirationDate(dateString);
        if(!c.addProduct(product)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Cannot create duplicate products!",
                    "Existing Product",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        };
        parent.updateTable();
        dispose();
    }

    private int getMonthNumber(String monthName) {
        // Converts the month name to its corresponding number
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(monthFormat.parse(monthName));
            return calendar.get(Calendar.MONTH) + 1; // Adding 1 because Calendar.MONTH is zero-based
        } catch (ParseException e) {
            e.printStackTrace();
            return 1; // Default to January if parsing fails
        }
    }

}
