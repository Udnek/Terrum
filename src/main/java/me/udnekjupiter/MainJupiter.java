package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.scene.NetGraphicScene;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PrimitiveScenePhysicEngine;
import me.udnekjupiter.physic.object.SphereObject;
import me.udnekjupiter.physic.scene.NetPhysicsScene;
import org.realityforge.vecmath.Vector3d;

import java.security.spec.RSAOtherPrimeInfo;

public class MainJupiter extends Main{

    @Override
    public ApplicationSettings initializeGraphicsSettings() {
//        return ApplicationSettings.defaultWithRecording(512,
//                512,
//                "Default",
//                16,
//                PolygonHolder.Type.SMART);
      return ApplicationSettings.defaultNoRecording(4, 16, PolygonHolder.Type.SMART);
    }
    public EnvironmentSettings initializePhysicsSettings(){return EnvironmentSettings.defaultPreset();}

    @Override
    public void run() {
        NetPhysicsScene physicScene = new NetPhysicsScene("cloth.png");
        PrimitiveScenePhysicEngine physicEngine = new PrimitiveScenePhysicEngine(physicScene);
        SphereObject sphere = new SphereObject(new Vector3d(7, -6, 7), 5, 100);
        sphere.freeze();
        physicScene.addObject(sphere);
//        physicScene.addSphereObject(new Vector3d(3, 11, 3), 1.5, 50);

        NetGraphicScene graphicScene = new NetGraphicScene(physicScene);
        GraphicEngine3d graphicEngine = new GraphicEngine3d(graphicScene);
        Main.runApplication(graphicEngine, physicEngine);
        graphicScene.getCamera().setPosition(new Vector3d(7, 8, -7.5));
        graphicScene.getCamera().setPitch(40);
    }
}
