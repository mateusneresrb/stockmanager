package dev.mateusneres.stockmanager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Product {

    private final int id;
    private final String name;
    private final double price;
    private final int amountAvailable;

}
