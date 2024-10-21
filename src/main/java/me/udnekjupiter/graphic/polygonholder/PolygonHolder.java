package me.udnekjupiter.graphic.polygonholder;

import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public interface PolygonHolder {
    void recacheObjects(int width, int height);
    @NotNull List<RenderableTriangle> getCachedPlanes(@UnknownNullability Vector3d direction);
    @NotNull List<RenderableTriangle> getLightCachedPlanes(@UnknownNullability Vector3d direction);

    enum Type{
        DEFAULT,
        SMART
    }
}
