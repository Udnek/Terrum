package me.udnek.object.shape;

import me.udnek.object.SceneObject;
import me.udnek.util.Triangle;
import org.realityforge.vecmath.Vector3d;

public class IcosahedronObject extends SceneObject {

    public static final float FI = (float) ((1.0f + Math.sqrt(5.0f))/2.0f);
    private Triangle[] polygons;
    private final double size;
    public IcosahedronObject(Vector3d position, double size) {
        super(position);
        this.size = size;
        generatePolygons();
    }
    public IcosahedronObject(Vector3d position) {
        super(position);
        generatePolygons();
        size = 1;
    }

    protected void generatePolygons(){

        Vector3d v1 = new Vector3d(FI, 1, 0).mul(size);
        Vector3d v2 = new Vector3d(FI, -1, 0).mul(size);
        Vector3d v3 = new Vector3d(-FI, -1, 0).mul(size);
        Vector3d v4 = new Vector3d(-FI, 1, 0).mul(size);

        Vector3d v5 = new Vector3d(1, 0, FI).mul(size);
        Vector3d v6 = new Vector3d(-1, 0, FI).mul(size);
        Vector3d v7 = new Vector3d(-1, 0, -FI).mul(size);
        Vector3d v8 = new Vector3d(1, 0, -FI).mul(size);

        Vector3d v9 = new Vector3d(0, FI, 1).mul(size);
        Vector3d v10 = new Vector3d(0, FI, -1).mul(size);
        Vector3d v11 = new Vector3d(0, -FI, -1).mul(size);
        Vector3d v12 = new Vector3d(0, -FI, 1).mul(size);

        polygons = new Triangle[20];

        // up
        // z -
        polygons[0] = new Triangle(v4, v10, v7);
        polygons[1] = new Triangle(v10, v7, v8);
        polygons[2] = new Triangle(v10, v8, v1);
        // z +
        polygons[3] = new Triangle(v4, v6, v9);
        polygons[4] = new Triangle(v6, v9, v5);
        polygons[5] = new Triangle(v9, v5, v1);

        // down
        // z -
        polygons[6] = new Triangle(v3, v7, v11);
        polygons[7] = new Triangle(v7, v11, v8);
        polygons[8] = new Triangle(v8, v11, v2);
        // z +
        polygons[9] = new Triangle(v3, v6, v12);
        polygons[10] = new Triangle(v6, v12, v5);
        polygons[11] = new Triangle(v12, v5, v2);


        // sides
        // x +
        polygons[12] = new Triangle(v5, v1, v2);
        polygons[13] = new Triangle(v1, v2, v8);
        // x -
        polygons[14] = new Triangle(v4, v3, v6);
        polygons[15] = new Triangle(v4, v3, v7);
        // y +
        polygons[16] = new Triangle(v4, v9, v10);
        polygons[17] = new Triangle(v9, v10, v1);
        // y -
        polygons[18] = new Triangle(v3, v12, v11);
        polygons[19] = new Triangle(v12, v11, v2);

    }

    public double getCircumradius(){
        return calculateCircumradius(size);
    }

    public static double calculateCircumradius(double size){
        return Math.sqrt(FI*FI + 1)/2.0 * size;
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
