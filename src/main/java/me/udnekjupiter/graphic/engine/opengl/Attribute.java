package me.udnekjupiter.graphic.engine.opengl;

import org.jetbrains.annotations.NotNull;

public class Attribute {

    protected String name;
    protected int size;

    public Attribute(@NotNull String name, int size){
        this.name = name;
        this.size = size;
    }

    public @NotNull String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
