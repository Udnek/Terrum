package me.udnek.utils;

import org.realityforge.vecmath.Vector3d;

public class Triangle {

    private Vector3d vertex0;
    private Vector3d vertex1;
    private Vector3d vertex2;

    public Triangle(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2) {
        this.vertex0 = vertex0;
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    public Triangle(Triangle triangle) {
        this.vertex0 = triangle.getVertex0();
        this.vertex1 = triangle.getVertex1();
        this.vertex2 = triangle.getVertex2();
    }

    public void setVertex0(Vector3d vertex) {
        this.vertex0 = vertex;
    }
    public void setVertex1(Vector3d vertex) {
        this.vertex1 = vertex;
    }
    public void setVertex2(Vector3d vertex) {
        this.vertex2 = vertex;
    }

    public Vector3d getVertex0() {
        return this.vertex0.dup();
    }
    public Vector3d getVertex1() {
        return this.vertex1.dup();
    }
    public Vector3d getVertex2() {
        return this.vertex2.dup();
    }

    public Vector3d getEdge0(){
        return getVertex1().sub(getVertex0());
    }
    public Vector3d getEdge1(){
        return getVertex2().sub(getVertex1());
    }
    public Vector3d getEdge2(){
        return getVertex0().sub(getVertex2());
    }

    public Vector3d getNormal(){return new Vector3d().cross(getEdge0(), getEdge1());}
    public double getArea(){
        return VectorUtils.getAreaOfTriangle(getEdge0(), getEdge1());
    }

    public Triangle copy(){
        return new Triangle(this);
    }
}
