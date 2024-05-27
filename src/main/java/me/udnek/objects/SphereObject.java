package me.udnek.objects;

import me.udnek.utils.Triangle;
import org.realityforge.vecmath.Vector3d;

public class SphereObject extends SceneObject{

    public final float radius;
    private Triangle[] polygons;
    public SphereObject(Vector3d position, float radius) {
        super(position);
        this.radius = radius;
        generatePolygons();
    }

    public void generatePolygons(){

        final float fi = 0.5f;

        Vector3d v1 = new Vector3d(fi, 1, 0);
        Vector3d v2 = new Vector3d(fi, -1, 0);
        Vector3d v3 = new Vector3d(-fi, -1, 0);
        Vector3d v4 = new Vector3d(-fi, 1, 0);

        Vector3d v5 = new Vector3d(1, 0, fi);
        Vector3d v6 = new Vector3d(-1, 0, fi);
        Vector3d v7 = new Vector3d(-1, 0, -fi);
        Vector3d v8 = new Vector3d(1, 0, -fi);

        Vector3d v9 = new Vector3d(0, fi, 1);
        Vector3d v10 = new Vector3d(0, fi, -1);
        Vector3d v11 = new Vector3d(0, -fi, -1);
        Vector3d v12 = new Vector3d(0, -fi, 1);

        polygons = new Triangle[20];

        // up
        // x +
        polygons[0] = new Triangle(v6, v4, v9);
        polygons[1] = new Triangle(v4, v9, v1);
        polygons[2] = new Triangle(v9, v1, v5);
        // x -
        polygons[3] = new Triangle(v7, v4, v10);
        polygons[4] = new Triangle(v4, v10, v1);
        polygons[5] = new Triangle(v1, v8, v10);

        // down
        // x +
        polygons[6] = new Triangle(v6, v3, v12);
        polygons[7] = new Triangle(v3, v12, v2);
        polygons[8] = new Triangle(v12, v2, v5);
        // x -
        polygons[9] = new Triangle(v3, v11, v12);
        polygons[10] = new Triangle(v3, v11, v2);
        polygons[11] = new Triangle(v11, v12, v2);


        // sides
        // x +
        polygons[12] = new Triangle(v6, v9, v12);
        polygons[13] = new Triangle(v9, v12, v5);
        // x -
        polygons[14] = new Triangle(v7, v10, v11);
        polygons[15] = new Triangle(v11, v10, v8);
        // z +
        polygons[16] = new Triangle(v1, v8, v5);
        polygons[17] = new Triangle(v8, v5, v2);
        // z -
        polygons[18] = new Triangle(v4, v7, v6);
        polygons[19] = new Triangle(v7, v6, v3);

    }

    @Override
    public Triangle[] getRenderTriangles() {
        Triangle[] copy = new Triangle[polygons.length];
        for (int i = 0; i < polygons.length; i++) {
            copy[i] = polygons[i].copy();
        }
        return copy;
    }
}
