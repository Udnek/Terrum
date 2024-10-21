package me.udnekjupiter.graphic.triangle;

import me.udnekjupiter.util.VectorUtils;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class MassEssenceTriangle extends ColoredTriangle {

    public static final int DEFAULT_COLOR = new Color(128, 128, 128, 80).getRGB();
    public static final int HIGHLIGHTED_COLOR = new Color(200, 0, 0, 80).getRGB();

    protected int color;

    public MassEssenceTriangle(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2) {
        super(vertex0, vertex1, vertex2, DEFAULT_COLOR);
    }
    public MassEssenceTriangle(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2, int color) {
        super(vertex0, vertex1, vertex2, color);
    }
    public MassEssenceTriangle(MassEssenceTriangle triangle) {
        super(triangle);
        this.color = triangle.getColor();
    }

    @Override
    public int getTraceColor(Vector3d hitPosition) {
        double d0 = VectorUtils.distance(hitPosition, vertex0);
        double d1 = VectorUtils.distance(hitPosition, vertex1);
        double d2 = VectorUtils.distance(hitPosition, vertex2);

        Vector3d distances = new Vector3d(d0, d1, d2);
        double minDistance = VectorUtils.getMin(distances);
        int color;
        if (minDistance <= 0.08){
            color = Color.WHITE.getRGB();
        } else {
            color = this.color;
        }

        return color;
    }


    @Override
    public @NotNull MassEssenceTriangle copy() {
        return new MassEssenceTriangle(this);
    }
}
