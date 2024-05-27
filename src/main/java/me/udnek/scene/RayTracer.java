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

    public RayTracer(Vector3d cameraPosition){
        this.objectsToRender = new ArrayList<>();
    }

    public RayTracer(List<SceneObject> objectsToRender){
        this.objectsToRender = objectsToRender;
    }
    public RayTraceResult rayTrace(Vector3d cameraPosition, Vector3d direction){


        ArrayList<RayTraceResult> rayTraceResults = new ArrayList<>();
        for (SceneObject object : objectsToRender) {
            RayTraceResult rayTraceResult = rayTraceObject(cameraPosition, direction, object);
            if (rayTraceResult.isHit()){
                rayTraceResults.add(rayTraceResult);
            }
        }

        return sortNearest(rayTraceResults);
    }

    private RayTraceResult rayTraceObject(Vector3d cameraPosition, Vector3d direction, SceneObject sceneObject){

        ArrayList<RayTraceResult> rayTraceResults = new ArrayList<>();

        for (Triangle plane : sceneObject.getRenderTriangles()) {
            plane.addToAllVertexes(sceneObject.getPosition()).subFromAllVertexes(cameraPosition);
            Vector3d hitPosition = VectorUtils.triangleRayIntersection(direction, plane);

            if (hitPosition != null){
                RayTraceResult rayTraceResult = new RayTraceResult(hitPosition);

                rayTraceResult.setSuggestedColor(colorizeRayTraceResult(rayTraceResult, plane));
                rayTraceResults.add(rayTraceResult);


            }
        }

        return sortNearest(rayTraceResults);
    }


    private RayTraceResult sortNearest(List<RayTraceResult> rayTraceResults){
        if (rayTraceResults.isEmpty()) return new RayTraceResult();
        RayTraceResult nearestRayTrace = rayTraceResults.get(0);
        if (rayTraceResults.size() == 1) return nearestRayTrace;

        for (int i = 1; i < rayTraceResults.size(); i++) {
            RayTraceResult rayTraceResult = rayTraceResults.get(i);
            if (rayTraceResult.getDistance() < nearestRayTrace.getDistance()) {
                nearestRayTrace = rayTraceResult;
            }
        }
        return nearestRayTrace;
    }

    private int colorizeRayTraceResult(RayTraceResult rayTraceResult, Triangle triangle){
        Vector3d hitPosition = rayTraceResult.getHitPosition();

        double d0 = VectorUtils.distance(hitPosition, triangle.getVertex0());
        double d1 = VectorUtils.distance(hitPosition, triangle.getVertex1());
        double d2 = VectorUtils.distance(hitPosition, triangle.getVertex2());
        Vector3d distances = new Vector3d(d0, d1, d2);
        double minDistance = VectorUtils.getMin(distances);
        if (minDistance <= 0.1) return Color.WHITE.getRGB();

        Vector3d color = new Vector3d(1/d0, 1/d1 ,1/d2);
        color.div(VectorUtils.getMax(color));
        VectorUtils.cutTo(color, 1f);

        return new Color((float) color.x, (float) color.y, (float) color.z).getRGB();
    }
}
