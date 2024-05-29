package me.udnek.scene;

import me.udnek.objects.SceneObject;
import me.udnek.objects.light.LightSource;
import me.udnek.utils.Triangle;
import me.udnek.utils.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RayTracer {

    private Vector3d cameraPosition;
    private List<SceneObject> objectsToRender;
    private List<Triangle> cachedPlanes;

    private LightSource lightSource;
    private Vector3d lastLightPosition;
    private List<Triangle> lightCachedPlanes;



    public RayTracer(List<SceneObject> objectsToRender, LightSource lightSource){
        this.objectsToRender = objectsToRender;
        this.lightSource = lightSource;
        this.lastLightPosition = null;
    }

    public void recacheObjects(Vector3d position){
        cameraPosition = position;

        // camera cache
        cachedPlanes = new ArrayList<>();
        for (SceneObject object : objectsToRender) {
            cacheObject(cachedPlanes, object, cameraPosition);
        }
        cacheObject(cachedPlanes, lightSource, cameraPosition);

        // light cache
        Vector3d lightSourcePosition = lightSource.getPosition();
        if (lastLightPosition != null && lightSourcePosition.isEqualTo(lastLightPosition)) return; //skipping light recache
        lastLightPosition = lightSourcePosition;
        lightCachedPlanes = new ArrayList<>();
        for (SceneObject object : objectsToRender) {
            cacheObject(lightCachedPlanes, object, lightSourcePosition);
        }
    }

    private void cacheObject(List<Triangle> cache, SceneObject object, Vector3d position){
        Vector3d objectPosition = object.getPosition();
        for (Triangle plane: object.getRenderTriangles()) {
            plane.addToAllVertexes(objectPosition).subFromAllVertexes(position);
            cache.add(plane);
        }
    }

    public int rayTrace(Vector3d direction){

        Vector3d nearestHitPosition = null;
        Triangle nearestPlane = null;
        double nearestDistance = Double.POSITIVE_INFINITY;


        for (Triangle plane : cachedPlanes) {
            Vector3d hitPosition = VectorUtils.triangleRayIntersection(direction, plane);

            if (hitPosition != null){
                if (hitPosition.lengthSquared() < nearestDistance){
                    nearestHitPosition = hitPosition;
                    nearestPlane = plane;
                    nearestDistance = hitPosition.lengthSquared();
                }
            }
        }
        if (nearestPlane == null) return 0;
        return colorizeRayTrace(nearestHitPosition, nearestPlane);
    }

    private double positionLighted(Vector3d position, Triangle plane){

        // to absolute position;
        position.add(cameraPosition);
        // to light relative position
        position.sub(lastLightPosition);

        // from light to point direction
        //Vector3d direction = position.sub(lightSource.getPosition());
        Vector3d direction = position;

        final float EPSILON = 0.0001f;

        for (Triangle triangle : lightCachedPlanes) {
            Vector3d hitPosition = VectorUtils.triangleRayIntersection(direction, triangle);
            if (hitPosition != null){
                if (direction.lengthSquared() - EPSILON > hitPosition.lengthSquared()){
                    return 0;
                }
            }
        }
        double perpendicularity = 1 - new Vector3d().cross(plane.getNormal().normalize(), direction.normalize()).length();
        return perpendicularity;
    }

    private int colorizeRayTrace(Vector3d hitPosition, Triangle plane){

        double d0 = VectorUtils.distance(hitPosition, plane.getVertex0());
        double d1 = VectorUtils.distance(hitPosition, plane.getVertex1());
        double d2 = VectorUtils.distance(hitPosition, plane.getVertex2());
        Vector3d distances = new Vector3d(d0, d1, d2);
        double minDistance = VectorUtils.getMin(distances);
        Vector3d color;
        if (minDistance <= 0.01){
            color = new Vector3d(1f, 1f, 1f);
        } else {
            color = new Vector3d(1/d0, 1/d1 ,1/d2);
            color.div(VectorUtils.getMax(color));
            VectorUtils.cutTo(color, 1f);
        }

        float light = (float) positionLighted(hitPosition, plane);
        light += 0.1f;
        if (light < 0.15) light = 0.15f;
        else if (light > 1) light = 1;

        color.mul(light);

        return new Color((float) color.x, (float) color.y, (float) color.z).getRGB();

    }
}
