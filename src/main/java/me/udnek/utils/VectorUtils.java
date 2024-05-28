package me.udnek.utils;

import org.realityforge.vecmath.Vector3d;

public class VectorUtils {
    public static double distanceSquared(Vector3d vector0, Vector3d vector1){
        return Math.pow((vector0.x-vector1.x), 2) + Math.pow((vector0.y-vector1.y), 2) + Math.pow((vector0.z-vector1.z), 2);
    }
    public static double distance(Vector3d vector0, Vector3d vector1){
        return Math.sqrt(distanceSquared(vector0, vector1));
    }

    public static double getAreaOfTriangle(Vector3d edge0, Vector3d edge1){
        return getAreaOfParallelogram(edge0, edge1) / 2.0;
    }

    public static double getCrossProductLength(Vector3d vector0, Vector3d vector1){
        return new Vector3d().cross(vector0, vector1).length();
    }

    public static double getAreaOfParallelogram(Vector3d edge0, Vector3d edge1){
        return getCrossProductLength(edge0, edge1);
    }

    public static double getMax(Vector3d vector){
        double x = vector.x;
        double y = vector.y;
        double z = vector.z;
        if (x > y && x > z) return x;
        if (y > x && y > z) return y;
        return z;
    }

    public static double getMin(Vector3d vector){
        double x = vector.x;
        double y = vector.y;
        double z = vector.z;
        if (x < y && x < z) return x;
        if (y < x && y < z) return y;
        return z;
    }

    public static void cutTo(Vector3d vector, double value){
        vector.x = Math.min(vector.x, value);
        vector.y = Math.min(vector.y, value);
        vector.z = Math.min(vector.z, value);
    }
    ///////////////////////////////////////////////////////////////////////////
    // FOR 3D CALCS
    ///////////////////////////////////////////////////////////////////////////

    public static Vector3d triangleRayIntersection(Vector3d direction, Triangle triangle) {

        final float EPSILON = 0.00001f;

        Vector3d vertex0 = triangle.getVertex0();
        Vector3d vertex1 = triangle.getVertex1();
        Vector3d vertex2 = triangle.getVertex2();

        Vector3d normal = triangle.getNormal();
        double parallelityWithNormal = 1 - new Vector3d().cross(normal, direction).length();
        if (-EPSILON <= parallelityWithNormal && parallelityWithNormal <= EPSILON){
            return null;
        }

        double distanceToPlane = normal.dot(vertex0);
        double directionCoefficient = distanceToPlane / normal.dot(direction);
        if (directionCoefficient < 0) return null;
        Vector3d onPlanePosition = direction.dup().mul(directionCoefficient);
        double actualArea = triangle.getArea();

        Vector3d pointToVertex0 = vertex0.sub(onPlanePosition);
        Vector3d pointToVertex1 = vertex1.sub(onPlanePosition);
        Vector3d pointToVertex2 = vertex2.sub(onPlanePosition);

        double area0 = getAreaOfTriangle(pointToVertex0, pointToVertex1);
        double area1 = getAreaOfTriangle(pointToVertex1, pointToVertex2);
        double area2 = getAreaOfTriangle(pointToVertex2, pointToVertex0);

        if (area0 + area1 + area2 > actualArea + EPSILON) return null;

        return onPlanePosition;
    }

    public static double distanceFromLineToPoint(Vector3d direction, Vector3d point){
        return new Vector3d().cross(direction, point).length()/direction.length();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ROTATION
    ///////////////////////////////////////////////////////////////////////////

    public static void rotateYaw(Vector3d vector, float angle){
        double x = vector.x;
        double z = vector.z;
        vector.x = x*Math.cos(angle) + -z*Math.sin(angle);
        vector.z = x*Math.sin(angle) + z*Math.cos(angle);
    }

    public static void rotateRoll(Vector3d vector, float angle){
        double x = vector.x;
        double y = vector.y;
        vector.x = x*Math.cos(angle) + -y*Math.sin(angle);
        vector.y = x*Math.sin(angle) + y*Math.cos(angle);
    }

    public static void rotatePitch(Vector3d vector, float angle){
        double y = vector.y;
        double z = vector.z;
        vector.y = y*Math.cos(angle) + -z*Math.sin(angle);
        vector.z = y*Math.sin(angle) + z*Math.cos(angle);
    }

}
