package dev.mateusneres.stockmanager.controllers;

import dev.mateusneres.stockmanager.models.*;
import dev.mateusneres.stockmanager.repositories.ProductRepository;
import dev.mateusneres.stockmanager.repositories.PurchaseProductRepository;
import dev.mateusneres.stockmanager.repositories.SupplierRepository;
import dev.mateusneres.stockmanager.repositories.UserRepository;
import dev.mateusneres.stockmanager.utils.DateUtil;
import lombok.Getter;

import java.util.List;

@Getter
public class StockController {

    private final List<Product> productList;
    private final List<Supplier> supplierList;
    private final List<PurchaseProduct> purchaseProductList;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseProductRepository purchaseProductRepository;

    public StockController() {
        this.userRepository = new UserRepository();
        this.productRepository = new ProductRepository();
        this.supplierRepository = new SupplierRepository();
        this.purchaseProductRepository = new PurchaseProductRepository();

        /* LOAD ALL DATA */
        this.productList = productRepository.findAll();
        this.supplierList = supplierRepository.findAll();
        this.purchaseProductList = purchaseProductRepository.findAll();
    }

    public boolean createProduct(Product product) {
        Product productCreated = productRepository.createProduct(product);
        if (productCreated == null) return false;

        getProductList().add(productCreated);
        return true;
    }

    public void updateProduct(Product product) {
        productRepository.updateProduct(product);

        getPurchaseProductList().stream()
                .filter(product1 -> product1.getProduct().getId() == product.getId())
                .forEach(product1 -> product1.setProduct(product));

        getProductList().removeIf(product1 -> product1.getId() == product.getId());
        getProductList().add(product);
    }

    public boolean createSupplier(Supplier supplier) {
        Supplier supplierCreated = supplierRepository.createSupplier(supplier);
        if (supplierCreated == null) return false;

        getSupplierList().add(supplierCreated);
        return true;
    }

    public void updateSupplier(Supplier supplier) {
        supplierRepository.updateSupplier(supplier);

        getPurchaseProductList().stream()
                .filter(purchaseProduct -> purchaseProduct.getSupplier().getId() == supplier.getId())
                .forEach(purchaseProduct -> purchaseProduct.setSupplier(supplier));

        getSupplierList().removeIf(supplier1 -> supplier1.getId() == supplier.getId());
        getSupplierList().add(supplier);
    }
    
    public boolean createPurchaseProduct(PurchaseProduct purchaseProduct) {
        Purchase createdPurchase = purchaseProductRepository.createPurchase(purchaseProduct.getPurchase());
        if (createdPurchase == null) return false;

        PurchaseProduct createPurchase = purchaseProductRepository.createPurchaseProduct(
                purchaseProduct.getProduct(), createdPurchase,
                purchaseProduct.getSupplier(), purchaseProduct.getQuantity());
        if (createPurchase == null) return false;

        getPurchaseProductList().add(createPurchase);
        return true;
    }

    public void updatePurchase(PurchaseProduct purchaseProduct) {
        purchaseProductRepository.updatePurchase(purchaseProduct.getPurchase());
        purchaseProductRepository.updatePurchaseProduct(purchaseProduct);

        getPurchaseProductList().removeIf(purchaseProduct1 -> purchaseProduct1.getId() == purchaseProduct.getId());
        getPurchaseProductList().add(purchaseProduct);
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

    public String[] getSupplierWithID(){
        return supplierList.stream().map(supplier -> supplier.getId() + " - " + supplier.getName()).toArray(String[]::new);
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

    public String[] getProductsWithID(){
        return productList.stream().map(product -> product.getId() + " - " + product.getName() + " - R$ " + product.getPrice()).toArray(String[]::new);
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

}
