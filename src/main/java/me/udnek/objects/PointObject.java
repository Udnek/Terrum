package me.udnek.objects;

import org.realityforge.vecmath.Vector3d;

public class PointObject extends SceneObject{
    public PointObject(double x, double y, double z) {
        this(new Vector3d(x, y, z));
    }

    public PointObject(int x, int y, int z) {
        this(new Vector3d(x, y, z));
    }

    public PointObject(Vector3d position) {
        super(position);
    }
}
