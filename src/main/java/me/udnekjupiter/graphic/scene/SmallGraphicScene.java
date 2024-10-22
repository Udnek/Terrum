package me.udnekjupiter.graphic.scene;

import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.renderable.PlaneObject;
import me.udnekjupiter.graphic.object.renderable.RenderableObject3d;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class SmallGraphicScene extends GraphicScene3d{
    @Override
    protected Camera initializeCamera() {
        return new Camera(new Vector3d(0, 4, 0), 0, 90);
    }

    @Override
    protected List<RenderableObject3d> initializeSceneObjects() {
        List<RenderableObject3d> objects = new ArrayList<>();
        PlaneObject planeObject = new PlaneObject(new Vector3d(), -1, -1, 1, 1, 0);
        objects.add(planeObject);
        return objects;
    }

    @Override
    protected LightSource initializeLightSource() {
        return null;
    }
}
