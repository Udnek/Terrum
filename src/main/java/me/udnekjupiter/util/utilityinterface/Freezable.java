package me.udnekjupiter.util.utilityinterface;

public interface Freezable {
    void freeze();
    void unfreeze();
    boolean isFrozen();
    default void setFrozen(boolean freeze){
        if (freeze) freeze();
        else unfreeze();
    }
}
