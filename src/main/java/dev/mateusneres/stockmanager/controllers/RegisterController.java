package dev.mateusneres.stockmanager.controllers;

import dev.mateusneres.stockmanager.utils.EmailValidator;
import dev.mateusneres.stockmanager.views.LoginScreen;
import dev.mateusneres.stockmanager.views.SignUpScreen;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class RegisterController {

    private final SignUpScreen signUpScreen;

    public RegisterController(SignUpScreen signUpScreen) {
        this.signUpScreen = signUpScreen;
        handleActions();
    }

    private void handleActions() {
        onRegisterButtonClicked();
        onSignInLabelClicked();
    }

    private void onRegisterButtonClicked() {
        signUpScreen.getButton().addActionListener(e -> {
            String name = signUpScreen.getNameField().getText();
            String email = signUpScreen.getEmailField().getText();
            String password = Arrays.toString(signUpScreen.getPasswordField().getPassword());
            String confirmPassword = Arrays.toString(signUpScreen.getPasswordConfirmField().getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(signUpScreen.getEmailField(), "Error: Unable to send with any of the fields empty!", "Empty field", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!EmailValidator.validateEmail(email)) {
                JOptionPane.showMessageDialog(signUpScreen.getEmailField(), "Error: Invalid email format!", "Invalid email", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(signUpScreen.getEmailField(), "Error: Passwords do not match!", "Password mismatch", JOptionPane.ERROR_MESSAGE);
                return;
            }

            System.out.println(name + " " + email + " " + password + " " + confirmPassword);
        });
    }

    private void onSignInLabelClicked() {
        signUpScreen.getSignInLabel().addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                signUpScreen.dispose();

                LoginScreen loginScreen = new LoginScreen(signUpScreen.getLocation());
                new LoginController(loginScreen);
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
