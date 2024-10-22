package me.udnekjupiter.graphic.object.renderable.shape;

import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.function.Consumer;

public class IcosphereObject extends GraphicObject3d {

    protected final double radius;
    protected RenderableTriangle[] polygons;
    public final int subdivideIterations;
    public IcosphereObject(Vector3d position, double radius, int subdivideIterations, RenderableTriangle example) {
        super(position);
        this.radius = radius;
        this.subdivideIterations = subdivideIterations;
        generatePolygons(example);
    }

    protected void generatePolygons(RenderableTriangle example){
        double icoSize =  radius/2/ IcosahedronObject.calculateCircumradius(1);
        IcosahedronObject icosahedronObject = new IcosahedronObject(new Vector3d(), icoSize, example);

        polygons = icosahedronObject.getRenderTriangles();
        if (subdivideIterations < 1) return;
        for (int i = 0; i < subdivideIterations; i++) {
            polygons = generatePolygons(polygons);
        }
    }

    protected RenderableTriangle[] generatePolygons(RenderableTriangle[] polygons){
        RenderableTriangle[] newPolygons = new RenderableTriangle[polygons.length * 4];
        for (int i = 0; i < polygons.length; i++) {
            RenderableTriangle[] subdividedFaces = subdivideFace(polygons[i]);
            for (int j = 0; j < subdividedFaces.length; j++) {
                newPolygons[i*4 + j] = subdividedFaces[j];
            }
        }
        return newPolygons;
    }



    protected RenderableTriangle[] subdivideFace(RenderableTriangle face){
        Vector3d newVertex01 = face.getVertex0().add(face.getVertex1()).div(2);
        Vector3d newVertex12 = face.getVertex1().add(face.getVertex2()).div(2);
        Vector3d newVertex02 = face.getVertex2().add(face.getVertex0()).div(2);

        double distance0 = newVertex01.length();
        double distance1 = newVertex12.length();
        double distance2 = newVertex02.length();

        newVertex01.div(distance0).mul(radius);
        newVertex12.div(distance1).mul(radius);
        newVertex02.div(distance2).mul(radius);

        RenderableTriangle face0 = face.copyWithVertices(face.getVertex0(), newVertex01, newVertex02);
        RenderableTriangle face1 = face.copyWithVertices(face.getVertex1(), newVertex01, newVertex12);
        RenderableTriangle face2 = face.copyWithVertices(face.getVertex2(), newVertex12, newVertex02);
        RenderableTriangle faceCenter = face.copyWithVertices(newVertex01, newVertex12, newVertex02);

        return new RenderableTriangle[]{face0, face1, face2, faceCenter};
    }

    @Override
    public RenderableTriangle @NotNull [] getRenderTriangles() {
        RenderableTriangle[] copy = new RenderableTriangle[polygons.length];
        for (int i = 0; i < polygons.length; i++) {
            copy[i] = polygons[i].copy();
        }
        return copy;
    }

    @Override
    public RenderableTriangle @NotNull [] getUnsafeRenderTriangles() {
        return polygons;
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {
        for (RenderableTriangle polygon : polygons) {
            consumer.accept(polygon);
        }
    }
}
