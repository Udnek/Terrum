package me.udnekjupiter;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.graphic.engine.rasterization.RasterizationEngine;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.EulerPhysicEngine;
import me.udnekjupiter.physic.engine.PhysicEngine3d;
import me.udnekjupiter.physic.net.SpringSphereNet;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.Vector3d;

public class BallMain extends Main{
    @Override
    public @NotNull ApplicationSettings initializeGraphicsSettings() {
/*        ApplicationSettings applicationSettings = ApplicationSettings.withRecording(720, 720, "Ball2");
        applicationSettings.drawWireframe = false;
        return applicationSettings;*/
        //return ApplicationSettings.withRecording(512, 512, "Collapse2");
        ApplicationSettings applicationSettings = ApplicationSettings.noRecording();
        //applicationSettings.pixelScaling = 4;
        return applicationSettings;
    }
    public @NotNull EnvironmentSettings initializePhysicsSettings(){
        EnvironmentSettings environmentSettings = EnvironmentSettings.defaultPreset();
        environmentSettings.iterationsPerTick = 200;
        environmentSettings.decayCoefficient = 0.3;
        return environmentSettings;
    }

    @Override
    public void run() {

        PhysicScene3d physicScene = new PhysicScene3d();
        PhysicEngine3d physicEngine = new EulerPhysicEngine(physicScene, initializePhysicsSettings());
        GraphicScene3d graphicScene = new GraphicScene3d(new Camera());
        GraphicEngine graphicEngine = new RasterizationEngine(graphicScene);


        physicEngine.addObjects(SpringSphereNet.createFromCuboid(2d, 2d/4d, 5000));


        graphicScene.tryRepresentingAsGraphic(physicEngine.getScene().getAllObjects());


        graphicScene.getCamera().setPosition(new Vector3d(-0.056768052754865406, 2.6988419722616674, -4.659554678586445));
        graphicScene.getCamera().setYaw(0);
        graphicScene.getCamera().setPitch(33);

        initializeApplication(graphicEngine, physicEngine);
        startApplication();

    }
}
