package me.udnekjupiter.physic;

import org.realityforge.vecmath.Vector3d;

public class PhysicsUtility {
    public static Vector3d valueToVector(double value){
        return new Vector3d(value, value, value);
    }
}
