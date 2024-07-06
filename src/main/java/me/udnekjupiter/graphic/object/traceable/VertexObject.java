package me.udnekjupiter.graphic.object.traceable;

import me.udnekjupiter.graphic.object.PhysicLinked;
import me.udnekjupiter.physic.net.VertexColor;
import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import org.realityforge.vecmath.Vector3d;

public class VertexObject extends PlaneObject implements PhysicLinked {

    private static final double SCALE_MULTIPLIER = 0.2;
    private final NetVertex netVertex;
    public VertexObject(Vector3d position, NetVertex netVertex) {
        super(
                position,
                -1*SCALE_MULTIPLIER,
                -1*SCALE_MULTIPLIER,
                1*SCALE_MULTIPLIER,
                1*SCALE_MULTIPLIER,
                0,
                VertexColor.getColorFromVertex(netVertex));

        this.netVertex = netVertex;
    }

    @Override
    public void setPosition(Vector3d position) {
        super.setPosition(position);
        netVertex.setPosition(position);
    }

    @Override
    public void synchronizeWithPhysic() {
        setPosition(netVertex.getPosition());
    }

    @Override
    public PhysicObject getPhysicRepresentation() {
        return netVertex;
    }
}
