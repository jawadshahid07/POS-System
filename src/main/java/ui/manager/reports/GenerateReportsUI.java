package ui.manager.reports;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenerateReportsUI extends JFrame {

    private JComboBox<String> salesReportComboBox;

    public GenerateReportsUI() {
        setTitle("Generate Reports");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1));

        // Sales Report Section
        JPanel salesReportPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel salesReportLabel = new JLabel("Generate Sales Report:");
        salesReportComboBox = new JComboBox<>(new String[]{"Daily", "Weekly", "Monthly"});
        JButton generateSalesReportButton = new JButton("Generate");
        generateSalesReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateSalesReport();
            }
        });

        salesReportPanel.add(salesReportLabel);
        salesReportPanel.add(salesReportComboBox);
        salesReportPanel.add(generateSalesReportButton);

        mainPanel.add(salesReportPanel);

        // Inventory Report Section
        JPanel inventoryReportPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel inventoryReportLabel = new JLabel("Generate Inventory Report:");
        JButton generateInventoryReportButton = new JButton("Generate");
        generateInventoryReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInventoryReport();
            }
        });

        inventoryReportPanel.add(inventoryReportLabel);
        inventoryReportPanel.add(generateInventoryReportButton);

        mainPanel.add(inventoryReportPanel);

        // Add some space between sections
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        add(mainPanel);
    }

    private void generateSalesReport() {
        // Implement logic to generate sales report based on the selected option
        String selectedOption = (String) salesReportComboBox.getSelectedItem();
        JOptionPane.showMessageDialog(
                this,
                "Generating Sales Report for " + selectedOption + " period.",
                "Sales Report",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void generateInventoryReport() {
        // Implement logic to generate inventory report
        JOptionPane.showMessageDialog(
                this,
                "Generating Inventory Report.",
                "Inventory Report",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GenerateReportsUI reportsScreen = new GenerateReportsUI();
                reportsScreen.setVisible(true);
            }
        });
    }
}

