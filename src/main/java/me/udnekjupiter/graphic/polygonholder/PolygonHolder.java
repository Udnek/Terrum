package me.udnekjupiter.graphic.polygonholder;

import me.udnekjupiter.graphic.triangle.TraceableTriangle;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public interface PolygonHolder {
    void recacheObjects(int width, int height);
    List<TraceableTriangle> getCachedPlanes(Vector3d direction);
    List<TraceableTriangle> getLightCachedPlanes(Vector3d direction);

    enum Type{
        DEFAULT,
        SMART
    }
}
