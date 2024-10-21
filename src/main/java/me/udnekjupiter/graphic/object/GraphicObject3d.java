package me.udnekjupiter.graphic.object;

import me.udnekjupiter.util.PositionedObject;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public abstract class GraphicObject3d extends PositionedObject implements GraphicObject{
    public GraphicObject3d(@NotNull Vector3d position) {
        super(position);
    }
}
