package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class PlaneObject extends RenderableObject3d {

    protected RenderableTriangle plane0;
    protected RenderableTriangle plane1;

    public PlaneObject(Vector3d position, double x0, double z0, double x1, double z1, double y) {
        super(position);
        plane0 = new RenderableTriangle(new Vector3d(x0, y, z0), new Vector3d(x1, y, z1), new Vector3d(x0, y, z1));
        plane1 = new RenderableTriangle(new Vector3d(x0, y, z0), new Vector3d(x1, y, z1), new Vector3d(x1, y, z0));
    }
    public PlaneObject(Vector3d position, double x0, double z0, double x1, double z1, double y, Color color) {
        super(position);
        plane0 = new ColoredTriangle(new Vector3d(x0, y, z0), new Vector3d(x1, y, z1), new Vector3d(x0, y, z1), color);
        plane1 = new ColoredTriangle(new Vector3d(x0, y, z0), new Vector3d(x1, y, z1), new Vector3d(x1, y, z0), color);
    }


    @Override
    public RenderableTriangle[] getRenderTriangles() {
        return new RenderableTriangle[]{plane0.copy(), plane1.copy()};
    }

    @Override
    public RenderableTriangle[] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane0, plane1};
    }
}
