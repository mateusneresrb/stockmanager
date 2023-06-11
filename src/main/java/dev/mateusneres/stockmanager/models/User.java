package dev.mateusneres.stockmanager.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class User {

    private UUID userID;
    private String name;
    private String email;
    private String nonce;
    private String password;

}
