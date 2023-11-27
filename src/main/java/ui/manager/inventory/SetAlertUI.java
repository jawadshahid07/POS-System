package ui.manager.inventory;

import business.productCatalog.Category;
import business.productCatalog.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetAlertUI extends JDialog {

    private JTextField quantityField;
    InventoryManagementUI parent;
    Object[] productDetails;

    public SetAlertUI(InventoryManagementUI parent, Object[] productDetails) {
        super(parent, "Set Low Stock Alert", true);
        this.productDetails = productDetails;
        this.parent = parent;
        setSize(300, 150);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Product Name:"));
        panel.add(new JLabel(productDetails[1].toString()));

        panel.add(new JLabel("Enter Alert Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAlert();
            }
        });
        panel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog without saving
            }
        });
        panel.add(cancelButton);

        add(panel);
    }

    private void setAlert() {
        if (quantityField.getText().isEmpty() || !isNumeric(quantityField.getText())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid numeric price",
                    "Invalid Price",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int quantity = Integer.parseInt(quantityField.getText());
        if (quantity < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Quantity cannot be less than 0",
                    "Alert Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Category c = new Category();
        Product product = new Product();
        product = product.getProductById(Integer.parseInt(productDetails[0].toString()));
        product.setAlertQuantity(quantity);
        c.editProduct(product);
        parent.updateTable();
        dispose();
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

