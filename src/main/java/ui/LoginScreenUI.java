package ui;

import business.userAuth.User;
import ui.assistant.AssistantUI;
import ui.manager.ManagerMainMenuUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreenUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreenUI() {
        // Set up the login screen
        setTitle("Pharmacy POS System - Login");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Pharmacy POS System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(titleLabel, constraints);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(loginLabel, constraints);

        JLabel usernameLabel = new JLabel("Username:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(usernameLabel, constraints);

        usernameField = new JTextField(15);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(usernameField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(15);
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(passwordField, constraints);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        panel.add(loginButton, constraints);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });

        add(panel);
    }

    private void authenticateUser() {
        String enteredUsername = usernameField.getText();
        char[] enteredPasswordChars = passwordField.getPassword();
        String enteredPassword = new String(enteredPasswordChars);

        User user = new User(enteredUsername, enteredPassword);
        int status = user.login(enteredUsername, enteredPassword);
        if (status == 1) {
            JOptionPane.showMessageDialog(this, "Invalid username. Please try again.");
        }
        else if (status == 2) {
            JOptionPane.showMessageDialog(this, "Invalid password. Please try again.");
        }
        else if (status == 0 && user.getRole().permissions().equals("SalesAssistant")) {
            // Authentication successful, sales assistant credentials
            AssistantUI assistantUI = new AssistantUI();
        } else if (status == 0 && user.getRole().permissions().equals("Manager")) {
            // Authentication successful, manager credentials
            ManagerMainMenuUI managerMainMenuUI = new ManagerMainMenuUI();
        }

        // Clear fields for security
        usernameField.setText("");
        passwordField.setText("");
    }
}

