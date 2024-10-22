package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.physic.EnvironmentSettings;
import org.jetbrains.annotations.NotNull;

public class MainJupiter extends Main{

    @Override
    public @NotNull ApplicationSettings initializeGraphicsSettings() {
//        return ApplicationSettings.defaultWithRecording(512,
//                512,
//                "Default",
//                16,
//                PolygonHolder.Type.SMART);
      return ApplicationSettings.noRecording();
    }
    public @NotNull EnvironmentSettings initializePhysicsSettings(){return EnvironmentSettings.defaultPreset();}

    @Override
    public void run() {

/*        Vector3x3 offset0 = new Vector3x3(
                new Vector3d(-1, 0.5, 0),
                new Vector3d(),
                new Vector3d(0, 0, 1)
        );
        Vector3x3 offset1 = new Vector3x3(
                new Vector3d(1, 0.5, 0),
                new Vector3d(),
                new Vector3d(0, 0, 1)
        );

        CellularNet net0 = new CellularNet("basket.png", new Vector3d(0,0,-3), offset0);
        CellularNet net1 = new CellularNet("small_launcher.png", new Vector3d(15, 2, 0), offset1);

        NetPhysicsScene physicScene = new NetPhysicsScene(net0, net1);
        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
//        SphereObject sphere = new SphereObject(new Vector3d(16, 7, 3), 2.5, 500);
//        physicScene.addObject(sphere);
//        physicScene.addSphereObject(new Vector3d(3, 11, 3), 1.5, 50);

        NetGraphicScene graphicScene = new NetGraphicScene(physicScene);
        RayTracingEngine graphicEngine = new RayTracingEngine(graphicScene);

        runApplication(graphicEngine, physicEngine);

        graphicScene.getCamera().setPosition(new Vector3d(7, 8, -7.5));
        graphicScene.getCamera().setPitch(20);*/
    }
}
