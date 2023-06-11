package dev.mateusneres.stockmanager.views;

import dev.mateusneres.stockmanager.controllers.HomeController;
import dev.mateusneres.stockmanager.utils.IconUtil;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
public class MPopUp extends JFrame {

    private final transient HomeController homeController;

    public MPopUp(String name, HomeController homeController) {
        super(name);
        IconUtil.setIcon(this);
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
                if (e.getOppositeWindow() instanceof JDialog) return;
                disposeAndUpdate();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                if (e.getOppositeWindow() instanceof JDialog) return;
                disposeAndUpdate();
            }

            private void disposeAndUpdate() {
                if (homeController != null) homeController.updateTableData();
                dispose();
            }
        });
    }

}
