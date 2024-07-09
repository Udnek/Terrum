package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
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
        NetPhysicsScene physicScene = new NetPhysicsScene(new CellularNet("frame.png"));
        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
        //
        //GraphicScene3d graphicScene = new SmallGraphicScene();
        GraphicScene3d graphicScene = new NetGraphicScene(physicScene);
        GraphicEngine3d graphicEngine = new GraphicEngine3d(graphicScene);

        for (int i = 0; i < 5; i++) {
            SphereObject sphere = new SphereObject(new Vector3d(7,  6 + i*3, 7), 1.2, 1_000, 1);
            physicScene.addObject(sphere);
        }


        //Test.run();
        Main.runApplication(graphicEngine, physicEngine);


    }

    @Override
    public ApplicationSettings initializeGraphicsSettings() {
        ApplicationSettings applicationSettings;
        applicationSettings = ApplicationSettings.defaultNoRecording(4, 12, PolygonHolder.Type.SMART);
        applicationSettings.startWindowWidth = 700;
        applicationSettings.startWindowHeight = 700;
        //return ApplicationSettings.defaultWithRecording(64, 64, "evenMoreNewTest3", 2, PolygonHolder.Type.SMART);
        //return ApplicationSettings.withRecording(256, 256, "newTest", 1, PolygonHolder.Type.SMART, false, false);
        //return ApplicationSettings.noRecording(2, 6, PolygonHolder.Type.SMART, false, false);

        return applicationSettings;
    }

    @Override
    public EnvironmentSettings initializePhysicsSettings() {
        EnvironmentSettings environmentSettings = EnvironmentSettings.defaultPreset();
        environmentSettings.springStiffness = 300;
        environmentSettings.springRelaxedLength = 1;
        //environmentSettings.iterationsPerTick = 0;
        return environmentSettings;
    }


}
