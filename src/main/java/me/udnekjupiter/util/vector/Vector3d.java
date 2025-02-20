package me.udnekjupiter.util.vector;

import org.jetbrains.annotations.NotNull;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Vector3d {
    public double x;
    public double y;
    public double z;

    public Vector3d(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d()
    {
         this(0,0,0);
    }
    public Vector3d(Vector3d other)
    {
        this(other.x, other.y, other.z);
    }

    @NotNull
    public Vector3d add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    @NotNull
    public Vector3d add(@NotNull Vector3d other){
        return add(other.x, other.y, other.z);
    }
    @NotNull
    public Vector3d add(double value){
        return add(value, value, value);
    }

    @NotNull
    public Vector3d dup() {
        return new Vector3d(this);
    }

    @NotNull
    public Vector3d mul(double x, double y, double z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }
    @NotNull
    public Vector3d mul(double value){
        return mul(value, value, value);
    }
    @NotNull
    public Vector3d dumbMul(@NotNull Vector3d other){
        return mul(other.x, other.y, other.z);
    }
    public double dot(@NotNull Vector3d other){
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    @NotNull
    public Vector3d sub(@NotNull Vector3d other){
        return sub(other.x, other.y, other.z);
    }
    @NotNull
    public Vector3d sub(double value){
        return sub(value, value, value);
    }
    @NotNull
    public Vector3d sub(double x, double y, double z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @NotNull
    public Vector3d div(double value){
        this.x /= value;
        this.y /= value;
        this.z /= value;
        return this;
    }

    public double length(){
        return sqrt(lengthSquared());
    }
    public double lengthSquared(){
        return x*x + y*y + z*z;
    }

    @NotNull
    public Vector3d normalize(){
        if (length() == 0){
            set(new Vector3d(0, 1, 0));
        }
        return this.div(Math.max(length(), Double.MIN_VALUE));
    }

    @NotNull
    public String asString(){
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @NotNull
    public static Vector3d cross(Vector3d vector1, Vector3d vector2){
        double newX = vector1.y * vector2.z - vector1.z * vector2.y;
        double newY = vector2.x * vector1.z - vector2.z * vector1.x;
        double newZ = vector1.x * vector2.y - vector1.y * vector2.x;
        return new Vector3d(newX, newY, newZ);
    }

    public void set(@NotNull Vector3d other){
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public boolean containsNaN(){
        return (Double.isNaN(x) | Double.isNaN(y) | Double.isNaN(z));
    }

    public double distanceSquared(Vector3d other){
        return pow((this.x-other.x), 2) + pow((this.y-other.y), 2) + pow((this.z-other.z), 2);
    }
    public double distance(Vector3d other){
        return sqrt(distanceSquared(other));
    }

    public @NotNull Vector3d getNormalizedDirection(@NotNull Vector3d other){
        return other.sub(this).normalize();
    }
}
