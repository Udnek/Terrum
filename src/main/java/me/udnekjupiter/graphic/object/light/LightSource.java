package me.udnekjupiter.graphic.object.light;

import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.function.Consumer;

public abstract class LightSource extends GraphicObject3d {
    public LightSource(Vector3d position) {
        super(position);
    }

    @Override
    public RenderableTriangle @NotNull [] getRenderTriangles() {
        return new RenderableTriangle[]{new RenderableTriangle(new Vector3d(0,0.1,0), new Vector3d(0.2, 0.1, 0.2), new Vector3d(-0.2, 0.1, 0.2))};
    }

    @Override
    public RenderableTriangle @NotNull [] getUnsafeRenderTriangles() {
        return new RenderableTriangle[0];
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {}
}
