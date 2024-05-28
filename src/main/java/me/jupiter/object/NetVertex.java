package me.jupiter.object;

import org.realityforge.vecmath.Vector3d;

public abstract class NetVertex {
    protected Vector3d position;
    protected final float springRelaxedLength;
    protected final float springStiffness;

    protected NetVertex neighbourUp;
    protected NetVertex neighbourRight;
    protected NetVertex neighbourDown;
    protected NetVertex neighbourLeft;

    public NetVertex(Vector3d position, float springRelaxedLength, float springStiffness, NetVertex n1, NetVertex n2, NetVertex n3, NetVertex n4) {
        this.position = position;
        this.springRelaxedLength = springRelaxedLength;
        this.springStiffness = springStiffness;
        this.setupNeighbours(n1, n2, n3, n4);
    }
    public NetVertex(Vector3d position, float springRelaxedLength, float springStiffness)
    {
        this(position, springRelaxedLength, springStiffness, null, null, null, null);
    }
    public NetVertex(Vector3d position){
        this(position, 1, 1, null, null, null, null);
    }

    public Vector3d getPosition() {return position.dup();}
    public void setPosition(Vector3d position) {this.position = position;}

    public void setupNeighbours(NetVertex n1, NetVertex n2, NetVertex n3, NetVertex n4){
        this.neighbourUp = n1;
        this.neighbourRight = n2;
        this.neighbourDown = n3;
        this.neighbourLeft = n4;
    }
}
