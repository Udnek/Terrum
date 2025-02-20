package me.udnekjupiter.graphic.triangle;

import me.udnekjupiter.util.Triangle;
import me.udnekjupiter.util.Utils;
import me.udnekjupiter.util.vector.VectorUtils;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.vector.Vector3d;

import java.awt.*;

public class RenderableTriangle extends Triangle {
    public RenderableTriangle(@NotNull Vector3d vertex0, @NotNull Vector3d vertex1, @NotNull Vector3d vertex2) {
        super(vertex0, vertex1, vertex2);
    }

    public RenderableTriangle(@NotNull RenderableTriangle triangle) {
        super(triangle);
    }

    public static @NotNull RenderableTriangle empty(){
        return new RenderableTriangle(new Vector3d(), new Vector3d(), new Vector3d());
    }

    public int getTraceColor(@NotNull Vector3d hitPosition){
        double d0 = VectorUtils.distance(hitPosition, vertex0);
        double d1 = VectorUtils.distance(hitPosition, vertex1);
        double d2 = VectorUtils.distance(hitPosition, vertex2);

        Vector3d distances = new Vector3d(d0, d1, d2);
        double minDistance = VectorUtils.getMin(distances);
        Vector3d color;
        if (minDistance <= 0){
            color = new Vector3d(1f, 1f, 1f);
        } else {
            color = new Vector3d(1/d0, 1/d1 ,1/d2);
            color.div(VectorUtils.getMax(color));
            VectorUtils.cutTo(color, 1f);
        }

        return Utils.vectorToColor(color).getRGB();
    }

    // TODO: 9/3/2024 WTF
    public int getRasterizeColor(){
        return Color.GRAY.getRGB();
    }

    
    public RenderableTriangle copyWithVertices(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2){
        RenderableTriangle newTriangle = copy();
        newTriangle.setVertices(vertex0, vertex1, vertex2);
        return newTriangle;
    }

    @Override
    public @NotNull RenderableTriangle copy() {
        return new RenderableTriangle(this);
    }
}
