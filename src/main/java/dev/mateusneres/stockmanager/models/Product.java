package dev.mateusneres.stockmanager.models;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
public class Product {

    private int id;
    private final String name;
    private final double price;
    private final int amountAvailable;

}
