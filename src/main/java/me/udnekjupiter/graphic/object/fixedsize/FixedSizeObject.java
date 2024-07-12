package me.udnekjupiter.graphic.object.fixedsize;

import me.udnekjupiter.graphic.frame.GraphicFrame;
import me.udnekjupiter.graphic.object.GraphicObject;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.List;

public abstract class FixedSizeObject extends GraphicObject {
    public FixedSizeObject(Vector3d position) {
        super(position);
    }
    public abstract void foundOnFrameAt(List<Point> points, GraphicFrame frame);
    public abstract Vector3d[] getPoints();
}
