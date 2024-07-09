package me.udnekjupiter.util;

import org.realityforge.vecmath.Vector3d;

public class Vector3x3 {

    public Vector3d x;
    public Vector3d y;
    public Vector3d z;

    public Vector3x3(Vector3d x, Vector3d y, Vector3d z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3x3(Vector3x3 v){
        this.x = v.x.dup();
        this.y = v.y.dup();
        this.z = v.z.dup();
    }

    public Vector3x3(){
        this.x = new Vector3d();
        this.y = new Vector3d();
        this.z = new Vector3d();
    }

    public Vector3x3 dup(){
        return new Vector3x3(this);
    }
}
