package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.PositionedObject;
import me.udnekjupiter.util.VectorUtils;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.function.Consumer;

public class DoubleSpringObject extends SpringObject {

    private RenderableTriangle plane1;

    public DoubleSpringObject(PositionedObject tipFirst, PositionedObject tipSecond) {
        super(tipFirst, tipSecond);
        plane1 = new ColoredTriangle(new Vector3d(), new Vector3d(), new Vector3d(), color);
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
        plane1.setVertices(posB1, posB2, posA2);
    }

    @Override
    public RenderableTriangle @NotNull [] getRenderTriangles() {
        return new RenderableTriangle[]{plane.copy(), plane1.copy()};
    }

    @Override
    public RenderableTriangle @NotNull [] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane, plane1};
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {
        super.getUnsafeRenderTriangles(consumer);
        consumer.accept(plane1);
    }
}
