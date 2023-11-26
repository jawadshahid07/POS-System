package ui.manager;

import ui.LoginScreenUI;
import ui.manager.catalog.ProductCatalogUI;
import ui.manager.inventory.InventoryManagementUI;
import ui.manager.reports.GenerateReportsUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMainMenuUI extends JFrame {

    public ManagerMainMenuUI() {
        setTitle("Pharmacy POS System - Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel dashboardLabel = new JLabel("Dashboard", SwingConstants.CENTER);
        dashboardLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(dashboardLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 0, 10));

        JButton manageCatalogButton = new JButton("Manage Catalog");
        manageCatalogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductCatalogUI productCatalogUI = new ProductCatalogUI();
                dispose();
            }
        });

        JButton manageInventoryButton = new JButton("Manage Product Inventory");
        manageInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InventoryManagementUI inventoryScreen = new InventoryManagementUI();
                inventoryScreen.setVisible(true);
                dispose();
            }
        });

        JButton generateReportsButton = new JButton("Generate Reports");
        generateReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateReportsUI reportsScreen = new GenerateReportsUI();
                reportsScreen.setVisible(true);
                dispose();
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginScreenUI().setVisible(true);
            }
        });

        buttonPanel.add(manageCatalogButton);
        buttonPanel.add(manageInventoryButton);
        buttonPanel.add(generateReportsButton);
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
