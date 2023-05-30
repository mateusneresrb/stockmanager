package dev.mateusneres.stockmanager.controllers.popups;

import dev.mateusneres.stockmanager.controllers.ControllerAction;
import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.enums.OperationType;
import dev.mateusneres.stockmanager.views.components.MenuComponent;
import dev.mateusneres.stockmanager.views.components.ProductHandleComponent;
import dev.mateusneres.stockmanager.views.components.ProductListComponent;
import dev.mateusneres.stockmanager.views.components.SupplierListComponent;

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
    }

    public void onAddProductButtonClicked() {
        menuComponent.getAddProductButton().addActionListener(e -> {
            ProductHandleComponent productHandleComponent = new ProductHandleComponent(OperationType.CREATE, null);
            new ProductHandleController(stockController, productHandleComponent);
        });
    }

    public void onListProductsButtonClicked() {
        menuComponent.getListProductsButton().addActionListener(e -> {
           ProductListComponent productListComponent = new ProductListComponent(stockController.getProductsDataTable());
           new ProductListController(stockController, productListComponent);
        });
    }

    public void onAddSupplierButtonClicked() {
        menuComponent.getAddSupplierButton().addActionListener(e -> {
            System.out.println("Add supplier button clicked");
        });
    }

    public void onListSupplierButtonClicked() {
        menuComponent.getListSupplierButton().addActionListener(e -> {
            SupplierListComponent supplierListComponent = new SupplierListComponent(stockController.getSupplierDataTable());
            new SupplierListController(stockController, supplierListComponent);
        });
    }

}
