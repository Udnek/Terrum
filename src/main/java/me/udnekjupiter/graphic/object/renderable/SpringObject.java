package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.utilityinterface.Positioned;
import me.udnekjupiter.util.utilityinterface.Tickable;
import me.udnekjupiter.util.vector.VectorUtils;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.vector.Vector3d;

import java.awt.*;
import java.util.function.Consumer;

public class SpringObject extends GraphicObject3d implements Tickable {

    protected Positioned tipA;
    protected Positioned tipB;
    protected RenderableTriangle plane;

    protected final int color = Color.WHITE.getRGB();

    public SpringObject(@NotNull me.udnekjupiter.physic.object.SpringObject springObject) {
        super(new Vector3d());
        this.tipA = springObject.getEndpointA();
        this.tipB = springObject.getEndpointB();
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
    public RenderableTriangle @NotNull [] getRenderTriangles() {
        return new RenderableTriangle[]{plane.copy()};
    }

    @Override
    public RenderableTriangle @NotNull [] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane};
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {
        consumer.accept(plane);
    }
}
