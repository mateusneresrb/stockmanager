package dev.mateusneres.stockmanager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Supplier {

    private int id;
    private final String name;
    private final String address;
    private final String phone;
    
}
