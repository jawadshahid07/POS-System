package ui.manager;

import ui.manager.catalog.ProductCatalogUI;
import ui.manager.inventory.InventoryManagementUI;
import ui.manager.reports.GenerateReportsUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMainMenuUI extends JFrame {

    public ManagerMainMenuUI() {
        // Set up the manager's main menu
        setTitle("Pharmacy POS System - Manager");
        setSize(600, 400); // Set window size to 600x400
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Create the "Dashboard" heading and center it
        JLabel dashboardLabel = new JLabel("Dashboard", SwingConstants.CENTER);
        dashboardLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(dashboardLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        // 0, 10 are the horizontal and vertical gaps

        JButton manageCatalogButton = new JButton("Manage Catalog");
        manageCatalogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductCatalogUI productCatalogUI = new ProductCatalogUI();
            }
        });

        JButton manageInventoryButton = new JButton("Manage Product Inventory");
        manageInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InventoryManagementUI inventoryScreen = new InventoryManagementUI();
                inventoryScreen.setVisible(true);
            }
        });

        JButton generateReportsButton = new JButton("Generate Reports");
        generateReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateReportsUI reportsScreen = new GenerateReportsUI();
                reportsScreen.setVisible(true);
            }
        });

        buttonPanel.add(manageCatalogButton);
        buttonPanel.add(manageInventoryButton);
        buttonPanel.add(generateReportsButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}

