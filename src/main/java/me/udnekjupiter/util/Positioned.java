package me.udnekjupiter.util;

import org.realityforge.vecmath.Vector3d;

public interface Positioned {
    Vector3d getPosition();
    void setPosition(Vector3d position);
    void move(Vector3d position);
    void move(double x, double y, double z);

}
