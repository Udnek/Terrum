package me.udnek.scene;

import me.udnek.objects.PolygonObject;
import me.udnek.objects.SceneObject;
import me.udnek.utils.Vector3;
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
    public int rayCast(Vector3d direction){
        for (SceneObject object : objectsToRender) {
            if (rayCastObject(direction, object)) return Color.WHITE.getRGB();
        }

        return Color.BLACK.getRGB();
    }

    private boolean rayCastObject(Vector3d direction, SceneObject sceneObject){
        if (sceneObject instanceof PolygonObject){
            return (VectorUtils.triangleRayIntersection(direction, ((PolygonObject) sceneObject).getPlane()) != null);
        }
        else {
            double distance = VectorUtils.distanceFromLineToPoint(direction, sceneObject.getPosition().sub(cameraPosition));
            return distance < 0.1;
        }
    }
}
