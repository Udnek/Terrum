package me.udnek.objects;

import me.jupiter.object.NetVertex;
import me.udnek.objects.shape.PyramidObject;
import org.realityforge.vecmath.Vector3d;

public class VertexObject extends PyramidObject {

    private static final double HEIGHT = Math.sqrt(3)/2.0;
    private static final double SCALE_MULTIPLIER = 0.2;
    private final NetVertex netVertex;
    public VertexObject(Vector3d position, NetVertex netVertex) {

        super(position,
                new Vector3d(0, 2, 0).mul(SCALE_MULTIPLIER),
                new Vector3d(0, 0, HEIGHT/2).mul(SCALE_MULTIPLIER),
                new Vector3d(-0.5, 0, -HEIGHT/2).mul(SCALE_MULTIPLIER),
                new Vector3d(0.5, 0, -HEIGHT/2).mul(SCALE_MULTIPLIER)
        );

        this.netVertex = netVertex;
    }

    public void update(){
        setPosition(netVertex.getPosition());
    }
}
