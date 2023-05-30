package dev.mateusneres.stockmanager.controllers.popups;

import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.repositories.SupplierRepository;
import dev.mateusneres.stockmanager.views.components.SupplierListComponent;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SupplierListController {

    private final StockController stockController;
    private final SupplierListComponent supplierListComponent;
    private final SupplierRepository supplierRepository;

    public SupplierListController(StockController stockController, SupplierListComponent supplierListComponent) {
        this.stockController = stockController;
        this.supplierListComponent = supplierListComponent;
        this.supplierRepository = new SupplierRepository();

        handleActions();
    }

    private void handleActions() {
        onEditButtonClicked();
        onDeleteButtonClicked();
    }

    private void onEditButtonClicked() {
        supplierListComponent.getEditButton().addActionListener(e -> {
            //OPEN OTHER SCREEN SIMILAR ADD...
        });
    }

    private void onDeleteButtonClicked() {
        supplierListComponent.getDeleteButton().addActionListener(e -> {
            JXTable table = (JXTable) SwingUtilities.getAncestorOfClass(JXTable.class, (JButton) e.getSource());

            int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int column = table.getColumnModel().getColumnIndex("ID");
            int id = Integer.parseInt((String) table.getModel().getValueAt(modelRow, column));

            stockController.getSupplierList().removeIf(product -> product.getId() == id);
            supplierRepository.deleteSupplier(id);
            model.removeRow(modelRow);
        });
    }

}
