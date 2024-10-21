package me.udnekjupiter.graphic.polygonholder;

import me.udnekjupiter.app.StandartApplication;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.renderable.RenderableObject3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class DefaultPolygonHolder implements PolygonHolder{

    private Camera camera;
    private List<? extends RenderableObject3d> objectsToRender;
    private List<RenderableTriangle> cachedPlanes;
    private List<RenderableTriangle> lightCachedPlanes;
    private LightSource lightSource;

    public DefaultPolygonHolder(@NotNull List<RenderableObject3d> objectsToRender, @NotNull Camera camera, @NotNull LightSource lightSource){
        this.camera = camera;
        this.objectsToRender = objectsToRender;
        this.lightSource = lightSource;

    }

    @Override
    public void recacheObjects(int width, int height) {
        Vector3d cameraPosition = camera.getPosition();
        cachedPlanes = new ArrayList<>();

        for (RenderableObject3d object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (RenderableTriangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);
                cachedPlanes.add(plane);
            }
        }

        if (!StandartApplication.APPLICATION_SETTINGS.doLight) return;

        lightCachedPlanes = new ArrayList<>();
        Vector3d lightPosition = lightSource.getPosition();
        for (RenderableObject3d object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (RenderableTriangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(lightPosition);
                lightCachedPlanes.add(plane);
            }
        }

    }

    @Override
    public @NotNull List<RenderableTriangle> getCachedPlanes(@Nullable Vector3d direction) { return cachedPlanes;}
    @Override
    public @NotNull List<RenderableTriangle> getLightCachedPlanes(@Nullable Vector3d direction) {return lightCachedPlanes;}
}
