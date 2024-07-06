package me.udnekjupiter.graphic.object.traceable;

import me.udnekjupiter.graphic.triangle.TraceableTriangle;
import org.realityforge.vecmath.Vector3d;

public class AxisCrosshairObject extends TraceableObject {

    private final TraceableTriangle planeX;
    private final TraceableTriangle planeY;
    private final TraceableTriangle planeZ;

    public AxisCrosshairObject(Vector3d position) {
        super(position);
        final float size = 0.05f;
        planeX = new TraceableTriangle(new Vector3d(1, 0, 0), new Vector3d(), new Vector3d(0, size, 0));
        planeY = new TraceableTriangle(new Vector3d(), new Vector3d(0, 1, 0), new Vector3d(-size, 0, -size));
        planeZ = new TraceableTriangle(new Vector3d(), new Vector3d(0, size, 0), new Vector3d(0, 0, 1));
    }

    public AxisCrosshairObject(){
        this(new Vector3d());
    }


    @Override
    public TraceableTriangle[] getRenderTriangles() {
        return new TraceableTriangle[]{planeX.copy(), planeY.copy(), planeZ.copy()};
    }
}
