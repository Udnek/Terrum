package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public abstract class RenderableObject3d extends GraphicObject3d {
    public RenderableObject3d(@NotNull Vector3d position) {
        super(position);
    }
    public abstract @NotNull RenderableTriangle[] getRenderTriangles();
    public abstract @NotNull RenderableTriangle[] getUnsafeRenderTriangles();
}
