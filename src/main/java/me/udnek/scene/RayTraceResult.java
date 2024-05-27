package me.udnek.scene;

import org.realityforge.vecmath.Vector3d;

import javax.annotation.Nonnull;

public class RayTraceResult {

    private final Vector3d hitPosition;
    private int suggestedColor = 0;
    private double distance;

    public RayTraceResult(@Nonnull Vector3d hitPosition){
        this.hitPosition = hitPosition;
        this.distance = hitPosition.length();
    }

    public RayTraceResult(){
        this.hitPosition = null;
        this.distance = Double.POSITIVE_INFINITY;
    }

    public Vector3d getHitPosition() {
        return hitPosition.dup();
    }

    public double getDistance() {return this.distance;}
    public boolean isHit(){
        return hitPosition != null;
    }

    public int getSuggestedColor() {
        return suggestedColor;
    }

    public void setSuggestedColor(int suggestedColor) {
        this.suggestedColor = suggestedColor;
    }
}
