package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProductUI extends JDialog {

    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;

    public EditProductUI(JFrame parent, Object[] productDetails) {
        super(parent, "Edit Product", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField(productDetails[1].toString());
        panel.add(nameField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField(productDetails[2].toString());
        panel.add(priceField);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField(productDetails[3].toString());
        panel.add(quantityField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the save operation here
                dispose(); // Close the dialog
            }
        });
        panel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog without saving changes
            }
        });
        panel.add(cancelButton);

        add(panel);
    }
}

