package me.udnek.scene.example;

import me.udnek.objects.AxisCrosshairObject;
import me.udnek.objects.SceneObject;
import me.udnek.objects.SpringObject;
import me.udnek.objects.VertexObject;
import me.udnek.objects.light.LightSource;
import me.udnek.objects.light.PointLight;
import me.udnek.scene.Camera;
import me.udnek.scene.Scene;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class DynamicScene extends Scene {

    private VertexObject vertex0;
    private VertexObject vertex1;
    private SpringObject spring;
    private int tick = 0;

    @Override
    protected Camera initCamera() {
        return new Camera(new Vector3d(0.1, 0.1, 0.1));
    }

    @Override
    protected LightSource initLightSource() {
        return new PointLight(new Vector3d(1, 2, 1));
    }

    @Override
    public void tick() {
        tick++;

        vertex0.move(0, Math.sin(tick/20.0)/20.0, 0);
        vertex1.move(0, Math.cos(tick/30.0)/30.0, 0);
        spring.recalculateTips();
    }

    @Override
    public boolean doLight() {
        return false;
    }

    @Override
    protected List<SceneObject> initSceneObjects() {
        List<SceneObject> sceneObjects = new ArrayList<>();

        vertex0 = new VertexObject(new Vector3d(-1, 0, 3), null);
        vertex1 = new VertexObject(new Vector3d(1, 0, 3), null);
        spring = new SpringObject(new Vector3d(), vertex0, vertex1);

        sceneObjects.add(vertex0);
        sceneObjects.add(vertex1);
        sceneObjects.add(spring);
        sceneObjects.add(new AxisCrosshairObject());
        sceneObjects.add(lightSource);


/*        sceneObjects.add(
                new PyramidObject(
                        //pos
                        new Vector3d(0, 0, 1),
                        //up
                        new Vector3d(0.5, 1, 0.5),
                        //bottom
                        new Vector3d(-1, -0.5, 1),
                        new Vector3d(0.6, 0, 0),
                        new Vector3d(-0.1, 0, 0)

                )
        );
        sceneObjects.add(
                new PyramidObject(
                        //pos
                        new Vector3d(0, 0, -1.1),
                        //up
                        new Vector3d(0.5, 1, 0.5),
                        //bottom
                        new Vector3d(-1, -0.5, 1),
                        new Vector3d(0.6, 0, 0),
                        new Vector3d(-0.1, 0, 0)
                )
        );*/

        return sceneObjects;
    }

}
