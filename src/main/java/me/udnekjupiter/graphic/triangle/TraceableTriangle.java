package me.udnekjupiter.graphic.triangle;

import me.udnekjupiter.util.Triangle;
import me.udnekjupiter.util.Utils;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class TraceableTriangle extends Triangle {
    public TraceableTriangle(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2) {
        super(vertex0, vertex1, vertex2);
    }

    public TraceableTriangle(TraceableTriangle triangle) {
        super(triangle);
    }

    public static TraceableTriangle empty(){
        return new TraceableTriangle(new Vector3d(), new Vector3d(), new Vector3d());
    }

    public int getColorWhenTraced(Vector3d hitPosition){
        double d0 = VectorUtils.distance(hitPosition, vertex0);
        double d1 = VectorUtils.distance(hitPosition, vertex1);
        double d2 = VectorUtils.distance(hitPosition, vertex2);

        Vector3d distances = new Vector3d(d0, d1, d2);
        double minDistance = VectorUtils.getMin(distances);
        Vector3d color;
        if (minDistance <= 0){
            color = new Vector3d(1f, 1f, 1f);
        } else {
            color = new Vector3d(1/d0, 1/d1 ,1/d2);
            color.div(VectorUtils.getMax(color));
            VectorUtils.cutTo(color, 1f);
        }

        return Utils.vectorToColor(color).getRGB();
    }

    public TraceableTriangle copyWithVertices(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2){
        TraceableTriangle newTriangle = copy();
        newTriangle.setVertices(vertex0, vertex1, vertex2);
        return newTriangle;
    }

    @Override
    public TraceableTriangle copy() {
        return new TraceableTriangle(this);
    }
}
