package dev.mateusneres.stockmanager;

import dev.mateusneres.stockmanager.controllers.LoginController;
import dev.mateusneres.stockmanager.views.LoginScreen;

import javax.swing.*;

public class StockManager {

    public static void main(String[] args) {
        setupFlatLafTheme();

        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen(null);
            new LoginController(loginScreen);
        });
    }

    private static void setupFlatLafTheme() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
