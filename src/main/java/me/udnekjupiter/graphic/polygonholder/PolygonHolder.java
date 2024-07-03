package me.udnekjupiter.graphic.polygonholder;

import me.udnekjupiter.util.Triangle;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public interface PolygonHolder {
    void recacheObjects(int width, int height);
    List<Triangle> getCachedPlanes(Vector3d direction);
    List<Triangle> getLightCachedPlanes(Vector3d direction);

    enum Type{
        DEFAULT,
        SMART
    }
}
