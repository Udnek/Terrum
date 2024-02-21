package me.udnek.utils;

import org.realityforge.vecmath.Vector3d;

public class VectorUtils {

    public static double getAreaOfTriangle(Vector3d edge0, Vector3d edge1){
        return getAreaOfParallelogram(edge0, edge1)/2;
    }

    public static double getCrossProductLength(Vector3d edge0, Vector3d edge1){
        return new Vector3d().cross(edge0, edge1).length();
    }

    public static double getAreaOfParallelogram(Vector3d edge0, Vector3d edge1){
        return getCrossProductLength(edge0, edge1);
    }

    public static Vector3d triangleRayIntersection(Vector3d direction, Triangle triangle) {

        final float EPSILON = 0.00001f;

        Vector3d vertex0 = triangle.getVertex0();
        Vector3d vertex1 = triangle.getVertex1();
        Vector3d vertex2 = triangle.getVertex2();

        Vector3d normal = triangle.getNormal();
        double perpendicularity = new Vector3d().cross(normal, direction).length();
        if (-EPSILON <= perpendicularity && perpendicularity <= EPSILON){
            return null;
        }

        double distanceToPlane = normal.dot(vertex0);
        Vector3d onPlanePosition = direction.dup().mul(distanceToPlane / normal.dot(direction));
        double actualArea = triangle.getArea();

        Vector3d pointToVertex0 = vertex0.sub(onPlanePosition);
        Vector3d pointToVertex1 = vertex1.sub(onPlanePosition);
        Vector3d pointToVertex2 = vertex2.sub(onPlanePosition);

        double area0 = getAreaOfTriangle(pointToVertex0, pointToVertex1);
        double area1 = getAreaOfTriangle(pointToVertex1, pointToVertex2);
        double area2 = getAreaOfTriangle(pointToVertex2, pointToVertex0);

        if (area0 + area1 + area2 > actualArea+0.00001) return null;

        return onPlanePosition;
    }

    public static double distanceFromLineToPoint(Vector3d direction, Vector3d point){
        return new Vector3d().cross(direction, point).length()/direction.length();
    }

}
