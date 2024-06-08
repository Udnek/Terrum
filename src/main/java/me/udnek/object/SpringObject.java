package me.udnek.object;

import me.udnek.util.PositionedObject;
import me.udnek.util.Triangle;
import me.udnek.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class SpringObject extends SceneObject{

    private PositionedObject tipFirst;
    private PositionedObject tipSecond;
    private Triangle plane;

    public SpringObject(Vector3d position, PositionedObject tipFirst, PositionedObject tipSecond) {
        super(position);
        this.tipFirst = tipFirst;
        this.tipSecond = tipSecond;
        this.plane = new Triangle(new Vector3d(), new Vector3d(), new Vector3d());
        update();
    }

    public void update(){
        Vector3d pos1 = tipFirst.getPosition().sub(0, 0.01, 0);
        Vector3d pos2 = tipSecond.getPosition().sub(0, 0.01, 0);
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
