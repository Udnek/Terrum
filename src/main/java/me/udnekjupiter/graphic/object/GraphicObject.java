package me.udnekjupiter.graphic.object;

import me.udnekjupiter.util.PositionedObject;
import org.realityforge.vecmath.Vector3d;

public abstract class GraphicObject extends PositionedObject {
    public GraphicObject(Vector3d position) {
        super(position);
    }
}
