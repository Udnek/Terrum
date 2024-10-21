package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.function.Consumer;

public class AxisCrosshairObject extends RenderableObject3d {

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
    public RenderableTriangle @NotNull [] getRenderTriangles() {
        return new RenderableTriangle[]{planeX.copy(), planeY.copy(), planeZ.copy()};
    }

    @Override
    public RenderableTriangle @NotNull [] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{planeX, planeY, planeZ};
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {
        consumer.accept(planeX);
        consumer.accept(planeY);
        consumer.accept(planeZ);
    }
}
