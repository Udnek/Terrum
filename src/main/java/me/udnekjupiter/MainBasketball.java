package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.graphic.engine.rasterization.RasterizationEngine;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.container.PhysicVariableContainer;
import me.udnekjupiter.physic.engine.EulerPhysicEngine;
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.SphereObject;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

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
    public @NotNull EnvironmentSettings initializePhysicsSettings(){
        EnvironmentSettings environmentSettings = EnvironmentSettings.defaultPreset();
        environmentSettings.iterationsPerTick = 200;
        return environmentSettings;
    }

    @Override
    public void run() {

        PhysicScene3d physicScene = new PhysicScene3d();
        EulerPhysicEngine physicEngine = new EulerPhysicEngine(physicScene, initializePhysicsSettings());

        GraphicScene3d graphicScene = new GraphicScene3d(new Camera());
        GraphicEngine graphicEngine = new RasterizationEngine(graphicScene);

        CellularNet basketNet = new CellularNet("medium_basket.png");
        //CellularNet launcherNet = new CellularNet("small_launcher.png", new Vector3d(15, 2, 0), launcherOffsets);
        SphereObject sphere = new SphereObject(2.5, 10_000);
        sphere.setContainer(new PhysicVariableContainer(new Vector3d()));
        physicEngine.addObject(sphere);

        basketNet.initialize();
        physicEngine.addObjects(basketNet.getNetObjects());

        graphicScene.tryRepresentingAsGraphic(physicEngine.getScene().getAllObjects());



        graphicScene.getCamera().setPosition(new Vector3d(0, 0, 0));
        graphicScene.getCamera().setPitch(10);
        graphicScene.getCamera().setYaw(50);



        initializeApplication(graphicEngine, physicEngine);
        startApplication();

    }
}
