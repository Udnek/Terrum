package me.udnekjupiter.graphic.polygonholder;

import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public interface PolygonHolder {
    void recacheObjects(int width, int height);
    List<RenderableTriangle> getCachedPlanes(Vector3d direction);
    List<RenderableTriangle> getLightCachedPlanes(Vector3d direction);

    enum Type{
        DEFAULT,
        SMART
    }
}
