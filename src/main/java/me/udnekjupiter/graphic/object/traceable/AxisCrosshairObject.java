package me.udnekjupiter.graphic.object.traceable;

import me.udnekjupiter.util.Triangle;
import org.realityforge.vecmath.Vector3d;

public class AxisCrosshairObject extends TraceableObject {

    private final Triangle planeX;
    private final Triangle planeY;
    private final Triangle planeZ;

    public AxisCrosshairObject(Vector3d position) {
        super(position);
        final float size = 0.05f;
        planeX = new Triangle(new Vector3d(1, 0, 0), new Vector3d(), new Vector3d(0, size, 0));
        planeY = new Triangle(new Vector3d(), new Vector3d(0, 1, 0), new Vector3d(-size, 0, -size));
        planeZ = new Triangle(new Vector3d(), new Vector3d(0, size, 0), new Vector3d(0, 0, 1));
    }

    public AxisCrosshairObject(){
        this(new Vector3d());
    }


    @Override
    public Triangle[] getRenderTriangles() {
        return new Triangle[]{planeX.copy(), planeY.copy(), planeZ.copy()};
    }
}
