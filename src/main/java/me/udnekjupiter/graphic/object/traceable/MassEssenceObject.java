package me.udnekjupiter.graphic.object.traceable;

import me.udnekjupiter.graphic.object.traceable.shape.IcosphereObject;
import org.realityforge.vecmath.Vector3d;

public class MassEssenceObject extends IcosphereObject {

    public MassEssenceObject(Vector3d position) {
        super(position, 0.5, 1);
    }
}
