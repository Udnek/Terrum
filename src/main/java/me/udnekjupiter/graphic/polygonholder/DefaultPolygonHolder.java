package me.udnekjupiter.graphic.polygonholder;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.traceable.TraceableObject;
import me.udnekjupiter.util.Triangle;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class DefaultPolygonHolder implements PolygonHolder{

    private Camera camera;
    private List<? extends TraceableObject> objectsToRender;
    private List<Triangle> cachedPlanes;
    private List<Triangle> lightCachedPlanes;
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
            for (Triangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);
                cachedPlanes.add(plane);
            }
        }

        if (!ApplicationSettings.GLOBAL.doLight) return;

        lightCachedPlanes = new ArrayList<>();
        Vector3d lightPosition = lightSource.getPosition();
        for (TraceableObject object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (Triangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(lightPosition);
                lightCachedPlanes.add(plane);
            }
        }

    }

    @Override
    public List<Triangle> getCachedPlanes(Vector3d direction) { return cachedPlanes;}
    @Override
    public List<Triangle> getLightCachedPlanes(Vector3d direction) {return lightCachedPlanes;}
}
