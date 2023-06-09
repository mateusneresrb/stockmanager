package dev.mateusneres.stockmanager.views;

import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JFrame {

    private final JLabel appLogoLabel;
    private final JProgressBar jProgressBar;

    public SplashScreen() {
        super("StockManager - Home");

        appLogoLabel = new JLabel("StockManager");
        appLogoLabel.setFont(new Font("Arial", Font.BOLD, 25));
        appLogoLabel.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 0));
        appLogoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        jProgressBar = new JProgressBar();
        jProgressBar.setStringPainted(true);
        jProgressBar.setFont(new Font("Arial", Font.BOLD, 17));

        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new VerticalLayout());

        JPanel headerPane = new JPanel(new BorderLayout());
        headerPane.add(appLogoLabel, BorderLayout.CENTER);
        headerPane.add(jProgressBar, BorderLayout.SOUTH);

        contentPane.add(headerPane);

        setContentPane(contentPane);
        setMinimumSize(new Dimension(450, 150));
        setUndecorated(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showSplashScreen() {
        setVisible(true);
    }

    public void hideSplashScreen() {
        setVisible(false);
    }

    public void setProgress(int progress) {
        jProgressBar.setValue(progress);
    }

}
