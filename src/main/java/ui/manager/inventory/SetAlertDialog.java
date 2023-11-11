package ui.manager.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetAlertDialog extends JDialog {

    private JTextField quantityField;

    public SetAlertDialog(JFrame parent, Object[] productDetails) {
        super(parent, "Set Low Stock Alert", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.add(new JLabel("Product Name:"));
        panel.add(new JLabel(productDetails[1].toString()));

        panel.add(new JLabel("Enter Alert Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to save the alert quantity
                dispose(); // Close the dialog
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
}

