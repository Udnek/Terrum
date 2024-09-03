package me.udnekjupiter.graphic.triangle;

import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class MassEssenceTriangle extends RenderableTriangle {

    protected int color = DEFAULT_COLOR;
    public static final int DEFAULT_COLOR = Color.LIGHT_GRAY.getRGB();
    public static final int HIGHLIGHTED_COLOR = Color.RED.getRGB();
    public MassEssenceTriangle(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2) {
        super(vertex0, vertex1, vertex2);
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

    public void setColor(int color) {
        this.color = color;
    }
    public int getColor() {
        return color;
    }

    @Override
    public RenderableTriangle copy() {
        return new MassEssenceTriangle(this);
    }
}
