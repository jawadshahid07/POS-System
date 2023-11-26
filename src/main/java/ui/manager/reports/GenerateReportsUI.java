package ui.manager.reports;

import business.reporting.InventoryReport;
import business.reporting.SalesReport;
import ui.manager.ManagerMainMenuUI;

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

        JPanel mainPanel = new JPanel(new GridLayout(4, 1));

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

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ManagerMainMenuUI();
            }
        });

        backButtonPanel.add(backButton);
        mainPanel.add(backButtonPanel);

        add(mainPanel);
    }

    private void generateSalesReport() {
        String selectedOption = (String) salesReportComboBox.getSelectedItem();
        JOptionPane.showMessageDialog(
                this,
                "Generating Sales Report for " + selectedOption + " period.",
                "Sales Report",
                JOptionPane.INFORMATION_MESSAGE
        );
        SalesReport salesReport = new SalesReport();
        salesReport.setReportType(selectedOption);
        salesReport.display();
    }

    private void generateInventoryReport() {
        JOptionPane.showMessageDialog(
                this,
                "Generating Inventory Report.",
                "Inventory Report",
                JOptionPane.INFORMATION_MESSAGE
        );
        InventoryReport inventoryReport = new InventoryReport();
        inventoryReport.display();
    }
}

