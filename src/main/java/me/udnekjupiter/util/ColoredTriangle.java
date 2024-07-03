package me.udnekjupiter.util;

import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class ColoredTriangle extends Triangle{

    private int color;
    public ColoredTriangle(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2, int color) {
        super(vertex0, vertex1, vertex2);
        this.color = color;
    }

    public ColoredTriangle(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2, Color color) {
        super(vertex0, vertex1, vertex2);
        this.color = color.getRGB();
    }

    public ColoredTriangle(ColoredTriangle triangle) {
        super(triangle);
        this.color = triangle.color;
    }

    public int getColor() {
        return color;
    }

    @Override
    public Triangle copy() {
        return new ColoredTriangle(this);
    }
}
