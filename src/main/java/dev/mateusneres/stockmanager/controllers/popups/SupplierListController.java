package dev.mateusneres.stockmanager.controllers.popups;

import dev.mateusneres.stockmanager.controllers.ControllerAction;
import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.enums.OperationType;
import dev.mateusneres.stockmanager.models.Supplier;
import dev.mateusneres.stockmanager.repositories.SupplierRepository;
import dev.mateusneres.stockmanager.views.components.SupplierHandleComponent;
import dev.mateusneres.stockmanager.views.components.SupplierListComponent;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SupplierListController implements ControllerAction {

    private final StockController stockController;
    private final SupplierListComponent supplierListComponent;
    private final SupplierRepository supplierRepository;

    public SupplierListController(StockController stockController, SupplierListComponent supplierListComponent) {
        this.stockController = stockController;
        this.supplierListComponent = supplierListComponent;
        this.supplierRepository = new SupplierRepository();

        handleActions();
    }

    @Override
    public void handleActions() {
        onEditButtonClicked();
        onDeleteButtonClicked();
    }

    private void onEditButtonClicked() {
        supplierListComponent.getEditButton().addActionListener(e -> {
            int supplierID = getSelectedSupplierId((JButton) e.getSource());

            Supplier supplier = stockController.getSupplierList().stream().filter(supplier1 -> supplier1.getId() == supplierID).findFirst().orElse(null);
            if (supplier == null) {
                JOptionPane.showMessageDialog(null, "Supplier not available!", "Not found supplier!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SupplierHandleComponent supplierHandleComponent = new SupplierHandleComponent(supplierListComponent.getHomeController(), OperationType.UPDATE, supplier);
            new SupplierHandleController(stockController, supplierHandleComponent);
        });
    }

    private void onDeleteButtonClicked() {
        supplierListComponent.getDeleteButton().addActionListener(e -> {
            JXTable table = (JXTable) SwingUtilities.getAncestorOfClass(JXTable.class, (JButton) e.getSource());

            int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int column = table.getColumnModel().getColumnIndex("ID");
            int id = Integer.parseInt((String) table.getModel().getValueAt(modelRow, column));

            stockController.getPurchaseProductList().removeIf(purchaseProduct -> purchaseProduct.getSupplier().getId() == id);
            stockController.getSupplierList().removeIf(supplier -> supplier.getId() == id);
            supplierRepository.deleteSupplier(id);
            model.removeRow(modelRow);
        });
    }

    private int getSelectedSupplierId(JButton button) {
        JXTable table = (JXTable) SwingUtilities.getAncestorOfClass(JXTable.class, button);

        int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
        int column = table.getColumnModel().getColumnIndex("ID");

        return Integer.parseInt((String) table.getModel().getValueAt(modelRow, column));
    }

}
