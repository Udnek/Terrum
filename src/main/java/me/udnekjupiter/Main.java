package me.udnekjupiter;


import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.scene.NetGraphicScene;
import me.udnekjupiter.physic.engine.PhysicEngine;
import me.udnekjupiter.physic.engine.PrimitiveScenePhysicEngine;
import me.udnekjupiter.physic.scene.NetPhysicScene;
import me.udnekjupiter.test.Test;

public class Main{
    public static void main(String ...args){
        NetPhysicScene physicScene = new NetPhysicScene();
        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
        NetGraphicScene graphicScene = new NetGraphicScene(physicScene);
        GraphicEngine3d graphicEngine = new GraphicEngine3d(graphicScene);

        runApplication(graphicEngine, physicEngine);

        physicScene.setInitialDeviation(1, 1, 1, 0.5, 0);
    }

    public static ApplicationSettings initSettings(){
        return ApplicationSettings.defaultNoRecording(2, 6, PolygonHolder.Type.SMART);
        //return ApplicationSettings.withRecording(256, 256, "newTest", 1, PolygonHolder.Type.SMART, false, false);
        //return ApplicationSettings.noRecording(2, 6, PolygonHolder.Type.SMART, false, false);
    }

    public static void runApplication(GraphicEngine graphicEngine, PhysicEngine physicEngine){
        Application application = Application.getInstance();
        application.initialize(graphicEngine, physicEngine);
        application.start();
    }

    public static void test(){
        Test.run();
    }
}