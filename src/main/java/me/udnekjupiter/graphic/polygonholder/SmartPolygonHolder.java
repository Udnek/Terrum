package me.udnekjupiter.graphic.polygonholder;

import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.renderable.RenderableObject3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.Triangle;
import me.udnekjupiter.util.VectorUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class SmartPolygonHolder implements PolygonHolder{
    private List<RenderableObject3d> objectsToRender;
    private Camera camera;

    private List<RenderableTriangle> leftCachedPlanes;
    private List<RenderableTriangle> downCachedPlanes;
    private List<RenderableTriangle> rightCachedPlanes;
    private List<RenderableTriangle> upCachedPlanes;

    private Triangle leftTriangle;
    private Triangle downTriangle;
    private Triangle rightTriangle;
    private Triangle upTriangle;

    private List<RenderableTriangle> lightCachedPlanes = new ArrayList<>();

    public SmartPolygonHolder(List<RenderableObject3d> objectsToRender, Camera camera){
        this.objectsToRender = objectsToRender;
        this.camera = camera;
    }

    public void recacheObjects(int width, int height){

        double fovMultiplier = height/camera.getFov();

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

        for (RenderableObject3d object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (RenderableTriangle plane: object.getRenderTriangles()) {
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

    private boolean isVectorInTriangle(@NotNull Vector3d vector, @NotNull Triangle triangle){
        return VectorUtils.triangleRayIntersection(vector, triangle) != null;
    }

    public @NotNull List<RenderableTriangle> getCachedPlanes(@NotNull Vector3d direction) {
        if (isVectorInTriangle(direction, downTriangle)) return downCachedPlanes;
        if (isVectorInTriangle(direction, leftTriangle)) return leftCachedPlanes;
        if (isVectorInTriangle(direction, rightTriangle)) return rightCachedPlanes;
        return upCachedPlanes;
    }

    @Override
    public @NotNull List<RenderableTriangle> getLightCachedPlanes(@Nullable Vector3d direction) {
        return lightCachedPlanes;
    }
}
