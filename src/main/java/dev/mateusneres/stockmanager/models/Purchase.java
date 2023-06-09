package dev.mateusneres.stockmanager.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
public class Purchase {

    private int id;
    private final Instant purchaseDate;
    private final double totalValue;
    private final Supplier supplier;

}
