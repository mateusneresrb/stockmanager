package dev.mateusneres.stockmanager.controllers.popups;

import dev.mateusneres.stockmanager.controllers.ControllerAction;
import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.enums.OperationType;
import dev.mateusneres.stockmanager.models.Supplier;
import dev.mateusneres.stockmanager.views.components.SupplierHandleComponent;

import javax.swing.*;

/**
 * This class is responsible for controlling the popup supplier handle.
 */
public class SupplierHandleController implements ControllerAction {

    private final StockController stockController;
    private final SupplierHandleComponent productHandleComponent;

    /**
     * Constructor
     * @param stockController StockController
     * @param supplierHandleComponent SupplierHandleComponent
     */
    public SupplierHandleController(StockController stockController, SupplierHandleComponent supplierHandleComponent) {
        this.stockController = stockController;
        this.productHandleComponent = supplierHandleComponent;

        handleActions();
    }

    /**
     * This method is responsible for handling the actions of the buttons in the supplier handle.
     */
    @Override
    public void handleActions() {
        onButtonClickEvent();
    }

    private void onButtonClickEvent() {
        productHandleComponent.getConfirmButton().addActionListener(e -> {
            String idLabel = productHandleComponent.getIdLabel().getText().replace("ID: ", "").replace("...", "");
            String name = productHandleComponent.getNameField().getText();
            String address = productHandleComponent.getAddressField().getText();
            String phone = productHandleComponent.getPhoneField().getText();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(productHandleComponent.getNameField(), "Error: Unable to send with any of the fields empty!", "Empty field", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (phone.contains("_")) {
                JOptionPane.showMessageDialog(productHandleComponent.getNameField(), "Error: Phone field is invalid!", "Invalid field", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (idLabel.isEmpty() && productHandleComponent.getOperationType() == OperationType.CREATE) {
                boolean isCreated = stockController.createSupplier(new Supplier(name, address, phone));
                if (!isCreated) {
                    JOptionPane.showMessageDialog(productHandleComponent.getNameField(), "Error: Unable to create supplier!", "Error in supplier create!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(productHandleComponent.getNameField(), "A supplier " + name + " has been created!", "Supplier is created!", JOptionPane.INFORMATION_MESSAGE);
                productHandleComponent.dispose();
                return;
            }

            stockController.updateSupplier(new Supplier(Integer.parseInt(idLabel), name, address, phone));
            JOptionPane.showMessageDialog(productHandleComponent.getNameField(), "A supplier " + name + " has been updated!", "Supplier is updated!", JOptionPane.INFORMATION_MESSAGE);
            productHandleComponent.dispose();
        });
    }


}
