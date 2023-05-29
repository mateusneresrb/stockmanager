package dev.mateusneres.stockmanager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PurchaseProduct {

    private final int id;
    private final Product product;
    private final Purchase purchase;
    private final Supplier supplier;
    private final int quantity;

}
