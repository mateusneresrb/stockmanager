package dev.mateusneres.stockmanager.controllers;

import dev.mateusneres.stockmanager.controllers.popups.MenuController;
import dev.mateusneres.stockmanager.controllers.popups.PurchaseHandleController;
import dev.mateusneres.stockmanager.enums.OperationType;
import dev.mateusneres.stockmanager.models.PurchaseProduct;
import dev.mateusneres.stockmanager.repositories.PurchaseProductRepository;
import dev.mateusneres.stockmanager.views.HomeScreen;
import dev.mateusneres.stockmanager.views.LoginScreen;
import dev.mateusneres.stockmanager.views.components.MenuComponent;
import dev.mateusneres.stockmanager.views.components.PurchaseHandleComponent;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class is responsible for controlling the home screen.
 */
public class HomeController implements ControllerAction {

    private final StockController stockController;
    private final HomeScreen homeScreen;
    private final PurchaseProductRepository purchaseProductRepository;

    /**
     * Constructor
     *
     * @param stockController StockController
     * @param homeScreen HomeScreen
     */
    public HomeController(StockController stockController, HomeScreen homeScreen) {
        this.stockController = stockController;
        this.homeScreen = homeScreen;
        this.purchaseProductRepository = new PurchaseProductRepository();

        handleActions();
    }

    /**
     * This method is responsible for handling the actions of the buttons in the home screen.
     */
    @Override
    public void handleActions() {
        onLogoutClicked();
        onSearchFieldChanged();
        onAddButtonClicked();
        onEditCellClicked();
        onDeleteCellClicked();
    }
    
    /**
     * This method is responsible for update the table data.
     */
    public void updateTableData() {
        homeScreen.updateTableData(stockController.getPurchasesDataTable());
    }

    private void onAddButtonClicked() {
        homeScreen.getAddButton().addActionListener(e -> {
            MenuComponent menuComponent = new MenuComponent(this);
            new MenuController(stockController, menuComponent);
        });
    }

    private void onEditCellClicked() {
        homeScreen.getEditButton().addActionListener(e -> {
            int purchaseId = getSelectedPurchaseId((JButton) e.getSource());

            PurchaseProduct purchaseProduct = stockController.getPurchaseProductList().stream().filter(purchaseProduct1 -> purchaseProduct1.getId() == purchaseId).findFirst().orElse(null);
            if (purchaseProduct == null) {
                JOptionPane.showMessageDialog(null, "Purchase not available!", "Not found purchase!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PurchaseHandleComponent purchaseHandleComponent = new PurchaseHandleComponent(this, OperationType.UPDATE, stockController.getProductsWithID(), stockController.getSupplierWithID(), purchaseProduct);
            new PurchaseHandleController(stockController, purchaseHandleComponent);
        });
    }

    private void onDeleteCellClicked() {
        homeScreen.getDeleteButton().addActionListener(e -> {
            JXTable table = (JXTable) SwingUtilities.getAncestorOfClass(JXTable.class, (JButton) e.getSource());

            int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int column = table.getColumnModel().getColumnIndex("ID");
            int id = Integer.parseInt((String) table.getModel().getValueAt(modelRow, column));

            stockController.getPurchaseProductList().removeIf(purchaseProduct -> purchaseProduct.getId() == id);
            purchaseProductRepository.deletePurchaseProduct(id);
            model.removeRow(modelRow);
        });
    }

    private void onSearchFieldChanged() {
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

    private void onLogoutClicked() {
        homeScreen.getLogoutLabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                homeScreen.dispose();

                LoginScreen loginScreen = new LoginScreen(null);
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

    private int getSelectedPurchaseId(JButton button) {
        JXTable table = (JXTable) SwingUtilities.getAncestorOfClass(JXTable.class, button);

        int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
        int column = table.getColumnModel().getColumnIndex("ID");

        return Integer.parseInt((String) table.getModel().getValueAt(modelRow, column));
    }

}
