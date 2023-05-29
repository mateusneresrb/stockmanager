package dev.mateusneres.stockmanager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class User {

    private UUID userID;
    private String name;
    private String nonce;
    private String password;

}
