package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.graphic.scene.NetGraphicScene;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PrimitiveScenePhysicEngine;
import me.udnekjupiter.physic.scene.NetPhysicsScene;
import org.realityforge.vecmath.Vector3d;

public class MainUdnek extends Main{

    @Override
    public void run() {
        NetPhysicsScene physicScene = new NetPhysicsScene("small_frame.png");
        physicScene.addMassEssence(new Vector3d(2, 0, 10), 2, 75);

        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
        //
        //GraphicScene3d graphicScene = new SmallGraphicScene();
        GraphicScene3d graphicScene = new NetGraphicScene(physicScene);


        GraphicEngine3d def = new GraphicEngine3d(graphicScene);
        GPUGraphicEngine gpu = new GPUGraphicEngine(graphicScene);


        Main.runApplication(gpu, physicEngine);
    }

    @Override
    public ApplicationSettings initializeGraphicsSettings() {
        return ApplicationSettings.defaultNoRecording(4, 6, PolygonHolder.Type.SMART);
        //return ApplicationSettings.defaultWithRecording(64, 64, "evenMoreNewTest3", 2, PolygonHolder.Type.SMART);
        //return ApplicationSettings.withRecording(256, 256, "newTest", 1, PolygonHolder.Type.SMART, false, false);
        //return ApplicationSettings.noRecording(2, 6, PolygonHolder.Type.SMART, false, false);
    }

    @Override
    public EnvironmentSettings initializePhysicsSettings() {
        EnvironmentSettings environmentSettings = EnvironmentSettings.defaultPreset();
        environmentSettings.iterationsPerTick = 0;
        return environmentSettings;
    }


}
