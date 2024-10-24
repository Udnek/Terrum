package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.graphic.engine.rasterization.RasterizationEngine;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.EulerPhysicEngine;
import me.udnekjupiter.physic.engine.RKMPhysicEngine;
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.net.SpringSphereNet;
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
        RKMPhysicEngine physicEngine = new RKMPhysicEngine(physicScene, initializePhysicsSettings());

        GraphicScene3d graphicScene = new GraphicScene3d(new Camera());
        GraphicEngine graphicEngine = new RasterizationEngine(graphicScene);


        //CellularNet launcherNet = new CellularNet("small_launcher.png", new Vector3d(15, 2, 0), launcherOffsets);
        SphereObject sphere = new SphereObject(4, 100_000);
        sphere.setPosition(new Vector3d(5, 5, 5));
        sphere.getContainer().initialPosition = new Vector3d(5, 5, 5);
        sphere.getContainer().mass = 1;
        physicEngine.addObject(sphere);

        //CellularNet basketNet = new CellularNet("medium_frame.png");
        //basketNet.initialize();
        //physicEngine.addObjects(basketNet.getNetObjects());
        //physicEngine.addObjects(SpringSphereNet.create(2, 2d/4d));


        graphicScene.tryRepresentingAsGraphic(physicEngine.getScene().getAllObjects());


        graphicScene.getCamera().setPosition(new Vector3d(4.98, 3.966, -4.187));
        graphicScene.getCamera().setPitch(21.42f);
        graphicScene.getCamera().setYaw(-0.7f);

        initializeApplication(graphicEngine, physicEngine);
        startApplication();

    }
}
