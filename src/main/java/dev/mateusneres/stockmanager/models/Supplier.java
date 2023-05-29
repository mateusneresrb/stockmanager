package dev.mateusneres.stockmanager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Supplier {

    private final int id;
    private final String name;
    private final String address;
    private final String phone;
    
}
