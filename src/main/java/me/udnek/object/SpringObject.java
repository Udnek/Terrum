package me.udnek.object;

import me.udnek.util.ColoredTriangle;
import me.udnek.util.PositionedObject;
import me.udnek.util.Triangle;
import me.udnek.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class SpringObject extends SceneObject{

    protected PositionedObject tipA;
    protected PositionedObject tipB;
    protected Triangle plane;

    protected final int color = Color.WHITE.getRGB();

    public SpringObject(Vector3d position, PositionedObject tipA, PositionedObject tipSecond) {
        super(position);
        this.tipA = tipA;
        this.tipB = tipSecond;
        this.plane = new ColoredTriangle(new Vector3d(), new Vector3d(), new Vector3d(), color);
    }

    public void update(){
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
    public Triangle[] getRenderTriangles() {
        return new Triangle[]{plane.copy()};
    }
}
