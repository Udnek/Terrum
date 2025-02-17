package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.VectorUtils;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.Vector3d;

import java.util.function.Consumer;

public class DoubleSpringObject extends SpringObject {

    private RenderableTriangle secondPlane;

    public DoubleSpringObject(me.udnekjupiter.physic.object.SpringObject springObject) {
        super(springObject);
        secondPlane = new ColoredTriangle(new Vector3d(), new Vector3d(), new Vector3d(), color);
    }

    @Override
    public void tick() {
        Vector3d posA1 = tipA.getPosition().sub(0, 0.01, 0);
        Vector3d posB1 = tipB.getPosition().sub(0, 0.01, 0);

        Vector3d direction = posB1.dup().sub(posA1).normalize().mul(0.05f);
        VectorUtils.rotateYaw(direction, Math.PI/2.0);
        direction.y = 0;


        posA1.sub(direction);
        posB1.sub(direction);

        direction.mul(2);

        Vector3d posA2 = posA1.dup().add(direction);
        Vector3d posB2 = posB1.dup().add(direction);

        plane.setVertices(posA1, posA2, posB1);
        secondPlane.setVertices(posB1, posB2, posA2);
    }

    @Override
    public RenderableTriangle @NotNull [] getRenderTriangles() {
        return new RenderableTriangle[]{plane.copy(), secondPlane.copy()};
    }

    @Override
    public RenderableTriangle @NotNull [] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane, secondPlane};
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {
        super.getUnsafeRenderTriangles(consumer);
        consumer.accept(secondPlane);
    }
}
