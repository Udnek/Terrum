package me.udnekjupiter.graphic.object.traceable;

import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.TraceableTriangle;
import me.udnekjupiter.util.PositionedObject;
import me.udnekjupiter.util.Tickable;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class SpringObject extends TraceableObject implements Tickable {

    protected PositionedObject tipA;
    protected PositionedObject tipB;
    protected TraceableTriangle plane;

    protected final int color = Color.WHITE.getRGB();

    public SpringObject(Vector3d position, PositionedObject tipA, PositionedObject tipSecond) {
        super(position);
        this.tipA = tipA;
        this.tipB = tipSecond;
        this.plane = new ColoredTriangle(new Vector3d(), new Vector3d(), new Vector3d(), color);
    }

    @Override
    public void tick() {
        Vector3d pos1 = tipA.getPosition().sub(0, 0.01, 0);
        Vector3d pos2 = tipB.getPosition().sub(0, 0.01, 0);
        Vector3d direction = pos2.dup().sub(pos1).normalize().mul(0.1f);
        VectorUtils.rotateYaw(direction, Math.PI/2.0);
        plane.setVertices(
                pos1,
                pos1.dup().add(direction),
                pos2
        );
    }

    @Override
    public TraceableTriangle[] getRenderTriangles() {
        return new TraceableTriangle[]{plane.copy()};
    }
}
