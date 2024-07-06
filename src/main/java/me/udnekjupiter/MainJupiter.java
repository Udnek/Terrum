package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.scene.NetGraphicScene;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PrimitiveScenePhysicEngine;
import me.udnekjupiter.physic.scene.NetPhysicsScene;

public class MainJupiter extends Main{

    @Override
    public ApplicationSettings initializeGraphicsSettings() {
//        return ApplicationSettings.defaultWithRecording(512,
//                512,
//                "Default",
//                12,
//                PolygonHolder.Type.SMART);
        return ApplicationSettings.defaultNoRecording(2, 12, PolygonHolder.Type.SMART);
    }
    public EnvironmentSettings initializePhysicsSettings(){return EnvironmentSettings.defaultPreset();}

    @Override
    public void run() {
        NetPhysicsScene physicScene = new NetPhysicsScene("frame.png");
        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
        NetGraphicScene graphicScene = new NetGraphicScene(physicScene);
        GraphicEngine3d graphicEngine = new GraphicEngine3d(graphicScene);

        Main.runApplication(graphicEngine, physicEngine);
    }
}
