package me.udnek.scene.polygonholder;

import me.udnek.object.SceneObject;
import me.udnek.scene.Camera;
import me.udnek.util.Triangle;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class DefaultPolygonHolder implements PolygonHolder{

    private Camera camera;
    private List<? extends SceneObject> objectsToRender;
    private List<Triangle> cachedPlanes;

    public DefaultPolygonHolder(List<? extends SceneObject> objectsToRender, Camera camera){
        this.camera = camera;
        this.objectsToRender = objectsToRender;
    }

    @Override
    public void recacheObjects(int width, int height) {
        Vector3d cameraPosition = camera.getPosition();
        cachedPlanes = new ArrayList<>();

        for (SceneObject object : objectsToRender) {
            Vector3d objectPosition = object.getPosition();
            for (Triangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);
                cachedPlanes.add(plane);
            }
        }
    }

    @Override
    public List<Triangle> getCachedPlanes(Vector3d direction) {
        return cachedPlanes;
    }
}
