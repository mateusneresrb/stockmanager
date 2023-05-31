package dev.mateusneres.stockmanager.controllers.popups;

import dev.mateusneres.stockmanager.controllers.ControllerAction;
import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.enums.OperationType;
import dev.mateusneres.stockmanager.views.components.*;

public class MenuController implements ControllerAction {

    private final StockController stockController;
    private final MenuComponent menuComponent;

    public MenuController(StockController stockController, MenuComponent menuComponent) {
        this.stockController = stockController;
        this.menuComponent = menuComponent;

        handleActions();
    }

    @Override
    public void handleActions() {
        onAddProductButtonClicked();
        onListProductsButtonClicked();
        onAddSupplierButtonClicked();
        onListSupplierButtonClicked();
        onAddPurchaseButtonClicked();
    }

    private void onAddPurchaseButtonClicked(){
        menuComponent.getAddPurchaseButton().addActionListener(e -> {
            PurchaseHandleComponent purchaseHandleComponent = new PurchaseHandleComponent(menuComponent.getHomeController(), OperationType.CREATE, stockController.getProductsWithID(), stockController.getSupplierWithID(), null);
            new PurchaseHandleController(stockController, purchaseHandleComponent);
        });
    }

    public void onAddProductButtonClicked() {
        menuComponent.getAddProductButton().addActionListener(e -> {
            ProductHandleComponent productHandleComponent = new ProductHandleComponent(menuComponent.getHomeController(), OperationType.CREATE, null);
            new ProductHandleController(stockController, productHandleComponent);
        });
    }

    private void onListProductsButtonClicked() {
        menuComponent.getListProductsButton().addActionListener(e -> {
            ProductListComponent productListComponent = new ProductListComponent(menuComponent.getHomeController(), stockController.getProductsDataTable());
            new ProductListController(stockController, productListComponent);
        });
    }

    private void onAddSupplierButtonClicked() {
        menuComponent.getAddSupplierButton().addActionListener(e -> {
            SupplierHandleComponent supplierHandleComponent = new SupplierHandleComponent(menuComponent.getHomeController(), OperationType.CREATE, null);
            new SupplierHandleController(stockController, supplierHandleComponent);
        });
    }

    private void onListSupplierButtonClicked() {
        menuComponent.getListSupplierButton().addActionListener(e -> {
            SupplierListComponent supplierListComponent = new SupplierListComponent(menuComponent.getHomeController(), stockController.getSupplierDataTable());
            new SupplierListController(stockController, supplierListComponent);
        });
    }

}
