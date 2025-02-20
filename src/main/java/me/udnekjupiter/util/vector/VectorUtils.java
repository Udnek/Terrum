package me.udnekjupiter.util.vector;

import me.udnekjupiter.util.Triangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static me.udnekjupiter.util.vector.Vector3d.cross;

public class VectorUtils {
    public static double vectorLength(double x, double y, double z){
        return Math.sqrt(x*x + y*y + z*z);
    }

    @Deprecated
    public static double distanceSquared(@NotNull Vector3d vector0, @NotNull Vector3d vector1){
        return pow((vector0.x-vector1.x), 2) + pow((vector0.y-vector1.y), 2) + pow((vector0.z-vector1.z), 2);
    }
    @Deprecated
    public static double distance(@NotNull Vector3d vector0, @NotNull Vector3d vector1){
        return sqrt(distanceSquared(vector0, vector1));
    }
    public static @NotNull Vector3d getMin(@NotNull Vector3d vector1, @NotNull Vector3d vector2){
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

    public static double getAreaOfTriangle(@NotNull Vector3d edge0, @NotNull Vector3d edge1){
        double newX = edge0.y * edge1.z - edge0.z * edge1.y;
        double newY = edge1.x * edge0.z - edge1.z * edge0.x;
        double newZ = edge0.x * edge1.y - edge0.y * edge1.x;
        return vectorLength(newX, newY, newZ) / 2.0;
    }
    @Deprecated
    public static @NotNull Vector3d getNormalizedDirection(@NotNull Vector3d positionStart, @NotNull Vector3d positionEnd){
        Vector3d difference = positionEnd.dup().sub(positionStart);
        if (difference.lengthSquared() == 0) return difference;
        return difference.normalize();
    }

    public static @NotNull Vector3d getNormal(@NotNull Vector3d edge0, @NotNull Vector3d edge1){
        return new Vector3d().cross(edge0, edge1);
    }

    public static double getMax(@NotNull Vector3d vector){
        double x = vector.x;
        double y = vector.y;
        double z = vector.z;
        if (x > y && x > z) return x;
        if (y > x && y > z) return y;
        return z;
    }

    public static double getMin(@NotNull Vector3d vector){
        double x = vector.x;
        double y = vector.y;
        double z = vector.z;
        if (x < y && x < z) return x;
        if (y < x && y < z) return y;
        return z;
    }

    public static void cutTo(@NotNull Vector3d vector, double value){
        vector.x = Math.min(vector.x, value);
        vector.y = Math.min(vector.y, value);
        vector.z = Math.min(vector.z, value);
    }

    public void multiply(@NotNull Vector3d v0, @NotNull Vector3d v1){
        v0.x *= v1.x;
        v0.y *= v1.y;
        v0.z *= v1.z;
    }

    ///////////////////////////////////////////////////////////////////////////
    // FOR 3D CALCULATIONS
    ///////////////////////////////////////////////////////////////////////////

    public static @Nullable Vector3d triangleRayIntersection(Vector3d direction, Triangle triangle) {

        final float EPSILON = 0.00001f;

        Vector3d vertex0 = triangle.getVertex0();
        Vector3d vertex1 = triangle.getVertex1();
        Vector3d vertex2 = triangle.getVertex2();

        Vector3d normal = triangle.getNormal();
        // TODO: 5/29/2024 NORMALIZE VECTORS???
        double parallelityWithNormal = 1 - cross(normal, direction).length();
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

    public static double distanceFromLineToPoint(@NotNull Vector3d direction, @NotNull Vector3d point){
        return new Vector3d().cross(direction, point).length()/direction.length();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ROTATION
    ///////////////////////////////////////////////////////////////////////////

    public static void rotateYaw(@NotNull Vector3d vector, double angle){
        double x = vector.x;
        double z = vector.z;
        vector.x = x*Math.cos(angle) + -z*Math.sin(angle);
        vector.z = x*Math.sin(angle) + z*Math.cos(angle);
    }

    public static void rotateRoll(@NotNull Vector3d vector, double angle){
        double x = vector.x;
        double y = vector.y;
        vector.x = x*Math.cos(angle) + -y*Math.sin(angle);
        vector.y = x*Math.sin(angle) + y*Math.cos(angle);
    }

    public static void rotatePitch(@NotNull Vector3d vector, double angle){
        double y = vector.y;
        double z = vector.z;
        vector.y = y*Math.cos(angle) + -z*Math.sin(angle);
        vector.z = y*Math.sin(angle) + z*Math.cos(angle);
    }
    ///////////////////////////////////////////////////////////////////////////
    // GEOMETRY
    ///////////////////////////////////////////////////////////////////////////
    public static double distanceFromPointToPlane(double a, double b, double c, double d, double x, double y, double z) {
        return Math.abs((a * x) + (b * y) + (c * z) + (d)) / Math.sqrt(a * a + b * b + c * c);
    }
}
