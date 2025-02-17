package me.udnekjupiter.util;

import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.Vector3d;

public class Triangle {

    protected Vector3d vertex0;
    protected Vector3d vertex1;
    protected Vector3d vertex2;

    public Triangle(@NotNull Vector3d vertex0, @NotNull Vector3d vertex1, @NotNull Vector3d vertex2) {
        this.vertex0 = vertex0;
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    public Triangle(@NotNull Triangle triangle) {
        this(triangle.getVertex0(), triangle.getVertex1(), triangle.getVertex2());
    }


    public void setVertex0(@NotNull Vector3d vertex) {
        this.vertex0 = vertex;
    }
    public void setVertex1(@NotNull Vector3d vertex) {
        this.vertex1 = vertex;
    }
    public void setVertex2(@NotNull Vector3d vertex) {
        this.vertex2 = vertex;
    }

    public void setVertices(@NotNull Vector3d vertex0, @NotNull Vector3d vertex1, @NotNull Vector3d vertex2){
        setVertex0(vertex0);
        setVertex1(vertex1);
        setVertex2(vertex2);
    }

    public @NotNull Vector3d getVertex0() {return vertex0.dup();}
    public @NotNull Vector3d getVertex1() {return vertex1.dup();}
    public @NotNull Vector3d getVertex2() {return vertex2.dup();}
    public @NotNull Vector3d getUnsafeVertex0() {return vertex0;}
    public @NotNull Vector3d getUnsafeVertex1() {return vertex1;}
    public @NotNull Vector3d getUnsafeVertex2() {return vertex2;}

    public @NotNull Vector3d[] getVertices() {
        return new Vector3d[]{getVertex0(), getVertex1(), getVertex2()};
    }

    public @NotNull Vector3d getEdge0() {return getVertex1().sub(vertex0);}
    public @NotNull Vector3d getEdge1() {return getVertex2().sub(vertex1);}
    public @NotNull Vector3d getEdge2() {return getVertex0().sub(vertex2);}

    public @NotNull Vector3d getCenter(){
        return new Vector3d(
                (vertex0.x + vertex1.x + vertex2.x)/3.0,
                (vertex0.y + vertex1.y + vertex2.y)/3.0,
                (vertex0.z + vertex1.z + vertex2.z)/3.0
        );
    }

    public @NotNull Vector3d getNormal(){
        return VectorUtils.getNormal(getEdge0(), getEdge1());
    }
    public double getArea(){
        return VectorUtils.getAreaOfTriangle(getEdge0(), getEdge1());
    }

    public @NotNull Triangle addToAllVertexes(Vector3d vector){
        this.vertex0.add(vector);
        this.vertex1.add(vector);
        this.vertex2.add(vector);
        return this;
    }

    public @NotNull Triangle subFromAllVertexes(Vector3d vector){
        this.vertex0.sub(vector);
        this.vertex1.sub(vector);
        this.vertex2.sub(vector);
        return this;
    }

    public @NotNull Triangle copy(){
        return new Triangle(this);
    }
}
