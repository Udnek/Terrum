package me.udnek.utils;


public class Vector3{
    public double x;
    public double y;
    public double z;


    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(){this(0, 0, 0);}

    ///////////////////////////////////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////////////////////////////////


    public double dot(Vector3 other) {return this.x * other.x + this.y * other.y + this.z * other.z;}

    public Vector3 cross(Vector3 other){
        double newX = this.y * other.z - this.z * other.y;
        double newY = other.x * this.z - other.z * this.x;
        double newZ = this.x * other.y - this.y * other.x;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        return this;
    }

/*    public static Vector3 cross(Vector3 first, Vector3 second){
        return first.clone()
    }*/



    public Vector3 normalize() { return this.div(this.length()); }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Vector3 mul(double n){
        this.x *= n;
        this.y *= n;
        this.z *= n;

        return this;
    }
    public Vector3 div(double n){ return mul(1/n); }


    public double length() {return Math.sqrt(lengthSquared());}
    public double lengthSquared() {return this.x * this.x + this.y * this.y + this.z * this.z;}
}
