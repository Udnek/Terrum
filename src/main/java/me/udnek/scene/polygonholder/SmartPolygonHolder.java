package me.udnek.scene.polygonholder;

import me.udnek.object.SceneObject;
import me.udnek.scene.Camera;
import me.udnek.util.Triangle;
import me.udnek.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class SmartPolygonHolder implements PolygonHolder{
    private List<? extends SceneObject> objectsToRender;
    private Camera camera;
    private List<Triangle> leftDownCachedPlanes;
    private List<Triangle> rightUpCachedPlanes;

    private Triangle leftDownTriangle;
    private Triangle rightUpTriangle;

    public SmartPolygonHolder(List<? extends SceneObject> objectsToRender, Camera camera){
        this.objectsToRender = objectsToRender;
        this.camera = camera;
    }

    public void recacheObjects(int width, int height){

        double fovMultiplier = width/camera.getFov();

/*        Vector3d centerDirection = new Vector3d(0, 0, 1);
        camera.rotateVector(centerDirection);*/
        Vector3d leftDownCorner = new Vector3d(-width / 2.0, -height / 2.0, fovMultiplier);
        camera.rotateVector(leftDownCorner);
        Vector3d rightDownCorner = new Vector3d(width / 2.0, -height / 2.0, fovMultiplier);
        camera.rotateVector(rightDownCorner);
        Vector3d leftUpCorner = new Vector3d(-width / 2.0, height / 2.0, fovMultiplier);
        camera.rotateVector(leftUpCorner);
        Vector3d rightUpCorner = new Vector3d(width / 2.0, height / 2.0, fovMultiplier);
        camera.rotateVector(rightUpCorner);

        leftDownTriangle = new Triangle(rightDownCorner, leftDownCorner, leftUpCorner);
        rightUpTriangle = new Triangle(rightDownCorner, leftUpCorner, rightUpCorner);

        leftDownCachedPlanes = new ArrayList<>();
        rightUpCachedPlanes = new ArrayList<>();

        Vector3d cameraPosition = camera.getPosition();

        for (SceneObject object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (Triangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);

                Vector3d vertex0 = plane.getVertex0();
                Vector3d vertex1 = plane.getVertex1();
                Vector3d vertex2 = plane.getVertex2();

                boolean vertex0Intersection = vectorInLeftDownTriangle(vertex0);
                boolean vertex1Intersection = vectorInLeftDownTriangle(vertex1);
                boolean vertex2Intersection = vectorInLeftDownTriangle(vertex2);

                if (vertex0Intersection || vertex1Intersection || vertex2Intersection) leftDownCachedPlanes.add(plane);

                vertex0Intersection = vectorInRightUpTriangle(vertex0);
                vertex1Intersection = vectorInRightUpTriangle(vertex1);
                vertex2Intersection = vectorInRightUpTriangle(vertex2);

                if (vertex0Intersection || vertex1Intersection || vertex2Intersection) rightUpCachedPlanes.add(plane);
            }
        }
    }

    private boolean vectorInLeftDownTriangle(Vector3d vector){
        Vector3d intersection = VectorUtils.triangleRayIntersection(vector, leftDownTriangle);
        return intersection != null;
    }
    private boolean vectorInRightUpTriangle(Vector3d vector){
        Vector3d intersection = VectorUtils.triangleRayIntersection(vector, rightUpTriangle);
        return intersection != null;
    }

    public List<Triangle> getCachedPlanes(Vector3d direction) {
        if (vectorInLeftDownTriangle(direction)) return leftDownCachedPlanes;
        return rightUpCachedPlanes;
    }

}
