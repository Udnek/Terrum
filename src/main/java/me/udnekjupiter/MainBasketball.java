package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.raytrace.RayTracingEngine;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.scene.NetGraphicScene;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PrimitiveScenePhysicEngine;
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.SphereObject;
import me.udnekjupiter.physic.scene.NetPhysicsScene;
import me.udnekjupiter.util.Vector3x3;
import org.realityforge.vecmath.Vector3d;

public class MainBasketball extends Main{
    @Override
    public ApplicationSettings initializeGraphicsSettings() {
/*        return ApplicationSettings.defaultWithRecording(200,
                200,
                "тачдаун",
                16,
                PolygonHolder.Type.SMART);*/
        return ApplicationSettings.defaultNoRecording(4, 16, PolygonHolder.Type.SMART);
    }
    public EnvironmentSettings initializePhysicsSettings(){return EnvironmentSettings.defaultPreset();}

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

        CellularNet basketNet = new CellularNet("medium_basket.png", new Vector3d(0, 0, -6), besketOffsets);
        CellularNet launcherNet = new CellularNet("small_launcher.png", new Vector3d(15, 2, 0), launcherOffsets);

        NetPhysicsScene physicScene = new NetPhysicsScene(basketNet, launcherNet);
        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
        SphereObject sphere = new SphereObject(new Vector3d(16, 7, 3), 2.5, 10_000, 500);
        physicScene.addObject(sphere);

        NetGraphicScene graphicScene = new NetGraphicScene(physicScene);
        RayTracingEngine graphicEngine = new RayTracingEngine(graphicScene);

        Main.runApplication(graphicEngine, physicEngine);

        graphicScene.getCamera().setPosition(new Vector3d(22, 8, -2));
        graphicScene.getCamera().setPitch(10);
        graphicScene.getCamera().setYaw(50);
    }
}
