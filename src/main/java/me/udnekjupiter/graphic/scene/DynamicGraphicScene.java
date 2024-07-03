package me.udnekjupiter.graphic.scene;

import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.AxisCrosshairObject;
import me.udnekjupiter.graphic.object.GraphicObject;
import me.udnekjupiter.graphic.object.SpringObject;
import me.udnekjupiter.graphic.object.VertexObject;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.light.PointLight;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class DynamicGraphicScene extends GraphicScene3d {

    private VertexObject vertex0;
    private VertexObject vertex1;
    private SpringObject spring;
    private int tick = 0;

    @Override
    protected Camera initializeCamera() {
        Camera camera = new Camera(new Vector3d(0.001, 0.01, 0.001));
        camera.rotateYaw(-45);
        return camera;
    }


    @Override
    public void beforeFrameUpdate(int width, int height) {
        tick++;

        vertex0.move(0, Math.sin(tick/20.0)/20.0, 0);
        vertex1.move(0, Math.cos(tick/30.0)/30.0, 0);
        spring.update();
    }
    @Override
    protected LightSource initializeLightSource() {
        return new PointLight(new Vector3d(1, 2, 1));
    }

    @Override
    protected List<GraphicObject> initializeSceneObjects() {
        List<GraphicObject> graphicObjects = new ArrayList<>();

        vertex0 = new VertexObject(new Vector3d(-1, 0, 3), null);
        vertex1 = new VertexObject(new Vector3d(1, 0, 3), null);
        spring = new SpringObject(new Vector3d(), vertex0, vertex1);

        graphicObjects.add(vertex0);
        graphicObjects.add(vertex1);
        graphicObjects.add(spring);
        graphicObjects.add(new AxisCrosshairObject());
        graphicObjects.add(lightSource);


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

        return graphicObjects;
    }
}