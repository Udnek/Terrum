package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.PositionedObject;
import me.udnekjupiter.util.Tickable;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class SpringObject extends RenderableObject3d implements Tickable {

    protected PositionedObject tipA;
    protected PositionedObject tipB;
    protected RenderableTriangle plane;

    protected final int color = Color.WHITE.getRGB();

    public SpringObject(PositionedObject tipA, PositionedObject tipSecond) {
        super(new Vector3d());
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
    public RenderableTriangle[] getRenderTriangles() {
        return new RenderableTriangle[]{plane.copy()};
    }

    @Override
    public RenderableTriangle[] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane};
    }
}
