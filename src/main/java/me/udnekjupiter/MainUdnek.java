package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.rasterization.RasterizationEngine;
import me.udnekjupiter.graphic.engine.raytrace.KernelRayTracingEngine;
import me.udnekjupiter.graphic.engine.raytrace.RayTracingEngine;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.graphic.scene.NetGraphicScene;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PrimitiveScenePhysicEngine;
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.SphereObject;
import me.udnekjupiter.physic.scene.NetPhysicsScene;
import org.realityforge.vecmath.Vector3d;

public class MainUdnek extends Main{

    @Override
    public void run() {
        NetPhysicsScene physicScene = new NetPhysicsScene(
                new CellularNet("medium_frame.png",
                        new Vector3d(0, 0, 0)));
        SphereObject sphere = new SphereObject(new Vector3d(3, 6, 3), 2.5, 10_000, 100);
        physicScene.addObject(sphere);


        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
        //
        //GraphicScene3d graphicScene = new SmallGraphicScene();
        GraphicScene3d graphicScene = new NetGraphicScene(physicScene);


        RayTracingEngine def = new RayTracingEngine(graphicScene);
        KernelRayTracingEngine gpu = new KernelRayTracingEngine(graphicScene);
        RasterizationEngine rasterizer = new RasterizationEngine(graphicScene);


        Main.runApplication(rasterizer, physicEngine);

        graphicScene.getCamera().setPosition(new Vector3d(2, 6, -5));
        graphicScene.getCamera().setYaw(-31.7f);
        graphicScene.getCamera().setPitch(24);
    }

    @Override
    public ApplicationSettings initializeGraphicsSettings() {
        return ApplicationSettings.defaultNoRecording(1, 2, PolygonHolder.Type.SMART);
        //return ApplicationSettings.defaultWithRecording(64, 64, "evenMoreNewTest3", 2, PolygonHolder.Type.SMART);
        //return ApplicationSettings.withRecording(512, 512, "newEngineTest", 1, PolygonHolder.Type.SMART, false, false);
        //return ApplicationSettings.noRecording(2, 6, PolygonHolder.Type.SMART, false, false);
    }

    @Override
    public EnvironmentSettings initializePhysicsSettings() {
        EnvironmentSettings environmentSettings = EnvironmentSettings.defaultPreset();
        environmentSettings.iterationsPerTick = 200;
        return environmentSettings;
    }


}
