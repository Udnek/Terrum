package me.udnekjupiter.graphic.triangle;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class ColoredTriangle extends RenderableTriangle {
    protected int color;
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

    public void setColor(int color) {this.color = color;}
    public int getColor() {return color;}

    @Override
    public int getTraceColor(Vector3d hitPosition) {
        return color;
    }
    @Override
    public int getRasterizeColor() {return color;}

    @Override
    public @NotNull ColoredTriangle copy() {
        return new ColoredTriangle(this);
    }
}
