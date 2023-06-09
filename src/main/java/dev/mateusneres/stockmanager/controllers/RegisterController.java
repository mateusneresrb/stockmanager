package dev.mateusneres.stockmanager.controllers;

import dev.mateusneres.stockmanager.repositories.UserRepository;
import dev.mateusneres.stockmanager.utils.EmailValidator;
import dev.mateusneres.stockmanager.views.LoginScreen;
import dev.mateusneres.stockmanager.views.SignUpScreen;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class RegisterController implements ControllerAction{

    private final StockController stockController;
    private final SignUpScreen signUpScreen;
    private final UserRepository userRepository;

    public RegisterController(StockController stockController, SignUpScreen signUpScreen) {
        this.stockController = stockController;
        this.signUpScreen = signUpScreen;
        this.userRepository = new UserRepository();

        handleActions();
    }

    @Override
    public void handleActions() {
        onRegisterButtonClicked();
        onSignInLabelClicked();
    }

    private void onRegisterButtonClicked() {
        signUpScreen.getButton().addActionListener(e -> {
            String name = signUpScreen.getNameField().getText();
            String email = signUpScreen.getEmailField().getText();
            char[] password = signUpScreen.getPasswordField().getPassword();
            char[] confirmPassword = signUpScreen.getPasswordConfirmField().getPassword();

            if (name.isEmpty() || email.isEmpty() || password.length == 0 || confirmPassword.length == 0) {
                JOptionPane.showMessageDialog(signUpScreen.getEmailField(), "Error: Unable to send with any of the fields empty!", "Empty field", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!EmailValidator.validateEmail(email)) {
                JOptionPane.showMessageDialog(signUpScreen.getEmailField(), "Error: Invalid email format!", "Invalid email", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!Arrays.equals(password, confirmPassword)) {
                JOptionPane.showMessageDialog(signUpScreen.getEmailField(), "Error: Passwords do not match!", "Password mismatch", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean isRegistered = userRepository.register(name, email, password);

            if (!isRegistered) {
                JOptionPane.showMessageDialog(signUpScreen.getEmailField(), "An account with this email address already exists", "Invalid email", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(signUpScreen.getEmailField(), "Account is registered!", "Your account is successful registered, you need login!", JOptionPane.INFORMATION_MESSAGE);
            signUpScreen.dispose();

            LoginScreen loginScreen = new LoginScreen(signUpScreen.getLocation());
            new LoginController(stockController, loginScreen);
        });
    }

    private void onSignInLabelClicked() {
        signUpScreen.getSignInLabel().addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                signUpScreen.dispose();

                LoginScreen loginScreen = new LoginScreen(signUpScreen.getLocation());
                new LoginController(stockController, loginScreen);
            }

            @Override
            public void mousePressed(MouseEvent e) { /* Not use */ }

            @Override
            public void mouseReleased(MouseEvent e) { /* Not use */ }

            @Override
            public void mouseEntered(MouseEvent e) { /* Not use */ }

            @Override
            public void mouseExited(MouseEvent e) { /* Not use */ }
        });

    }


}
