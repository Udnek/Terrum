package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.physic.object.PlaneObject;
import me.udnekjupiter.util.Vector3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import me.udnekjupiter.util.vector.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class SurfaceObject extends GraphicObject3d {

    public static final double PIECE_SIZE = 4;
    public static final double RADIUS = 100;

    protected RenderableTriangle[] polygons;

    public SurfaceObject(@NotNull PlaneObject planeObject, @Nullable Color color){
        super(new Vector3d());
        if (color == null) color = new Color(0.5f, 0.5f, 0.5f, 0.7f);
        List<RenderableTriangle> polygonsList = new ArrayList<>();
        // TODO MAKE PLANE ROTATABLE
        double y = planeObject.getY(0, 0);
        for (double x = -RADIUS/PIECE_SIZE; x <= RADIUS/PIECE_SIZE; x+=PIECE_SIZE) {
            for (double z = -RADIUS/PIECE_SIZE; z <= RADIUS/PIECE_SIZE; z+=PIECE_SIZE) {
                polygonsList.add(new ColoredTriangle(
                        new Vector3d(x, y, z),
                        new Vector3d(x+PIECE_SIZE, y, z+PIECE_SIZE),
                        new Vector3d(x, y, z+PIECE_SIZE), color.getRGB()));
                polygonsList.add(new ColoredTriangle(
                        new Vector3d(x, y, z),
                        new Vector3d(x+PIECE_SIZE, y, z+PIECE_SIZE),
                        new Vector3d(x+PIECE_SIZE, y, z), color.getRGB()));
            }
        }
        polygons = new RenderableTriangle[polygonsList.size()];
        polygonsList.toArray(polygons);
    }



    @Override
    public @NotNull RenderableTriangle[] getRenderTriangles() {
        return Arrays.stream(polygons).map(RenderableTriangle::copy).toArray(RenderableTriangle[]::new);
    }

    @Override
    public @NotNull RenderableTriangle[] getUnsafeRenderTriangles() {
        return polygons;
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {
        Arrays.stream(polygons).forEach(consumer);
    }
}
