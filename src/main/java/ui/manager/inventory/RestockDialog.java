package ui.manager.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestockDialog extends JDialog {

    private JTextField quantityField;

    public RestockDialog(JFrame parent, Object[] productDetails) {
        super(parent, "Restock Product", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(new JLabel("Product Name:"));
        panel.add(new JLabel(productDetails[1].toString()));

        panel.add(new JLabel("Enter Quantity to Restock:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to restock the product
                dispose(); // Close the dialog
            }
        });
        panel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog without restocking
            }
        });
        panel.add(cancelButton);

        add(panel);
    }
}

