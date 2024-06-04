package me.udnek.object;

import me.udnek.util.Triangle;
import org.realityforge.vecmath.Vector3d;

public class PlaneObject extends SceneObject{

    private Triangle plane0;
    private Triangle plane1;

    public PlaneObject(Vector3d position, double x0, double z0, double x1, double z1, double y) {
        super(position);
        plane0 = new Triangle(new Vector3d(x0, y, z0), new Vector3d(x1, y, z1), new Vector3d(x0, y, z1));
        plane1 = new Triangle(new Vector3d(x0, y, z0), new Vector3d(x1, y, z1), new Vector3d(x1, y, z0));
    }

    @Override
    public Triangle[] getRenderTriangles() {
        return new Triangle[]{plane0.copy(), plane1.copy()};
    }
}
