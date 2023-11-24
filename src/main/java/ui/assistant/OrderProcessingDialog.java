package ui.assistant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderProcessingDialog extends JDialog {

    public OrderProcessingDialog(JFrame parent, double totalCost, DefaultTableModel cartModel) {
        super(parent, "Order Processing", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Display total cost
        JLabel totalCostLabel = new JLabel("Total Cost: $" + String.format("%.2f", totalCost), JLabel.CENTER);
        mainPanel.add(totalCostLabel, BorderLayout.NORTH);

        // Display cart
        JTable cartTable = new JTable(cartModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        mainPanel.add(cartScrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
            }
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInvoice();
                dispose(); // Close the dialog
            }
        });

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(confirmButton);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setResizable(false);
        setVisible(true);
    }

    private void generateInvoice() {
        // Add logic to generate the invoice
        JOptionPane.showMessageDialog(
                this,
                "Invoice generated successfully.",
                "Invoice Generated",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}

