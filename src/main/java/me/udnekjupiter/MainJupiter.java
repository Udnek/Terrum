package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.scene.NetGraphicScene;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PrimitiveScenePhysicEngine;
import me.udnekjupiter.physic.scene.NetPhysicScene;

public class MainJupiter extends Main{

    @Override
    public ApplicationSettings initializeGraphicsSettings(){return ApplicationSettings.defaultNoRecording(2, 6, PolygonHolder.Type.SMART);}
    public EnvironmentSettings initializePhysicsSettings(){return EnvironmentSettings.defaultPreset();}

    @Override
    public void run() {
        NetPhysicScene physicScene = new NetPhysicScene();
        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
        NetGraphicScene graphicScene = new NetGraphicScene(physicScene);
        GraphicEngine3d graphicEngine = new GraphicEngine3d(graphicScene);

        Main.runApplication(graphicEngine, physicEngine);

        //physicScene.setInitialDeviation(1, 1, 1, 0.5, 0);
    }
}
