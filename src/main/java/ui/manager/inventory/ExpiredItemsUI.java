package ui.manager.inventory;

import business.productCatalog.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ExpiredItemsUI extends JDialog {

    private JTextArea expiredTextArea;
    private InventoryManagementUI parent;

    public ExpiredItemsUI(InventoryManagementUI parent, String expiredItems, ArrayList<Product> expiredProducts) {
        super(parent, "Show Expired Items", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(new BorderLayout());

        expiredTextArea = new JTextArea();
        expiredTextArea.setEditable(false);
        expiredTextArea.setText(expiredItems);

        JScrollPane scrollPane = new JScrollPane(expiredTextArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete Expired Items");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        parent,
                        "Are you sure you want to delete all expired items?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    deleteExpiredItemsFromDatabase(expiredProducts);

                    JOptionPane.showMessageDialog(
                            parent,
                            "Expired items have been deleted.",
                            "Delete Expired Items",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose();
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog without deleting
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void deleteExpiredItemsFromDatabase(ArrayList<Product> expiredItems) {
        Product product = new Product();
        product.deleteProducts(expiredItems);
    }
}
