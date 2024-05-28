package me.udnek.scene;

import me.udnek.objects.SceneObject;
import me.udnek.utils.Triangle;
import me.udnek.utils.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RayTracer {

    private List<SceneObject> objectsToRender;
    private List<Triangle> cachedPlanes;
    private Vector3d cameraPosition;
    public RayTracer(List<SceneObject> objectsToRender){
        this.objectsToRender = objectsToRender;
    }

    public void recacheObjects(Vector3d cameraPosition){
        cachedPlanes = new ArrayList<>();

        for (SceneObject object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (Triangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);
                cachedPlanes.add(plane);
            }
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


    private int colorizeRayTrace(Vector3d hitPosition, Triangle triangle){
        double d0 = VectorUtils.distance(hitPosition, triangle.getVertex0());
        double d1 = VectorUtils.distance(hitPosition, triangle.getVertex1());
        double d2 = VectorUtils.distance(hitPosition, triangle.getVertex2());
        Vector3d distances = new Vector3d(d0, d1, d2);
        double minDistance = VectorUtils.getMin(distances);
        if (minDistance <= 0.03) return Color.WHITE.getRGB();

        Vector3d color = new Vector3d(1/d0, 1/d1 ,1/d2);
        color.div(VectorUtils.getMax(color));
        VectorUtils.cutTo(color, 1f);

        return new Color((float) color.x, (float) color.y, (float) color.z).getRGB();
    }
}
