package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.realityforge.vecmath.Vector3d;

public class AxisCrosshairObject extends RenderableObject {

    private final RenderableTriangle planeX;
    private final RenderableTriangle planeY;
    private final RenderableTriangle planeZ;

    public AxisCrosshairObject(Vector3d position) {
        super(position);
        final float size = 0.05f;
        planeX = new RenderableTriangle(new Vector3d(1, 0, 0), new Vector3d(), new Vector3d(0, size, 0));
        planeY = new RenderableTriangle(new Vector3d(), new Vector3d(0, 1, 0), new Vector3d(-size, 0, -size));
        planeZ = new RenderableTriangle(new Vector3d(), new Vector3d(0, size, 0), new Vector3d(0, 0, 1));
    }

    public AxisCrosshairObject(){
        this(new Vector3d());
    }


    @Override
    public RenderableTriangle[] getRenderTriangles() {
        return new RenderableTriangle[]{planeX.copy(), planeY.copy(), planeZ.copy()};
    }
}
