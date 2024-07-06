package me.udnekjupiter.graphic.triangle;

import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class MassEssenceTriangle extends TraceableTriangle{
    public MassEssenceTriangle(Vector3d vertex0, Vector3d vertex1, Vector3d vertex2) {
        super(vertex0, vertex1, vertex2);
    }

    public MassEssenceTriangle(TraceableTriangle triangle) {
        super(triangle);
    }

    @Override
    public int getColorWhenTraced(Vector3d hitPosition) {
        double d0 = VectorUtils.distance(hitPosition, vertex0);
        double d1 = VectorUtils.distance(hitPosition, vertex1);
        double d2 = VectorUtils.distance(hitPosition, vertex2);

        Vector3d distances = new Vector3d(d0, d1, d2);
        double minDistance = VectorUtils.getMin(distances);
        int color;
        if (minDistance <= 0.08){
            color = Color.WHITE.getRGB();
        } else {
            color = Color.LIGHT_GRAY.getRGB();
        }

        return color;
    }

    @Override
    public TraceableTriangle copy() {
        return new MassEssenceTriangle(this);
    }
}
