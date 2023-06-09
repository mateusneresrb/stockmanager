package dev.mateusneres.stockmanager.controllers.popups;


import dev.mateusneres.stockmanager.controllers.ControllerAction;
import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.enums.OperationType;
import dev.mateusneres.stockmanager.models.Product;
import dev.mateusneres.stockmanager.views.components.ProductHandleComponent;

import javax.swing.*;

public class ProductHandleController implements ControllerAction {

    private final StockController stockController;
    private final ProductHandleComponent productHandleComponent;

    public ProductHandleController(StockController stockController, ProductHandleComponent productHandleComponent) {
        this.stockController = stockController;
        this.productHandleComponent = productHandleComponent;

        handleActions();
    }

    @Override
    public void handleActions() {
        onButtonClickEvent();
    }

    private void onButtonClickEvent() {
        productHandleComponent.getConfirmButton().addActionListener(e -> {
            String idLabel = productHandleComponent.getIdLabel().getText().replace("ID: ", "").replace("...", "");
            String name = productHandleComponent.getNameField().getText();
            String priceText = productHandleComponent.getPriceField().getText();
            String amountText = productHandleComponent.getAmountField().getText();

            if (name.isEmpty() || priceText.isEmpty() || amountText.isEmpty()) {
                JOptionPane.showMessageDialog(productHandleComponent.getNameField(), "Error: Unable to send with any of the fields empty!", "Empty field", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double price = Double.parseDouble(priceText);

            int amount;
            try {
                amount = Integer.parseInt(amountText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(productHandleComponent.getNameField(), "The amount field contains invalid number!", "Error field", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (price < 0 || amount < 0) {
                JOptionPane.showMessageDialog(productHandleComponent.getNameField(), "Error: Numeric fields cannot be less than 0.", "Error field", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (idLabel.isEmpty() && productHandleComponent.getOperationType() == OperationType.CREATE) {
                boolean isCreated = stockController.createProduct(new Product(name, price, amount));
                if (!isCreated) {
                    JOptionPane.showMessageDialog(productHandleComponent.getNameField(), "Error: Unable to create product!", "Error in product create!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(productHandleComponent.getNameField(), "A product " + name + " has been created!", "Product is created!", JOptionPane.INFORMATION_MESSAGE);
                productHandleComponent.dispose();
                return;
            }

            stockController.updateProduct(new Product(Integer.parseInt(idLabel), name, price, amount));
            JOptionPane.showMessageDialog(productHandleComponent.getNameField(), "A product " + name + " has been updated!", "Product is updated!", JOptionPane.INFORMATION_MESSAGE);
            productHandleComponent.dispose();
        });
    }

}
