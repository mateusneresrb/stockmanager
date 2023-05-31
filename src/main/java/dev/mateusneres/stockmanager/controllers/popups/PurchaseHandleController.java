package dev.mateusneres.stockmanager.controllers.popups;

import dev.mateusneres.stockmanager.controllers.ControllerAction;
import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.enums.OperationType;
import dev.mateusneres.stockmanager.models.Product;
import dev.mateusneres.stockmanager.models.Purchase;
import dev.mateusneres.stockmanager.models.PurchaseProduct;
import dev.mateusneres.stockmanager.models.Supplier;
import dev.mateusneres.stockmanager.repositories.PurchaseProductRepository;
import dev.mateusneres.stockmanager.views.components.PurchaseHandleComponent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.time.Instant;

public class PurchaseHandleController implements ControllerAction {

    private final StockController stockController;
    private final PurchaseHandleComponent productHandleComponent;
    private final PurchaseProductRepository purchaseProductRepository;

    public PurchaseHandleController(StockController stockController, PurchaseHandleComponent purchaseHandleComponent) {
        this.stockController = stockController;
        this.productHandleComponent = purchaseHandleComponent;
        this.purchaseProductRepository = new PurchaseProductRepository();

        handleActions();
    }

    @Override
    public void handleActions() {
        onButtonClickEvent();
        onAmountFieldChangeEvent();
    }

    private void onButtonClickEvent() {
        productHandleComponent.getConfirmButton().addActionListener(e -> {
            String idLabel = productHandleComponent.getIdLabel().getText().replace("ID: ", "").replace("...", "");
            Object productSelected = productHandleComponent.getProductComboBox().getSelectedItem();
            Object supplierSelected = productHandleComponent.getSupplierComboBox().getSelectedItem();
            String amount = productHandleComponent.getAmountField().getText();
            String totalPriceResult = productHandleComponent.getTotalPriceResult().getText().replace("R$ ", "");

            if (productSelected == null || supplierSelected == null || amount.isEmpty() || totalPriceResult.isEmpty()) {
                JOptionPane.showMessageDialog(productHandleComponent.getProductComboBox(), "Error: Unable to send with any of the fields empty!", "Empty field", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double totalPrice = Double.parseDouble(totalPriceResult);
            Product product = getProductBySelection(productSelected);
            Supplier supplier = getSupplierBySelection(supplierSelected);
            Purchase purchase = new Purchase(Instant.now(), totalPrice, supplier);

            if (product == null || supplier == null) {
                JOptionPane.showMessageDialog(productHandleComponent.getProductComboBox(), "Error: Unable to get product or supplier!", "Invalid field", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (idLabel.isEmpty() && productHandleComponent.getOperationType() == OperationType.CREATE) {
                boolean isCreated = createPurchase(new PurchaseProduct(product, purchase, supplier, Integer.parseInt(amount)));
                if (!isCreated) {
                    JOptionPane.showMessageDialog(productHandleComponent.getProductComboBox(), "Error: Unable to create purchase!", "Error in purchase create!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(productHandleComponent.getProductComboBox(), "Purchase has been created!", "Purchase is created!", JOptionPane.INFORMATION_MESSAGE);
                productHandleComponent.dispose();
                return;
            }

            updatePurchase(new PurchaseProduct(Integer.parseInt(idLabel), product, purchase, supplier, Integer.parseInt(amount)));
            JOptionPane.showMessageDialog(productHandleComponent.getProductComboBox(), "Purchase has been updated!", "Purchase is updated!", JOptionPane.INFORMATION_MESSAGE);
            productHandleComponent.dispose();
        });
    }

    private void onAmountFieldChangeEvent() {
        productHandleComponent.getAmountField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateValue();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateValue();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateValue();
            }

            private void updateValue() {
                Object productSelected = productHandleComponent.getProductComboBox().getSelectedItem();
                String amount = productHandleComponent.getAmountField().getText();

                if (productSelected == null || amount.isEmpty() || amount.length() > 9) {
                    return;
                }

                Product product = getProductBySelection(productSelected);
                double totalValue = Integer.parseInt(amount) * product.getPrice();

                productHandleComponent.getTotalPriceResult().setText(String.format("R$ %.2f", totalValue));
            }
        });
    }

    private boolean createPurchase(PurchaseProduct purchaseProduct) {
        Purchase createdPurchase = purchaseProductRepository.createPurchase(purchaseProduct.getPurchase());
        if (createdPurchase == null) return false;

        PurchaseProduct createPurchase = purchaseProductRepository.createPurchaseProduct(
                purchaseProduct.getProduct(), createdPurchase,
                purchaseProduct.getSupplier(), purchaseProduct.getQuantity());
        if (createPurchase == null) return false;

        stockController.getPurchaseProductList().add(createPurchase);
        return true;
    }

    private void updatePurchase(PurchaseProduct purchaseProduct) {
        purchaseProductRepository.updatePurchaseProduct(purchaseProduct);

        stockController.getPurchaseProductList().removeIf(purchaseProduct1 -> purchaseProduct1.getId() == purchaseProduct.getId());
        stockController.getPurchaseProductList().add(purchaseProduct);
    }

    private Product getProductBySelection(Object productSelected) {
        int productID = Integer.parseInt(productSelected.toString().split(" - ")[0]);
        return stockController.getProductList().stream().filter(product1 -> product1.getId() == productID).findFirst().orElse(null);
    }

    private Supplier getSupplierBySelection(Object supplierSelected) {
        int supplierID = Integer.parseInt(supplierSelected.toString().split(" - ")[0]);
        return stockController.getSupplierList().stream().filter(supplier1 -> supplier1.getId() == supplierID).findFirst().orElse(null);
    }

}
