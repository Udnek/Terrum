package me.udnekjupiter.graphic.polygonholder;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.renderable.RenderableObject;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class DefaultPolygonHolder implements PolygonHolder{

    private Camera camera;
    private List<? extends RenderableObject> objectsToRender;
    private List<RenderableTriangle> cachedPlanes;
    private List<RenderableTriangle> lightCachedPlanes;
    private LightSource lightSource;

    public DefaultPolygonHolder(List<RenderableObject> objectsToRender, Camera camera, LightSource lightSource){
        this.camera = camera;
        this.objectsToRender = objectsToRender;
        this.lightSource = lightSource;

    }

    @Override
    public void recacheObjects(int width, int height) {
        Vector3d cameraPosition = camera.getPosition();
        cachedPlanes = new ArrayList<>();

        for (RenderableObject object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (RenderableTriangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);
                cachedPlanes.add(plane);
            }
        }

        if (!Application.APPLICATION_SETTINGS.doLight) return;

        lightCachedPlanes = new ArrayList<>();
        Vector3d lightPosition = lightSource.getPosition();
        for (RenderableObject object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (RenderableTriangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(lightPosition);
                lightCachedPlanes.add(plane);
            }
        }

    }

    @Override
    public List<RenderableTriangle> getCachedPlanes(Vector3d direction) { return cachedPlanes;}
    @Override
    public List<RenderableTriangle> getLightCachedPlanes(Vector3d direction) {return lightCachedPlanes;}
}
