package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.graphic.engine.rasterization.RasterizationEngine;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.renderable.MassEssenceObject;
import me.udnekjupiter.graphic.object.renderable.RenderableObject3d;
import me.udnekjupiter.graphic.object.renderable.VertexObject;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.EulerPhysicEngine;
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.object.SphereObject;
import me.udnekjupiter.physic.object.container.PhysicVariableContainer;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import me.udnekjupiter.util.Vector3x3;
import org.jcodec.common.JCodecUtil;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainBasketball extends Main{
    @Override
    public @NotNull ApplicationSettings initializeGraphicsSettings() {
/*        return ApplicationSettings.defaultWithRecording(200,
                200,
                "тачдаун",
                16,
                PolygonHolder.Type.SMART);*/
        return ApplicationSettings.noRecording();
    }
    public @NotNull EnvironmentSettings initializePhysicsSettings(){return EnvironmentSettings.defaultPreset();}

    @Override
    public void run() {

        Vector3x3 besketOffsets = new Vector3x3(
                new Vector3d(-1, 0.5, 0),
                new Vector3d(),
                new Vector3d(0, 0, 1.1)
        );
        Vector3x3 launcherOffsets = new Vector3x3(
                new Vector3d(1, 0.5, 0),
                new Vector3d(),
                new Vector3d(0, 0, 1)
        );

        EnvironmentSettings settings = EnvironmentSettings.defaultPreset();
        PhysicScene3d physicScene = new PhysicScene3d();
        EulerPhysicEngine physicEngine = new EulerPhysicEngine(physicScene, settings);


        GraphicScene3d graphicScene = new GraphicScene3d() {


            @Override
            protected Camera initializeCamera() {
                return new Camera();
            }

            @Override
            protected List<RenderableObject3d> initializeSceneObjects() {
                List<RenderableObject3d> graphicObjects = new ArrayList<>();
                List<? extends PhysicObject3d> physicObjects = physicScene.getAllObjects();
                for (PhysicObject3d object : physicObjects) {
                    if (object instanceof SphereObject sphereObject) {
                        graphicObjects.add(new MassEssenceObject(sphereObject));
                    } else if (object instanceof NetVertex vertexObject) {
                        graphicObjects.add(new VertexObject(vertexObject.getPosition(), vertexObject));
                    }
                }
                return graphicObjects;
            }

            @Override
            protected LightSource initializeLightSource() {
                return null;
            }
        };
        GraphicEngine graphicEngine = new RasterizationEngine(graphicScene);

        CellularNet basketNet = new CellularNet("medium_basket.png", new Vector3d(0, 0, -6), besketOffsets);
        CellularNet launcherNet = new CellularNet("small_launcher.png", new Vector3d(15, 2, 0), launcherOffsets);
        SphereObject sphere = new SphereObject(2.5, 10_000);

        PhysicVariableContainer sphereContainer = new PhysicVariableContainer(new Vector3d());
        sphere.setContainer(sphereContainer);

        physicEngine.addObject(sphere);
//        physicEngine.addObjects(basketNet.getVerticesObjects());
//        physicEngine.addObjects(launcherNet.getVerticesObjects());

        runApplication(graphicEngine, physicEngine);

        graphicScene.getCamera().setPosition(new Vector3d(22, 8, -2));
        graphicScene.getCamera().setPitch(10);
        graphicScene.getCamera().setYaw(50);
    }
}
