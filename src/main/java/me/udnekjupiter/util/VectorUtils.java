package me.udnekjupiter.util;

import org.realityforge.vecmath.Vector3d;

public class VectorUtils {
    public static double vectorLength(double x, double y, double z){
        return Math.sqrt(x*x + y*y + z*z);
    }
    public static double distanceSquared(Vector3d vector0, Vector3d vector1){
        return Math.pow((vector0.x-vector1.x), 2) + Math.pow((vector0.y-vector1.y), 2) + Math.pow((vector0.z-vector1.z), 2);
    }
    public static double distance(Vector3d vector0, Vector3d vector1){
        return Math.sqrt(distanceSquared(vector0, vector1));
    }
    public static Vector3d getMin(Vector3d vector1, Vector3d vector2){
        if (vector1.length() > vector2.length()) return vector2;
        return vector1;
    }

/*    public static double getCrossProductLength(Vector3d vector0, Vector3d vector1){
        double newX = vector0.y * vector1.z - vector0.z * vector1.y;
        double newY = vector1.x * vector0.z - vector1.z * vector0.x;
        double newZ = vector0.x * vector1.y - vector0.y * vector1.x;
        return vectorLength(newX, newY, newZ);
    }
    public static double getAreaOfParallelogram(Vector3d edge0, Vector3d edge1){
        return getCrossProductLength(edge0, edge1);
    }*/

    public static double getAreaOfTriangle(Vector3d edge0, Vector3d edge1){
        double newX = edge0.y * edge1.z - edge0.z * edge1.y;
        double newY = edge1.x * edge0.z - edge1.z * edge0.x;
        double newZ = edge0.x * edge1.y - edge0.y * edge1.x;
        return vectorLength(newX, newY, newZ) / 2.0;
    }
    public static Vector3d getNormalizedDirection(Vector3d positionStart, Vector3d positionEnd){
        return positionEnd.dup().sub(positionStart).normalize();
    }

    public static Vector3d getNormal(Vector3d edge0, Vector3d edge1){
        return new Vector3d().cross(edge0, edge1);
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

    public void multiply(Vector3d v0, Vector3d v1){
        v0.x *= v1.x;
        v0.y *= v1.y;
        v0.z *= v1.z;
    }

    ///////////////////////////////////////////////////////////////////////////
    // FOR 3D CALCULATIONS
    ///////////////////////////////////////////////////////////////////////////

    public static Vector3d triangleRayIntersection(Vector3d direction, Triangle triangle) {

        final float EPSILON = 0.00001f;

        Vector3d vertex0 = triangle.getVertex0();
        Vector3d vertex1 = triangle.getVertex1();
        Vector3d vertex2 = triangle.getVertex2();

        Vector3d normal = triangle.getNormal();
        // TODO: 5/29/2024 NORMALIZE VECTORS???
        double parallelityWithNormal = 1 - new Vector3d().cross(normal, direction).length();
        if (-EPSILON <= parallelityWithNormal && parallelityWithNormal <= EPSILON){
            return null;
        }

        double distanceToPlane = normal.dot(vertex0);
        double directionCoefficient = distanceToPlane / normal.dot(direction);
        // facing back
        if (directionCoefficient < 0) return null;

        Vector3d onPlanePosition = direction.dup().mul(directionCoefficient);
        double actualArea = triangle.getArea();

        // points to vertices
        vertex0.sub(onPlanePosition);
        vertex1.sub(onPlanePosition);
        vertex2.sub(onPlanePosition);

        double area0 = getAreaOfTriangle(vertex0, vertex1);

        // premature
        if (area0 > actualArea) return null;

        double area1 = getAreaOfTriangle(vertex1, vertex2);
        double area2 = getAreaOfTriangle(vertex2, vertex0);

        if (area0 + area1 + area2 > actualArea + EPSILON) return null;

        return onPlanePosition;
    }

    public static double distanceFromLineToPoint(Vector3d direction, Vector3d point){
        return new Vector3d().cross(direction, point).length()/direction.length();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ROTATION
    ///////////////////////////////////////////////////////////////////////////

    public static void rotateYaw(Vector3d vector, double angle){
        double x = vector.x;
        double z = vector.z;
        vector.x = x*Math.cos(angle) + -z*Math.sin(angle);
        vector.z = x*Math.sin(angle) + z*Math.cos(angle);
    }

    public static void rotateRoll(Vector3d vector, double angle){
        double x = vector.x;
        double y = vector.y;
        vector.x = x*Math.cos(angle) + -y*Math.sin(angle);
        vector.y = x*Math.sin(angle) + y*Math.cos(angle);
    }

    public static void rotatePitch(Vector3d vector, double angle){
        double y = vector.y;
        double z = vector.z;
        vector.y = y*Math.cos(angle) + -z*Math.sin(angle);
        vector.z = y*Math.sin(angle) + z*Math.cos(angle);
    }

}
