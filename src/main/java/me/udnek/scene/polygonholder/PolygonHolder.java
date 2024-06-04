package me.udnek.scene.polygonholder;

import me.udnek.util.Triangle;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public interface PolygonHolder {
    void recacheObjects(int width, int height);
    List<Triangle> getCachedPlanes(Vector3d direction);
}
