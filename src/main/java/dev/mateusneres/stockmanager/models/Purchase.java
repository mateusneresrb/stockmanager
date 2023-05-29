package dev.mateusneres.stockmanager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class Purchase {

    private final int id;
    private final Instant purchaseDate;
    private final double totalValue;
    private final Supplier supplier;

}
