package dev.mateusneres.stockmanager.controllers.popups;

import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.enums.OperationType;
import dev.mateusneres.stockmanager.models.Product;
import dev.mateusneres.stockmanager.repositories.ProductRepository;
import dev.mateusneres.stockmanager.views.components.ProductHandleComponent;
import dev.mateusneres.stockmanager.views.components.ProductListComponent;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductListController {

    private final StockController stockController;
    private final ProductListComponent productListComponent;
    private final ProductRepository productRepository;

    public ProductListController(StockController stockController, ProductListComponent productListComponent) {
        this.stockController = stockController;
        this.productListComponent = productListComponent;
        this.productRepository = new ProductRepository();

        handleActions();
    }

    private void handleActions() {
        onEditButtonClicked();
        onDeleteButtonClicked();
    }

    private void onEditButtonClicked() {
        productListComponent.getEditButton().addActionListener(e -> {
            int productID = getSelectedProductId((JButton) e.getSource());

            Product product = stockController.getProductList().stream().filter(product1 -> product1.getId() == productID).findFirst().orElse(null);
            if (product == null) {
                JOptionPane.showMessageDialog(null, "Product not available!", "Not found product!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ProductHandleComponent productHandleComponent = new ProductHandleComponent(productListComponent.getHomeController(), OperationType.UPDATE, product);
            new ProductHandleController(stockController, productHandleComponent);
        });
    }

    private void onDeleteButtonClicked() {
        productListComponent.getDeleteButton().addActionListener(e -> {
            JXTable table = (JXTable) SwingUtilities.getAncestorOfClass(JXTable.class, (JButton) e.getSource());

            int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int column = table.getColumnModel().getColumnIndex("ID");
            int id = Integer.parseInt((String) table.getModel().getValueAt(modelRow, column));

            stockController.getPurchaseProductList().removeIf(purchaseProduct -> purchaseProduct.getProduct().getId() == id);
            stockController.getProductList().removeIf(product -> product.getId() == id);
            productRepository.deleteProduct(id);
            model.removeRow(modelRow);
        });
    }

    private int getSelectedProductId(JButton button) {
        JXTable table = (JXTable) SwingUtilities.getAncestorOfClass(JXTable.class, button);

        int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
        int column = table.getColumnModel().getColumnIndex("ID");

        return Integer.parseInt((String) table.getModel().getValueAt(modelRow, column));
    }
    
}
