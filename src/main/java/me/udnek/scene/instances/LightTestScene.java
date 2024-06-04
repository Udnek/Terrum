package me.udnek.scene.instances;

import me.udnek.object.SceneObject;
import me.udnek.object.light.LightSource;
import me.udnek.object.light.PointLight;
import me.udnek.object.shape.IcosphereObject;
import me.udnek.scene.Camera;
import me.udnek.scene.Scene;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class LightTestScene extends Scene {
    @Override
    protected Camera initCamera() {
/*        Camera camera = new Camera(new Vector3d(0.6, 1.3, 0));
        camera.rotatePitch(10);*/
        Camera camera = new Camera(new Vector3d(0, 0.3, 0), 356, 8);
        return camera;
    }

    @Override
    protected List<SceneObject> initSceneObjects() {
        List<SceneObject> sceneObjects = new ArrayList<>();
        sceneObjects.add(new IcosphereObject(new Vector3d(0, 0, 2), 1));
        sceneObjects.add(lightSource);

        return sceneObjects;
    }

    @Override
    protected LightSource initLightSource() {
        return new PointLight(new Vector3d(1, 2, 1));
    }

    @Override
    public void tick() {}

    @Override
    public boolean doLight() {
        return false;
    }
}
