package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.object.Draggable;
import me.udnekjupiter.graphic.object.PhysicLinked;
import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.physic.net.VertexColor;
import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class VertexObject extends PlaneObject implements PhysicLinked, Draggable {

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

    @Override
    public void select() {
        ((ColoredTriangle) plane0).setColor(Color.RED.getRGB());
        ((ColoredTriangle) plane1).setColor(Color.RED.getRGB());
    }

    @Override
    public void unselect() {
        ((ColoredTriangle) plane0).setColor(VertexColor.getColorFromVertex(netVertex).getRGB());
        ((ColoredTriangle) plane1).setColor(VertexColor.getColorFromVertex(netVertex).getRGB());
    }
}
