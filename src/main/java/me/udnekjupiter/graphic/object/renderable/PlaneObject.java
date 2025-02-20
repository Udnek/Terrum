package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.engine.opengl.Texture;
import me.udnekjupiter.graphic.engine.opengl.TextureCorners;
import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.vector.Vector3d;

import java.awt.*;
import java.util.function.Consumer;

public class PlaneObject extends GraphicObject3d {

    protected RenderableTriangle plane0;
    protected RenderableTriangle plane1;

    public PlaneObject(@NotNull Vector3d position, double x0, double z0, double x1, double z1, double y, int color, @NotNull Texture texture, @NotNull TextureCorners corners0, @NotNull TextureCorners corners1) {
        super(position);
        plane0 = new ColoredTriangle(new Vector3d(x0, y, z0), new Vector3d(x0, y, z1), new Vector3d(x1, y, z1), color, texture, corners0);
        plane1 = new ColoredTriangle(new Vector3d(x1, y, z1), new Vector3d(x1, y, z0), new Vector3d(x0, y, z0), color, texture, corners1);
    }

    public PlaneObject(@NotNull Vector3d position, double x0, double z0, double x1, double z1, double y) {
        this(position, x0, z0, x1, z1, y, Color.GRAY.getRGB(), Texture.BLANK, TextureCorners.FIRST_HALF, TextureCorners.SECOND_HALF);
    }


    @Override
    public @NotNull RenderableTriangle @NotNull [] getRenderTriangles() {return new RenderableTriangle[]{plane0.copy(), plane1.copy()};}
    @Override
    public @NotNull RenderableTriangle @NotNull [] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane0, plane1};
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {
        consumer.accept(plane0);
        consumer.accept(plane1);
    }
}
