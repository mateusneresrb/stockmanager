package dev.mateusneres.stockmanager.views;

import dev.mateusneres.stockmanager.utils.IconUtil;
import lombok.Getter;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;

@Getter
public class LoginScreen extends JFrame {

    private final JLabel titleLabel;
    private final JLabel emailLabel;
    private final JLabel passwordLabel;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JLabel signUpLabel;
    private final JButton button;
    private final Point frameLocation;

    public LoginScreen(Point frameLocation) {
        super("StockManager - SignIn");
        IconUtil.setIcon(this);

        this.frameLocation = frameLocation;

        titleLabel = new JLabel("SignIn");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        emailLabel = new JLabel("Email:");
        emailLabel.setBorder(BorderFactory.createEmptyBorder(15, 2, 2, 0));
        emailField = new JTextField();

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(8, 2, 2, 0));
        passwordField = new JPasswordField();

        button = new JButton("Sign In!");
        button.setPreferredSize(new Dimension(getWidth(), 30));
        button.setHorizontalAlignment(SwingConstants.CENTER);

        signUpLabel = new JLabel("New user? Sign up now!");
        signUpLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        signUpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new VerticalLayout());

        JPanel formPanel = new JPanel(new VerticalLayout());
        JPanel separationPanel = new JPanel();
        separationPanel.setPreferredSize(new Dimension(getWidth(), 20));
        separationPanel.setOpaque(false);

        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 80));
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(separationPanel);

        formPanel.add(button);
        formPanel.add(signUpLabel);

        contentPane.add(titleLabel);
        contentPane.add(formPanel);

        setContentPane(contentPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(450, 400));
        setFrameLocation(frameLocation);
        setResizable(false);
        setVisible(true);
        setAlwaysOnTop(true);
    }

    private void setFrameLocation(Point location) {
        if (location != null) {
            setLocation(location);
            return;
        }
        setLocationRelativeTo(null);
    }

}