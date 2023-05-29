package dev.mateusneres.stockmanager.controllers;

import dev.mateusneres.stockmanager.repositories.AuthRepository;
import dev.mateusneres.stockmanager.utils.EmailValidator;
import dev.mateusneres.stockmanager.views.LoginScreen;
import dev.mateusneres.stockmanager.views.SignUpScreen;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class LoginController {

    private final LoginScreen loginScreen;
    private final AuthRepository authRepository;

    public LoginController(LoginScreen loginScreen) {
        this.loginScreen = loginScreen;
        this.authRepository = new AuthRepository();

        handleActions();
    }

    private void handleActions() {
        onLoginButtonClicked();
        onSignUpLabelClicked();
    }

    private void onLoginButtonClicked() {
        loginScreen.getButton().addActionListener(e -> {
            String email = loginScreen.getEmailField().getText();
            char[] password = loginScreen.getPasswordField().getPassword();

            if (email.isEmpty() || password.length == 0) {
                JOptionPane.showMessageDialog(loginScreen.getEmailField(), "Error: Unable to send with any of the fields empty!", "Empty field", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!EmailValidator.validateEmail(email)) {
                JOptionPane.showMessageDialog(loginScreen.getEmailField(), "Error: Invalid email format!", "Invalid email", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean isLogged = authRepository.login(email, password);
            if (!isLogged) {
                JOptionPane.showMessageDialog(loginScreen.getEmailField(), "Error: Invalid email or password!", "Invalid email or password", JOptionPane.ERROR_MESSAGE);
                return;
            }

        });
    }

    private void onSignUpLabelClicked() {
        loginScreen.getSignUpLabel().addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                loginScreen.dispose();

                SignUpScreen signUpScreen = new SignUpScreen(loginScreen.getLocation());
                new RegisterController(signUpScreen);
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
