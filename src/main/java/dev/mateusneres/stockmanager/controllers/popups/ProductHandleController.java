package dev.mateusneres.stockmanager.controllers.popups;


import dev.mateusneres.stockmanager.controllers.ControllerAction;
import dev.mateusneres.stockmanager.controllers.StockController;
import dev.mateusneres.stockmanager.repositories.ProductRepository;
import dev.mateusneres.stockmanager.views.components.ProductHandleComponent;

public class ProductHandleController implements ControllerAction {

    private final StockController stockController;
    private final ProductHandleComponent productHandleComponent;
    private final ProductRepository productRepository;

    public ProductHandleController(StockController stockController, ProductHandleComponent productHandleComponent) {
        this.stockController = stockController;
        this.productHandleComponent = productHandleComponent;
        this.productRepository = new ProductRepository();

        handleActions();
    }

    @Override
    public void handleActions() {
        onButtonClickEvent();
    }

    private void onButtonClickEvent() {
        productHandleComponent.getConfirmButton().addActionListener(e -> {
            //VALIDATE SOME INFORMATIONS...

            System.out.println("CLICOU: " + productHandleComponent.getOperationType().name());
        });
    }


}
