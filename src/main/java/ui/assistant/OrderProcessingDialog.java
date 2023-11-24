package ui.assistant;

import business.orderProcessing.Cart;
import business.orderProcessing.Item;
import business.orderProcessing.Order;
import business.userAuth.SalesAssistant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderProcessingDialog extends JDialog {

    AssistantUI parent;
    Cart cart;

    SalesAssistant salesAssistant;
    JTextField customerField;

    public OrderProcessingDialog(AssistantUI parent, double totalCost, Cart cart, SalesAssistant salesAssistant) {
        super(parent, "Order Processing", true);
        this.parent = parent;
        this.cart = cart;
        this.salesAssistant = salesAssistant;
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Display total cost
        JLabel totalCostLabel = new JLabel("Total Cost: $" + String.format("%.2f", totalCost), JLabel.CENTER);
        mainPanel.add(totalCostLabel, BorderLayout.NORTH);

        // Display cart
        String[] cartColumnNames = {"Product ID", "Name", "Quantity", "Price", "Total Price"};
        Object[][] cartData = new Object[0][4];
        DefaultTableModel cartModel = new DefaultTableModel(cartData, cartColumnNames);
        JTable cartTable = new JTable(cartModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);

        mainPanel.add(cartScrollPane, BorderLayout.CENTER);

        for (Item i : cart.getItemsList()) {
            Object[] itemDetails = {i.getProduct().getCode(), i.getProduct().getName(), i.getQuantityOrdered(), i.getProduct().getPrice(), i.total()};
            cartModel.addRow(itemDetails);
        }

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(2,2));

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

        //customer section
        JLabel customerLabel = new JLabel("Customer Name:");
        customerField = new JTextField(20);

        buttonsPanel.add(customerLabel);
        buttonsPanel.add(customerField);
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(confirmButton);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setResizable(false);
        setVisible(true);
    }

    private void generateInvoice() {
        if (customerField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter customer name.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        Order order = cart.generateOrder();
        if (order != null) {
            salesAssistant.addOrder(order);
        }
        JOptionPane.showMessageDialog(
                this,
                "Invoice generated successfully.",
                "Invoice Generated",
                JOptionPane.INFORMATION_MESSAGE
        );
        parent.clear();
    }
}

