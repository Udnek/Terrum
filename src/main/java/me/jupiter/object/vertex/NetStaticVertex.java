package me.jupiter.object.vertex;

import me.jupiter.object.vertex.NetVertex;
import org.realityforge.vecmath.Vector3d;

public class NetStaticVertex extends NetVertex {

    public NetStaticVertex(Vector3d position) {
        super(position);
    }

    @Override
    public void RKMCalculatePositionDifferential(){}
    public void updatePosition(){}
}
