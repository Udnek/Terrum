package me.udnekjupiter.util;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class Vector3x3 {

    public @NotNull Vector3d x;
    public @NotNull Vector3d y;
    public @NotNull Vector3d z;

    public Vector3x3(@NotNull Vector3d x, @NotNull Vector3d y, @NotNull Vector3d z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3x3(@NotNull Vector3x3 other){
        this(other.x.dup(), other.y.dup(), other.z.dup());
    }

    public Vector3x3(){
        this.x = new Vector3d();
        this.y = new Vector3d();
        this.z = new Vector3d();
    }

    public @NotNull Vector3x3 dup(){
        return new Vector3x3(this);
    }
}
