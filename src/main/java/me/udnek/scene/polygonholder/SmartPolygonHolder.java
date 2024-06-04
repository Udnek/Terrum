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

    private List<Triangle> leftCachedPlanes;
    private List<Triangle> downCachedPlanes;
    private List<Triangle> rightCachedPlanes;
    private List<Triangle> upCachedPlanes;

    private Triangle leftTriangle;
    private Triangle downTriangle;
    private Triangle rightTriangle;
    private Triangle upTriangle;


    public SmartPolygonHolder(List<? extends SceneObject> objectsToRender, Camera camera){
        this.objectsToRender = objectsToRender;
        this.camera = camera;
    }

    public void recacheObjects(int width, int height){

        double fovMultiplier = width/camera.getFov();

        Vector3d centerDirection = new Vector3d(0, 0, 1);
        camera.rotateVector(centerDirection);
        Vector3d leftDownCorner = new Vector3d(-width / 2.0, -height / 2.0, fovMultiplier);
        camera.rotateVector(leftDownCorner);
        Vector3d rightDownCorner = new Vector3d(width / 2.0, -height / 2.0, fovMultiplier);
        camera.rotateVector(rightDownCorner);
        Vector3d leftUpCorner = new Vector3d(-width / 2.0, height / 2.0, fovMultiplier);
        camera.rotateVector(leftUpCorner);
        Vector3d rightUpCorner = new Vector3d(width / 2.0, height / 2.0, fovMultiplier);
        camera.rotateVector(rightUpCorner);

        leftTriangle = new Triangle(leftDownCorner, centerDirection, leftUpCorner);
        downTriangle = new Triangle(leftDownCorner, centerDirection, rightDownCorner);
        rightTriangle = new Triangle(rightDownCorner, centerDirection, rightUpCorner);
        upTriangle = new Triangle(leftUpCorner, centerDirection, rightUpCorner);

        leftCachedPlanes = new ArrayList<>();
        downCachedPlanes = new ArrayList<>();
        rightCachedPlanes = new ArrayList<>();
        upCachedPlanes = new ArrayList<>();

        Vector3d cameraPosition = camera.getPosition();

        for (SceneObject object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (Triangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);

                Vector3d vertex0 = plane.getVertex0();
                Vector3d vertex1 = plane.getVertex1();
                Vector3d vertex2 = plane.getVertex2();

                boolean planeInLeft = false;
                boolean planeInDown = false;
                boolean planeInRight = false;
                boolean planeInUp = false;


                boolean vertex0Intersection = isVectorInTriangle(vertex0, leftTriangle);
                boolean vertex1Intersection = isVectorInTriangle(vertex1, leftTriangle);
                boolean vertex2Intersection = isVectorInTriangle(vertex2, leftTriangle);
                if (vertex0Intersection || vertex1Intersection || vertex2Intersection) {
                    leftCachedPlanes.add(plane);
                    planeInLeft = true;
                }

                vertex0Intersection = isVectorInTriangle(vertex0, downTriangle);
                vertex1Intersection = isVectorInTriangle(vertex1, downTriangle);
                vertex2Intersection = isVectorInTriangle(vertex2, downTriangle);
                if (vertex0Intersection || vertex1Intersection || vertex2Intersection){
                    downCachedPlanes.add(plane);
                    planeInDown = true;
                }

                vertex0Intersection = isVectorInTriangle(vertex0, rightTriangle);
                vertex1Intersection = isVectorInTriangle(vertex1, rightTriangle);
                vertex2Intersection = isVectorInTriangle(vertex2, rightTriangle);
                if (vertex0Intersection || vertex1Intersection || vertex2Intersection) {
                    rightCachedPlanes.add(plane);
                    planeInRight = true;
                }

                vertex0Intersection = isVectorInTriangle(vertex0, upTriangle);
                vertex1Intersection = isVectorInTriangle(vertex1, upTriangle);
                vertex2Intersection = isVectorInTriangle(vertex2, upTriangle);
                if (vertex0Intersection || vertex1Intersection || vertex2Intersection) {
                    upCachedPlanes.add(plane);
                    planeInUp = true;
                }

                if (planeInLeft && planeInRight){
                    if (!planeInDown) downCachedPlanes.add(plane);
                    if (!planeInUp) upCachedPlanes.add(plane);
                }
                else if (planeInDown && planeInUp){
                    if (!planeInLeft) leftCachedPlanes.add(plane);
                    if (!planeInRight) rightCachedPlanes.add(plane);
                }
            }
        }
    }

    private boolean isVectorInTriangle(Vector3d vector, Triangle triangle){
        return VectorUtils.triangleRayIntersection(vector, triangle) != null;
    }

/*    private boolean vectorInLeftDownTriangle(Vector3d vector){
        Vector3d intersection = VectorUtils.triangleRayIntersection(vector, leftDownTriangle);
        return intersection != null;
    }
    private boolean vectorInRightUpTriangle(Vector3d vector){
        Vector3d intersection = VectorUtils.triangleRayIntersection(vector, rightUpTriangle);
        return intersection != null;
    }*/

    public List<Triangle> getCachedPlanes(Vector3d direction) {
        if (isVectorInTriangle(direction, downTriangle)) return downCachedPlanes;
        if (isVectorInTriangle(direction, leftTriangle)) return leftCachedPlanes;
        if (isVectorInTriangle(direction, rightTriangle)) return rightCachedPlanes;
        return upCachedPlanes;
    }

}
