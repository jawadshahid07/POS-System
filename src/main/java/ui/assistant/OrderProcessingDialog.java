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
    JTextField enteredField;

    public OrderProcessingDialog(AssistantUI parent, Cart cart, SalesAssistant salesAssistant) {
        super(parent, "Order Processing", true);
        this.parent = parent;
        this.cart = cart;
        this.salesAssistant = salesAssistant;
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new BorderLayout());


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
        JPanel buttonsPanel = new JPanel(new GridLayout(4,2));

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
            }
        });

        // Display total cost
        JLabel totalCostLabel = new JLabel("Total Cost: $" + String.format("%.2f", cart.total()), JLabel.CENTER);
        JLabel empty = new JLabel();

        //entered amount
        JLabel enteredLabel = new JLabel("Enter Amount:");
        enteredField = new JTextField(20);

        //customer section
        JLabel customerLabel = new JLabel("Customer Name:");
        customerField = new JTextField(20);

        buttonsPanel.add(totalCostLabel);
        buttonsPanel.add(empty);
        buttonsPanel.add(enteredLabel);
        buttonsPanel.add(enteredField);
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
        if (Integer.parseInt(enteredField.getText()) < cart.total() || enteredField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Entered amount must not be less than total amount!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
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
        order.setCustomer(customerField.getText());
        order.setEnteredAmount(Double.parseDouble(enteredField.getText()));
        if (order == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Cannot order more items than available in stock",
                    "Unavailable Stock",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        salesAssistant.addOrder(order);
        salesAssistant.processOrder();
        JOptionPane.showMessageDialog(
                this,
                "Invoice generated successfully.",
                "Invoice Generated",
                JOptionPane.INFORMATION_MESSAGE
        );
        parent.clear();
        dispose();
    }
}

