package me.udnekjupiter.graphic.object;

import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.PositionedObject;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.function.Consumer;

public abstract class GraphicObject3d extends PositionedObject implements GraphicObject{
    public GraphicObject3d(@NotNull Vector3d position) {
        super(position);
    }
    public abstract @NotNull RenderableTriangle[] getRenderTriangles();
    public abstract @NotNull RenderableTriangle[] getUnsafeRenderTriangles();
    public void getRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer){
        getUnsafeRenderTriangles(triangle -> consumer.accept(triangle.copy()));
    }
    public abstract void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer);
}
