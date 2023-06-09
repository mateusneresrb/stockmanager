package dev.mateusneres.stockmanager;

import dev.mateusneres.stockmanager.controllers.LoginController;
import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.database.MySQLManager;
import dev.mateusneres.stockmanager.tasks.MonitorTask;
import dev.mateusneres.stockmanager.views.LoginScreen;
import dev.mateusneres.stockmanager.views.SplashScreen;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class StockManager {

    private static StockController stockController;

    public static void main(String[] args) {
        setupFlatLafTheme();

        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.showSplashScreen();

            MySQLManager.getInstance().initTables();
            stockController = new StockController();
            initMonitorsTask();

            AtomicInteger progress = new AtomicInteger();
            Timer timer = new Timer(20, e -> {
                splash.setProgress(progress.addAndGet(1));

                if (progress.get() > 100) {
                    ((Timer) e.getSource()).stop();
                    splash.hideSplashScreen();

                    LoginScreen loginScreen = new LoginScreen(null);
                    new LoginController(stockController, loginScreen);
                }
            });

            timer.start();
        });

    }

    private static void initMonitorsTask() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(new MonitorTask(stockController), 0, 10, TimeUnit.SECONDS);
    }

    private static void setupFlatLafTheme() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
