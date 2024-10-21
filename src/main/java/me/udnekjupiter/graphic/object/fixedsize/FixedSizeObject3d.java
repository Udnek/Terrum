package me.udnekjupiter.graphic.object.fixedsize;

import me.udnekjupiter.graphic.frame.GraphicFrame;
import me.udnekjupiter.graphic.object.GraphicObject3d;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.List;

public abstract class FixedSizeObject3d extends GraphicObject3d {
    public FixedSizeObject3d(Vector3d position) {
        super(position);
    }
    public abstract void foundOnFrameAt(List<Point> points, GraphicFrame frame);
    public abstract Vector3d[] getPoints();
}
