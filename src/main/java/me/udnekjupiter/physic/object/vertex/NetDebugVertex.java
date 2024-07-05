package me.udnekjupiter.physic.object.vertex;

import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class NetDebugVertex extends NetDynamicVertex {
    private int internalCounter;

    public NetDebugVertex(Vector3d position)
    {
        super(position);
    }

    private boolean isCurrentIterationObserved() {
        if (internalCounter < 0) return true;
        if (internalCounter > 2) return true;
        return false;
    }
}



