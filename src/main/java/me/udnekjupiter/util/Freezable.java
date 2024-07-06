package me.udnekjupiter.util;

public interface Freezable {
    void freeze();
    void unfreeze();
    boolean isFrozen();
    default void setFrozen(boolean freeze){
        if (freeze) freeze();
        else unfreeze();
    }
}
