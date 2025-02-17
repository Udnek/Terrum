/*
package me.udnekjupiter.graphic.scene;

import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.light.PointLight;
import me.udnekjupiter.graphic.object.renderable.RenderableObject3d;
import me.udnekjupiter.graphic.object.renderable.shape.IcosphereObject;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class LightTestGraphicScene extends GraphicScene3d {
    @Override
    protected Camera initializeCamera() {
        Camera camera = new Camera(new Vector3d(0, 0.3, 0), 356, 8);
        return camera;
    }

    @Override
    protected List<RenderableObject3d> initializeSceneObjects() {
        List<RenderableObject3d> graphicObjects = new ArrayList<>();
        graphicObjects.add(
                new IcosphereObject(
                        new Vector3d(0, 0, 2),
                        1,
                        2,
                        RenderableTriangle.empty()
                        )
                );
        graphicObjects.add(lightSource);

        return graphicObjects;
    }

    @Override
    protected LightSource initializeLightSource() {
        return new PointLight(new Vector3d(1, 2, 1));
    }
}
*/
