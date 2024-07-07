package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.physic.EnvironmentSettings;

public class MainUdnek extends Main{

    @Override
    public void run() {
/*
        NetPhysicsScene physicScene = new NetPhysicsScene("small_frame.png");
        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
        //
        //GraphicScene3d graphicScene = new SmallGraphicScene();
        GraphicScene3d graphicScene = new NetGraphicScene(physicScene);
        GraphicEngine3d graphicEngine = new GraphicEngine3d(graphicScene);
*/

        //Test.run();
        Main.runApplication(new GPUGraphicEngine(), new EmptyPhysicEngine());
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
        return EnvironmentSettings.defaultPreset();
    }


}
