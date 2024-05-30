package me.udnek.objects;

import me.udnek.utils.PositionedObject;
import me.udnek.utils.Triangle;
import org.realityforge.vecmath.Vector3d;

public class SpringObject extends SceneObject{

    private PositionedObject tipFirst;
    private PositionedObject tipSecond;
    private Triangle plane0;
    private Triangle plane1;

    public SpringObject(Vector3d position, PositionedObject tipFirst, PositionedObject tipSecond) {
        super(position);
        this.tipFirst = tipFirst;
        this.tipSecond = tipSecond;
        this.plane0 = new Triangle(new Vector3d(), new Vector3d(), new Vector3d());
        //this.plane1 = new Triangle(new Vector3d(), new Vector3d(), new Vector3d());

        update();
    }

    public void update(){
        plane0.setVertices(tipFirst.getPosition(), tipFirst.getPosition().add(0, 0.1, 0), tipSecond.getPosition().add(0, 0.1, 0));
        //plane1.setVertices(tipFirst.getPosition(), tipSecond.getPosition(), tipSecond.getPosition().add(0, 0.1, 0));
    }

    @Override
    public Triangle[] getRenderTriangles() {
        return new Triangle[]{plane0.copy()};
    }
}
