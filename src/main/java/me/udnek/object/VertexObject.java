package me.udnek.object;

import me.jupiter.net.VertexColor;
import me.jupiter.object.NetVertex;
import org.realityforge.vecmath.Vector3d;

public class VertexObject extends PlaneObject {

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

    public void update(){
        setPosition(netVertex.getPosition());
    }
}
