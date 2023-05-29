package dev.mateusneres.stockmanager.controllers;

import dev.mateusneres.stockmanager.views.HomeScreen;
import dev.mateusneres.stockmanager.views.LoginScreen;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HomeController {

    private final StockController stockController;
    private final HomeScreen homeScreen;

    public HomeController(StockController stockController, HomeScreen homeScreen) {
        this.stockController = stockController;
        this.homeScreen = homeScreen;

        handleActions();
    }

    private void handleActions() {
        onLogoutClicked();
        onSearchFieldChanged();
    }

    public void onSearchFieldChanged() {
        homeScreen.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applyFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyFilter();
            }

            private void applyFilter() {
                String searchText = homeScreen.getSearchField().getText();

                if (searchText.length() > 0) {
                    RowFilter<DefaultTableModel, Integer> rowFilter = RowFilter.regexFilter("(?i)" + searchText);
                    homeScreen.getRowSorter().setRowFilter(rowFilter);
                    return;
                }

                homeScreen.getRowSorter().setRowFilter(null);
            }

        });
    }


    public void onLogoutClicked() {
        homeScreen.getLogoutLabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                homeScreen.dispose();
                stockController.logout();

                LoginScreen loginScreen = new LoginScreen(null);
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
