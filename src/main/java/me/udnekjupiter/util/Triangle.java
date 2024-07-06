package me.udnekjupiter.util;

import org.realityforge.vecmath.Vector3d;

public class Triangle {

    private Vector3d vertex0;
    private Vector3d vertex1;
    private Vector3d vertex2;

    private Vector3d edge0;
    private Vector3d edge1;
    private Vector3d edge2;

    public Triangle(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2) {
        this.vertex0 = vertex0;
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        recalculateEdges();
    }

    public Triangle(Triangle triangle) {
        this(triangle.getVertex0(), triangle.getVertex1(), triangle.getVertex2());
    }

    public void recalculateEdges(){
        edge0 = getVertex1().sub(vertex0);
        edge1 = getVertex2().sub(vertex1);
        edge2 = getVertex0().sub(vertex2);
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

    public void setVertices(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2){
        setVertex0(vertex0);
        setVertex1(vertex1);
        setVertex2(vertex2);
        recalculateEdges();
    }

    public Vector3d getVertex0() {return vertex0.dup();}
    public Vector3d getVertex1() {return vertex1.dup();}
    public Vector3d getVertex2() {return vertex2.dup();}

    public Vector3d getEdge0() {return edge0;}
    public Vector3d getEdge1() {return edge1;}
    public Vector3d getEdge2() {return edge2;}

    public Vector3d getCenter(){
        return new Vector3d(
                (vertex0.x + vertex1.x + vertex2.x)/3.0,
                (vertex0.y + vertex1.y + vertex2.y)/3.0,
                (vertex0.z + vertex1.z + vertex2.z)/3.0
        );
    }

    public Vector3d getNormal(){
        return VectorUtils.getNormal(edge0, edge1);
    }
    public double getArea(){
        return VectorUtils.getAreaOfTriangle(edge0, edge1);
    }

    public Triangle copy(){
        return new Triangle(this);
    }

    public Triangle addToAllVertexes(Vector3d vector){
        this.vertex0.add(vector);
        this.vertex1.add(vector);
        this.vertex2.add(vector);
        return this;
    }

    public Triangle subFromAllVertexes(Vector3d vector){
        this.vertex0.sub(vector);
        this.vertex1.sub(vector);
        this.vertex2.sub(vector);
        return this;
    }

    public String asString() {
        return vertex0.asString() + vertex1.asString() + vertex2.asString();
    }
}
