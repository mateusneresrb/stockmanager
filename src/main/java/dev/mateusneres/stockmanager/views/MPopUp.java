package dev.mateusneres.stockmanager.views;

import dev.mateusneres.stockmanager.controllers.HomeController;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MPopUp extends JFrame {

    @Getter
    private final transient HomeController homeController;

    public MPopUp(String name, HomeController homeController) {
        super(name);
        this.homeController = homeController;

        handleListener();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 500));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setAlwaysOnTop(true);
    }

    private void handleListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                disposeAndUpdate();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                disposeAndUpdate();
            }

            private void disposeAndUpdate() {
                if (homeController != null) homeController.updateTableData();
                dispose();
            }
        });
    }

}
