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
    private final Vector3d cameraPosition;

    public RayTracer(Vector3d cameraPosition){
        this.cameraPosition = cameraPosition;
        this.objectsToRender = new ArrayList<>();
    }

    public RayTracer(Vector3d cameraPosition, List<SceneObject> objectsToRender){
        this.cameraPosition = cameraPosition;
        this.objectsToRender = objectsToRender;
    }
    public RayTraceResult rayTrace(Vector3d direction){


        ArrayList<RayTraceResult> rayTraceResults = new ArrayList<>();
        for (SceneObject object : objectsToRender) {
            RayTraceResult rayTraceResult = rayTraceObject(direction, object);
            if (rayTraceResult.isHitted()){
                rayTraceResults.add(rayTraceResult);
            }
        }

        return sortNearest(rayTraceResults);
    }

    private RayTraceResult rayTraceObject(Vector3d direction, SceneObject sceneObject){

        ArrayList<RayTraceResult> rayTraceResults = new ArrayList<>();

        for (Triangle plane : sceneObject.getRenderTriangles()) {
            plane.addToAllVertexes(sceneObject.getPosition()).subFromAllVertexes(cameraPosition);
            Vector3d hitPosition = VectorUtils.triangleRayIntersection(direction, plane);

            if (hitPosition != null){
                RayTraceResult rayTraceResult = new RayTraceResult(hitPosition);
                //rayTraceResult.setSuggestedColor(Color.WHITE.getRGB());
                rayTraceResult.setSuggestedColor(colorizeRayTraceResult(rayTraceResult, plane));
                rayTraceResults.add(rayTraceResult);


            }
        }

        return sortNearest(rayTraceResults);
    }


    private RayTraceResult sortNearest(List<RayTraceResult> rayTraceResults){
        if (rayTraceResults.isEmpty()) return new RayTraceResult();

        RayTraceResult nearestRayTrace = rayTraceResults.get(0);

        for (RayTraceResult rayTraceResult : rayTraceResults) {
            if (rayTraceResult.getDistance() < nearestRayTrace.getDistance()){
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
        Vector3d color = new Vector3d(1/d0, 1/d1 ,1/d2);
        color.div(VectorUtils.getMax(color));
        VectorUtils.cutTo(color, 1f);

        return new Color((float) color.x, (float) color.y, (float) color.z).getRGB();
    }
}
