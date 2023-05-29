package dev.mateusneres.stockmanager.views;

import lombok.Getter;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;

public class SignUpScreen extends JFrame {

    private final JLabel titleLabel;
    private final JLabel nameLabel;
    private final JLabel emailLabel;
    private final JLabel passwordLabel;
    private final JLabel passwordConfirmLabel;
    @Getter
    private final JTextField nameField;
    @Getter
    private final JTextField emailField;
    @Getter
    private final JPasswordField passwordField;
    @Getter
    private final JPasswordField passwordConfirmField;
    @Getter
    private final JLabel signInLabel;
    @Getter
    private final JButton button;
    private final Point frameLocation;

    public SignUpScreen(Point frameLocation) {
        super("StockManager - SignUP");
        this.frameLocation = frameLocation;

        titleLabel = new JLabel("Register your account!");
        titleLabel.setBorder(BorderFactory.createEmptyBorder(35, 0, 20, 0));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nameLabel = new JLabel("Name:");
        nameLabel.setBorder(BorderFactory.createEmptyBorder(15, 2, 2, 0));
        nameField = new JTextField();

        emailLabel = new JLabel("Email:");
        emailLabel.setBorder(BorderFactory.createEmptyBorder(15, 2, 2, 0));
        emailField = new JTextField();

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(8, 2, 2, 0));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(131, 27));

        passwordConfirmLabel = new JLabel("Confirm password:");
        passwordConfirmLabel.setBorder(BorderFactory.createEmptyBorder(8, 2, 2, 0));
        passwordConfirmField = new JPasswordField();
        passwordConfirmField.setPreferredSize(new Dimension(133, 27));

        button = new JButton("SIGN UP");
        button.setPreferredSize(new Dimension(getWidth(), 30));
        button.setHorizontalAlignment(SwingConstants.CENTER);

        signInLabel = new JLabel("Existing user? Log in!");
        signInLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        signInLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signInLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new VerticalLayout());

        JPanel formPanel = new JPanel(new VerticalLayout());
        JPanel separationPanel = new JPanel();
        separationPanel.setPreferredSize(new Dimension(getWidth(), 15));
        separationPanel.setOpaque(false);

        JPanel passwordPanelFlow = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel passwordPanelVertical = new JPanel(new VerticalLayout());
        passwordPanelVertical.add(passwordLabel);
        passwordPanelVertical.add(passwordField);

        JPanel passwordConfirmPanelVertical = new JPanel(new VerticalLayout());
        passwordConfirmPanelVertical.add(passwordConfirmLabel);
        passwordConfirmPanelVertical.add(passwordConfirmField);

        passwordPanelFlow.add(passwordPanelVertical);
        passwordPanelFlow.add(passwordConfirmPanelVertical);

        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 80));
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        formPanel.add(passwordPanelFlow);
        formPanel.add(separationPanel);
        formPanel.add(button);
        formPanel.add(signInLabel);

        contentPane.add(titleLabel);
        contentPane.add(formPanel);

        setContentPane(contentPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(450, 400));
        setResizable(false);
        setFrameLocation(frameLocation);
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