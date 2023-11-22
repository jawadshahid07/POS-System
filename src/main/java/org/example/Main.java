package org.example;

import ui.LoginScreenUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginScreenUI loginScreenUI = new LoginScreenUI();
                loginScreenUI.setVisible(true);
            }
        });
    }
}