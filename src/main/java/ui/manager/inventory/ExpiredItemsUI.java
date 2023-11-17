package ui.manager.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpiredItemsUI extends JDialog {

    private JTextArea expiredTextArea;

    public ExpiredItemsUI(JFrame parent, String expiredItems) {
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
                    // Implement logic to delete expired items from the database
                    // You may update the table after deletion
                    JOptionPane.showMessageDialog(
                            parent,
                            "Expired items have been deleted.",
                            "Delete Expired Items",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
                dispose(); // Close the dialog
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
}
