package dev.mateusneres.stockmanager;

import dev.mateusneres.stockmanager.controllers.LoginController;
import dev.mateusneres.stockmanager.database.MySQLManager;
import dev.mateusneres.stockmanager.views.LoginScreen;
import dev.mateusneres.stockmanager.views.SplashScreen;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StockManager {

    public static void main(String[] args) {
        setupFlatLafTheme();

        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.showSplashScreen();

            MySQLManager.getInstance().initTables();

            AtomicInteger progress = new AtomicInteger();

            Timer timer = new Timer(20, e -> {
                splash.setProgress(progress.addAndGet(1));

                if (progress.get() > 100) {
                    ((Timer) e.getSource()).stop();
                    splash.hideSplashScreen();

                    LoginScreen loginScreen = new LoginScreen(null);
                    new LoginController(loginScreen);
                }
            });

            timer.start();
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
