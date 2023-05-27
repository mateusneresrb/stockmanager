package dev.mateusneres.stockmanager.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class User extends BDObject{

    private UUID userID;
    private String name;
    private String nonce;
    private String password;


    @Override
    public void select() {

    }

    @Override
    public void save() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

}
