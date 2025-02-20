package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.engine.opengl.Texture;
import me.udnekjupiter.graphic.engine.opengl.TextureCorners;
import me.udnekjupiter.graphic.object.Draggable;
import me.udnekjupiter.graphic.object.PhysicLinked;
import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.physic.net.VertexColor;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.vector.Vector3d;

import java.awt.*;

public class VertexObject extends PlaneObject implements PhysicLinked, Draggable {

    private static final double SCALE_MULTIPLIER = 0.2;
    private final NetVertex netVertex;
    public VertexObject(NetVertex netVertex) {
        super(
                netVertex.getPosition(),
                -1*SCALE_MULTIPLIER,
                -1*SCALE_MULTIPLIER,
                1*SCALE_MULTIPLIER,
                1*SCALE_MULTIPLIER,
                0,
                VertexColor.getColorFromVertex(netVertex).getRGB(),
                Texture.VERTEX,
                TextureCorners.FIRST_HALF,
                TextureCorners.SECOND_HALF
        );

        this.netVertex = netVertex;
    }

    @Override
    public void setPosition(@NotNull Vector3d position) {
        super.setPosition(position);
        netVertex.setPosition(position);
    }

    @Override
    public void synchronizeWithPhysic() {
        setPosition(netVertex.getPosition());
    }

    @Override
    public @NotNull NetVertex getPhysicRepresentation() {
        return netVertex;
    }

    @Override
    public void select() {
        ((ColoredTriangle) plane0).color = Color.RED.getRGB();
        ((ColoredTriangle) plane1).color = Color.RED.getRGB();
    }

    @Override
    public void unselect() {
        ((ColoredTriangle) plane0).color = VertexColor.getColorFromVertex(netVertex).getRGB();
        ((ColoredTriangle) plane1).color = VertexColor.getColorFromVertex(netVertex).getRGB();
    }
}
