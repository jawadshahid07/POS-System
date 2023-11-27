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
import java.util.List;

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

        JPanel buttonsPanel = new JPanel(new GridLayout(4,2));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInvoice();
            }
        });

        JLabel totalCostLabel = new JLabel("Total Cost: $" + String.format("%.2f", cart.total()), JLabel.CENTER);
        JLabel empty = new JLabel();

        JLabel enteredLabel = new JLabel("Enter Amount:");
        enteredField = new JTextField(20);

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
        if (!isNumeric(enteredField.getText())  || enteredField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric amount",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if (Integer.parseInt(enteredField.getText()) < cart.total()) {
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
        if (order == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Cannot order more items than available in stock",
                    "Unavailable Stock",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        order.setCustomer(customerField.getText());
        order.setEnteredAmount(Double.parseDouble(enteredField.getText()));
        salesAssistant.addOrder(order);
        List<String> restockNames = salesAssistant.processOrder();
        JOptionPane.showMessageDialog(
                this,
                "Invoice generated successfully.",
                "Invoice Generated",
                JOptionPane.INFORMATION_MESSAGE
        );


        if (!restockNames.isEmpty()) {

            for (String restock: restockNames) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please restock the following item: " + restock,
                        "Restock Items Alert",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
        parent.clear();
        parent.updateResults();
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

