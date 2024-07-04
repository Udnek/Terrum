package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.scene.NetGraphicScene;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PrimitiveScenePhysicEngine;
import me.udnekjupiter.physic.scene.PhysicsScene;

public class MainUdnek extends Main{

    @Override
    public void run() {
        PhysicsScene physicScene = new PhysicsScene();
        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
        NetGraphicScene graphicScene = new NetGraphicScene(physicScene);
        GraphicEngine3d graphicEngine = new GraphicEngine3d(graphicScene);

        Main.runApplication(graphicEngine, physicEngine);

        physicScene.setInitialDeviation(1, 1, 1, 0.5, 0);
    }

    @Override
    public ApplicationSettings initializeGraphicsSettings() {
        return ApplicationSettings.defaultNoRecording(2, 2, PolygonHolder.Type.SMART);
        //return ApplicationSettings.defaultWithRecording(256, 256, "evenMoreNewTest", 2, PolygonHolder.Type.SMART);
        //return ApplicationSettings.withRecording(256, 256, "newTest", 1, PolygonHolder.Type.SMART, false, false);
        //return ApplicationSettings.noRecording(2, 6, PolygonHolder.Type.SMART, false, false);
    }

    @Override
    public EnvironmentSettings initializePhysicsSettings() {
        return EnvironmentSettings.defaultPreset();
    }


}
