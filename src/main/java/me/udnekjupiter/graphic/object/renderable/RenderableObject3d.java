package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.function.Consumer;

public abstract class RenderableObject3d extends GraphicObject3d {
    public RenderableObject3d(@NotNull Vector3d position) {
        super(position);
    }
    public abstract @NotNull RenderableTriangle[] getRenderTriangles();
    public abstract @NotNull RenderableTriangle[] getUnsafeRenderTriangles();
    public void getRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer){
        getUnsafeRenderTriangles(triangle -> consumer.accept(triangle.copy()));
    }
    public abstract void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer);
}
