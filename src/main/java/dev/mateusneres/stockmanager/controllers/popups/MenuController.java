package dev.mateusneres.stockmanager.controllers.popups;

import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.views.components.MenuComponent;
import dev.mateusneres.stockmanager.views.components.ProductListComponent;

public class MenuController {

    private final StockController stockController;
    private final MenuComponent menuComponent;

    public MenuController(StockController stockController, MenuComponent menuComponent) {
        this.stockController = stockController;
        this.menuComponent = menuComponent;

        handleActions();
    }

    private void handleActions() {
        onAddProductButtonClicked();
        onListProductsButtonClicked();
        onAddSupplierButtonClicked();
        onListSupplierButtonClicked();
    }

    public void onAddProductButtonClicked() {
        menuComponent.getAddProductButton().addActionListener(e -> {
            System.out.println("Add product button clicked");
        });
    }

    public void onListProductsButtonClicked() {
        menuComponent.getListProductsButton().addActionListener(e -> {
            System.out.println("CLICOU NO LIST...");
            new ProductListComponent(stockController.getProductsDataTable());
        });
    }

    public void onAddSupplierButtonClicked() {
        menuComponent.getAddSupplierButton().addActionListener(e -> {
            System.out.println("Add supplier button clicked");
        });
    }

    public void onListSupplierButtonClicked() {
        menuComponent.getListSupplierButton().addActionListener(e -> {
            System.out.println("List supplier button clicked");
        });
    }

}
