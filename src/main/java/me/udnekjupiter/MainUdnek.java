package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.rasterization.RasterizationEngine;
import me.udnekjupiter.graphic.engine.raytrace.KernelRayTracingEngine;
import me.udnekjupiter.graphic.engine.raytrace.RayTracingEngine;
import me.udnekjupiter.graphic.object.renderable.MassEssenceObject;
import me.udnekjupiter.graphic.object.renderable.RenderableObject3d;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.graphic.scene.NetGraphicScene;
import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PrimitiveScenePhysicEngine;
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.SphereObject;
import me.udnekjupiter.physic.scene.NetPhysicsScene;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public class MainUdnek extends Main{

    @Override
    public void run() {

        NetPhysicsScene physicScene = new NetPhysicsScene(
                new CellularNet("medium_frame.png",
                        new Vector3d(0, 0, 0)));


/*        SphereObject sphere = new SphereObject(new Vector3d(3, 6, 3), 2, 10_000, 5);
        physicScene.addObject(sphere);*/

        SphereObject sphere = new SphereObject(new Vector3d(5, 11, 5), 1, 100_000, 150);
        physicScene.addObject(sphere);
        sphere = new SphereObject(new Vector3d(5, 15, 5), 1, 100_000, 150);
        physicScene.addObject(sphere);
        sphere = new SphereObject(new Vector3d(5, 19, 5), 1, 100_000, 150);
        physicScene.addObject(sphere);
/*        sphere = new SphereObject(new Vector3d(5, 23, 5), 4, 10_000, 150);
        physicScene.addObject(sphere);
        sphere = new SphereObject(new Vector3d(5, 27, 5), 4, 10_000, 150);
        physicScene.addObject(sphere);*/

        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
        //
        //GraphicScene3d graphicScene = new SmallGraphicScene();
        GraphicScene3d graphicScene = new NetGraphicScene(physicScene);

        RayTracingEngine rayTracing = new RayTracingEngine(graphicScene);
        KernelRayTracingEngine kernel = new KernelRayTracingEngine(graphicScene);
        RasterizationEngine rasterizer = new RasterizationEngine(graphicScene);


        runApplication(rasterizer, physicEngine);

        graphicScene.getCamera().setPosition(new Vector3d(2, 6, -5));
        graphicScene.getCamera().setYaw(-31.7f);
        graphicScene.getCamera().setPitch(24);


        for (RenderableObject3d traceableObject : graphicScene.getTraceableObjects()) {
            if (traceableObject instanceof MassEssenceObject massEssenceObject){
                for (RenderableTriangle renderTriangle : massEssenceObject.getUnsafeRenderTriangles()) {
                    ((ColoredTriangle) renderTriangle).setColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f).getRGB());
                }
            }
        }
    }

    @Override
    public @NotNull ApplicationSettings initializeGraphicsSettings() {
        ApplicationSettings settings = ApplicationSettings.noRecording();
        settings.pixelScaling = 1;
/*        settings.pixelScaling = 60;
        settings.startWindowWidth = 10 * settings.pixelScaling;
        settings.startWindowHeight = 10 * settings.pixelScaling;*/
        return settings;
        //return ApplicationSettings.defaultWithRecording(64, 64, "evenMoreNewTest3", 2, PolygonHolder.Type.SMART);
        //return ApplicationSettings.withRecording(512, 512, "newEngineTest", 1, PolygonHolder.Type.SMART, false, false);
        //return ApplicationSettings.noRecording(2, 6, PolygonHolder.Type.SMART, false, false);
    }

    @Override
    public @NotNull EnvironmentSettings initializePhysicsSettings() {
        EnvironmentSettings environmentSettings = EnvironmentSettings.defaultPreset();
        environmentSettings.iterationsPerTick = 200;
        return environmentSettings;
    }


}
