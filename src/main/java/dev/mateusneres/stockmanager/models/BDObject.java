package dev.mateusneres.stockmanager.models;

public abstract class BDObject {

    public abstract void select();
    public abstract void save();
    public abstract void update();
    public abstract void delete();

}


