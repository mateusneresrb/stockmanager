package dev.mateusneres.stockmanager.controllers;

import dev.mateusneres.stockmanager.models.Product;
import dev.mateusneres.stockmanager.models.PurchaseProduct;
import dev.mateusneres.stockmanager.models.Supplier;
import dev.mateusneres.stockmanager.models.User;
import dev.mateusneres.stockmanager.repositories.ProductRepository;
import dev.mateusneres.stockmanager.repositories.PurchaseProductRepository;
import dev.mateusneres.stockmanager.repositories.SupplierRepository;
import dev.mateusneres.stockmanager.repositories.UserRepository;
import dev.mateusneres.stockmanager.utils.DateUtil;
import lombok.Getter;

import java.util.List;

@Getter
public class StockController {

    private User loggedUser;
    private final List<Product> productList;
    private final List<Supplier> supplierList;
    private final List<PurchaseProduct> purchaseProductList;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseProductRepository purchaseProductRepository;

    public StockController(User user) {
        this.loggedUser = user;

        this.userRepository = new UserRepository();
        this.productRepository = new ProductRepository();
        this.supplierRepository = new SupplierRepository();
        this.purchaseProductRepository = new PurchaseProductRepository();

        /* LOAD ALL DATA */
        this.productList = productRepository.findAll();
        this.supplierList = supplierRepository.findAll();
        this.purchaseProductList = purchaseProductRepository.findAll();
    }


    public String[][] getPurchasesDataTable() {
        String[][] data = new String[purchaseProductList.size()][6];

        for (int i = 0; i < purchaseProductList.size(); i++) {
            PurchaseProduct purchaseProduct = purchaseProductList.get(i);
            data[i][0] = String.valueOf(purchaseProduct.getId());
            data[i][1] = purchaseProduct.getProduct().getName();
            data[i][2] = purchaseProduct.getSupplier().getName();
            data[i][3] = String.valueOf(purchaseProduct.getQuantity());
            data[i][4] = String.valueOf(purchaseProduct.getProduct().getPrice());
            data[i][5] = DateUtil.formatInstant(purchaseProduct.getPurchase().getPurchaseDate());
        }

        return data;
    }

    public String[][] getProductsDataTable(){
        String[][] data = new String[productList.size()][4];

        for(int i = 0; i < productList.size(); i++){
            Product product = productList.get(i);
            data[i][0] = String.valueOf(product.getId());
            data[i][1] = product.getName();
            data[i][2] = String.valueOf(product.getPrice());
            data[i][3] = String.valueOf(product.getAmountAvailable());
        }

        return data;
    }

    public String[][] getSupplierDataTable(){
        String[][] data = new String[supplierList.size()][4];

        for(int i = 0; i < supplierList.size(); i++){
            Supplier supplier = supplierList.get(i);
            data[i][0] = String.valueOf(supplier.getId());
            data[i][1] = supplier.getName();
            data[i][2] = supplier.getAddress();
            data[i][3] = supplier.getPhone();
        }

        return data;
    }

    public void logout() {
        loggedUser = null;
    }


}
