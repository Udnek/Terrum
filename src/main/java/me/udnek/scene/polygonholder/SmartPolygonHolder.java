package me.udnek.scene.polygonholder;

import me.udnek.objects.SceneObject;
import me.udnek.scene.Camera;
import me.udnek.utils.Triangle;
import me.udnek.utils.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class SmartPolygonHolder implements PolygonHolder{
    private List<? extends SceneObject> objectsToRender;
    private Camera camera;
    private List<Triangle> leftCachedPlanes;
    private List<Triangle> rightCachedPlanes;

    private Vector3d centerDirection;
/*    private Vector3d maxLeftDirection;
    private Vector3d maxRightDirection;*/

    public SmartPolygonHolder(List<? extends SceneObject> objectsToRender, Camera camera){
        this.objectsToRender = objectsToRender;
        this.camera = camera;
    }

    public void recacheObjects(int width, int height){

        double fovMultiplier = width/camera.getFov();

        centerDirection = new Vector3d(0, 0, 1);
        camera.rotateVectorYaw(centerDirection);
        Vector3d maxLeftDirection = new Vector3d(-width/2.0, 0, fovMultiplier);
        VectorUtils.rotateYaw(maxLeftDirection, Math.toRadians(camera.getYaw()));
        Vector3d maxRightDirection = new Vector3d(width/2.0, 0, fovMultiplier);
        VectorUtils.rotateYaw(maxRightDirection, Math.toRadians(camera.getYaw()));


        leftCachedPlanes = new ArrayList<>();
        rightCachedPlanes = new ArrayList<>();

        Vector3d cameraPosition = camera.getPosition();

        for (SceneObject object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (Triangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);

                boolean outRightBound = Boolean.FALSE.equals(isAllLefter(maxRightDirection, plane));
                if (outRightBound) continue;
                boolean outLeftBound = Boolean.TRUE.equals(isAllLefter(maxLeftDirection, plane));
                if (outLeftBound) continue;
                Boolean allLefter = isAllLefter(centerDirection, plane);

                if (allLefter == null){
                    leftCachedPlanes.add(plane);
                    rightCachedPlanes.add(plane);
                }
                else if (allLefter){
                    leftCachedPlanes.add(plane);
                }
                else {
                    rightCachedPlanes.add(plane);
                }
            }
        }
    }

    private boolean isVectorLefter(Vector3d origin, Vector3d second){
        double dot = origin.x * -second.z + origin.z * second.x;
        return (dot < 0);
    }

    private Boolean isAllLefter(Vector3d origin, Triangle triangle){
        boolean vertex0Lefter = isVectorLefter(origin, triangle.getVertex0());
        boolean vertex1Lefter = isVectorLefter(origin, triangle.getVertex1());
        boolean vertex2Lefter = isVectorLefter(origin, triangle.getVertex2());
        if (vertex0Lefter && vertex1Lefter && vertex2Lefter) return true;
        if (!vertex0Lefter && !vertex1Lefter && !vertex2Lefter) return false;
        return null;
    }

/*    private void cacheObject(List<Triangle> cache, SceneObject object, Vector3d position){
        Vector3d objectPosition = object.getPosition();
        for (Triangle plane: object.getRenderTriangles()) {
            plane.addToAllVertexes(objectPosition).subFromAllVertexes(position);
            cache.add(plane);
        }
    }*/

    public List<Triangle> getCachedPlanes(Vector3d direction) {
        if (isVectorLefter(centerDirection, direction)) return leftCachedPlanes;
        return rightCachedPlanes;
    }

}
