package dev.mateusneres.stockmanager.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PurchaseProduct {

    private int id;
    private Product product;
    private Purchase purchase;
    private Supplier supplier;
    private int quantity;

    public PurchaseProduct(Product product, Purchase purchase, Supplier supplier, int quantity) {
        this.product = product;
        this.purchase = purchase;
        this.supplier = supplier;
        this.quantity = quantity;
    }

}
