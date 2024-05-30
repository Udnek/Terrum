package me.udnek.objects;

import me.jupiter.object.NetVertex;
import org.realityforge.vecmath.Vector3d;

public class VertexObject extends PlaneObject {

    private static final double SCALE_MULTIPLIER = 0.2;
    private final NetVertex netVertex;
    public VertexObject(Vector3d position, NetVertex netVertex) {

        super(position, -1*SCALE_MULTIPLIER, -1*SCALE_MULTIPLIER, 1*SCALE_MULTIPLIER, 1*SCALE_MULTIPLIER, 0);

        this.netVertex = netVertex;
    }

    public void update(){
        setPosition(netVertex.getPosition());
    }
}
