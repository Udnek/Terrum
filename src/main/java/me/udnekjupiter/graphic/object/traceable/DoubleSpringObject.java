package me.udnekjupiter.graphic.object.traceable;

import me.udnekjupiter.util.ColoredTriangle;
import me.udnekjupiter.util.PositionedObject;
import me.udnekjupiter.util.Triangle;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class DoubleSpringObject extends SpringObject{

    private Triangle plane1;

    public DoubleSpringObject(Vector3d position, PositionedObject tipFirst, PositionedObject tipSecond) {
        super(position, tipFirst, tipSecond);
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
    public Triangle[] getRenderTriangles() {
        return new Triangle[]{plane.copy(), plane1.copy()};
    }
}
