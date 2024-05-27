package me.udnek.objects;

import me.udnek.utils.Triangle;
import org.realityforge.vecmath.Vector3d;

public class TetrahedronObject extends SceneObject{
    private Triangle plane0;
    private Triangle plane1;
    private Triangle plane2;
    private Triangle planeBottom;

    public TetrahedronObject(Vector3d position,
                             Vector3d vertex,
                             Vector3d vertexBottom0,
                             Vector3d vertexBottom1,
                             Vector3d vertexBottom2) {
        super(position);
        planeBottom = new Triangle(vertexBottom0, vertexBottom1, vertexBottom2);
        plane0 = new Triangle(vertexBottom0, vertexBottom1, vertex);
        plane1 = new Triangle(vertexBottom1, vertexBottom2, vertex);
        plane2 = new Triangle(vertexBottom2, vertexBottom0, vertex);

    }


    @Override
    public Triangle[] getRenderTriangles() {
        return new Triangle[]{plane0.copy(), plane1.copy(), plane2.copy(), planeBottom.copy()};
    }
}
