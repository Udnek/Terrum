package me.udnekjupiter.graphic.object.traceable.shape;

import me.udnekjupiter.graphic.object.traceable.TraceableObject;
import me.udnekjupiter.util.Triangle;
import org.realityforge.vecmath.Vector3d;

public class IcosphereObject extends TraceableObject {

    private final double radius;
    private Triangle[] polygons;
    private final int subdivideIterations;
    public IcosphereObject(Vector3d position, double radius, int subdivideIterations) {
        super(position);
        this.radius = radius;
        this.subdivideIterations = subdivideIterations;
        generatePolygons();
    }

    protected void generatePolygons(){
        double icoSize =  radius/2/IcosahedronObject.calculateCircumradius(1);
        IcosahedronObject icosahedronObject = new IcosahedronObject(new Vector3d(), icoSize);

        polygons = icosahedronObject.getRenderTriangles();
        if (subdivideIterations < 1) return;
        for (int i = 0; i < subdivideIterations; i++) {
            polygons = generatePolygons(polygons);
        }
    }

    protected Triangle[] generatePolygons(Triangle[] polygons){
        Triangle[] newPolygons = new Triangle[polygons.length * 4];
        for (int i = 0; i < polygons.length; i++) {
            Triangle[] subdividedFaces = subdivideFace(polygons[i]);
            for (int j = 0; j < subdividedFaces.length; j++) {
                newPolygons[i*4 + j] = subdividedFaces[j];
            }
        }
        return newPolygons;
    }



    protected Triangle[] subdivideFace(Triangle face){
        Vector3d newVertex01 = face.getVertex0().add(face.getVertex1()).div(2);
        Vector3d newVertex12 = face.getVertex1().add(face.getVertex2()).div(2);
        Vector3d newVertex02 = face.getVertex2().add(face.getVertex0()).div(2);

        double distance0 = newVertex01.length();
        double distance1 = newVertex12.length();
        double distance2 = newVertex02.length();

        newVertex01.div(distance0).mul(radius);
        newVertex12.div(distance1).mul(radius);
        newVertex02.div(distance2).mul(radius);

        Triangle face0 = new Triangle(face.getVertex0(), newVertex01, newVertex02);
        Triangle face1 = new Triangle(face.getVertex1(), newVertex01, newVertex12);
        Triangle face2 = new Triangle(face.getVertex2(), newVertex12, newVertex02);
        Triangle faceCenter = new Triangle(newVertex01, newVertex12, newVertex02);

        return new Triangle[]{face0, face1, face2, faceCenter};
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
