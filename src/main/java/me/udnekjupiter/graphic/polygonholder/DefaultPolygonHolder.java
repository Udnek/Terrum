package me.udnekjupiter.graphic.polygonholder;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.traceable.TraceableObject;
import me.udnekjupiter.graphic.triangle.TraceableTriangle;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class DefaultPolygonHolder implements PolygonHolder{

    private Camera camera;
    private List<? extends TraceableObject> objectsToRender;
    private List<TraceableTriangle> cachedPlanes;
    private List<TraceableTriangle> lightCachedPlanes;
    private LightSource lightSource;

    public DefaultPolygonHolder(List<TraceableObject> objectsToRender, Camera camera, LightSource lightSource){
        this.camera = camera;
        this.objectsToRender = objectsToRender;
        this.lightSource = lightSource;

    }

    @Override
    public void recacheObjects(int width, int height) {
        Vector3d cameraPosition = camera.getPosition();
        cachedPlanes = new ArrayList<>();

        for (TraceableObject object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (TraceableTriangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);
                cachedPlanes.add(plane);
            }
        }

        if (!Application.APPLICATION_SETTINGS.doLight) return;

        lightCachedPlanes = new ArrayList<>();
        Vector3d lightPosition = lightSource.getPosition();
        for (TraceableObject object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (TraceableTriangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(lightPosition);
                lightCachedPlanes.add(plane);
            }
        }

    }

    @Override
    public List<TraceableTriangle> getCachedPlanes(Vector3d direction) { return cachedPlanes;}
    @Override
    public List<TraceableTriangle> getLightCachedPlanes(Vector3d direction) {return lightCachedPlanes;}
}
