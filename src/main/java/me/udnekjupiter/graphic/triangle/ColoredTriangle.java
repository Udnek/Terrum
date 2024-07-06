package me.udnekjupiter.graphic.triangle;

import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class ColoredTriangle extends TraceableTriangle{
    private final int color;
    public ColoredTriangle(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2, int color) {
        super(vertex0, vertex1, vertex2);
        this.color = color;
    }

    public ColoredTriangle(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2, Color color) {
        this(vertex0, vertex1, vertex2, color.getRGB());
    }

    public ColoredTriangle(ColoredTriangle triangle) {
        super(triangle);
        this.color = triangle.color;
    }

    @Override
    public int getColorWhenTraced(Vector3d hitPosition) {
        return color;
    }

    @Override
    public ColoredTriangle copy() {
        return new ColoredTriangle(this);
    }
}
