package me.udnekjupiter;

import me.udnekjupiter.app.util.ApplicationSettings;
import me.udnekjupiter.app.window.opengl.GlWindow;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.graphic.engine.opengl.GlEngine;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.EulerPhysicEngine;
import me.udnekjupiter.physic.engine.PhysicEngine3d;
import me.udnekjupiter.physic.net.SpringSphereNet;
import me.udnekjupiter.physic.object.PlaneObject;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import me.udnekjupiter.util.vector.Vector3d;
import org.jetbrains.annotations.NotNull;

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

        GlWindow window = new GlWindow();
        GraphicEngine graphicEngine = new GlEngine(window, graphicScene);


        physicEngine.addObjects(SpringSphereNet.createFromCuboid(new Vector3d(0, 1, 0), 2d, 2d/4d, 1000));

        PlaneObject plane = new PlaneObject(0, 1, 0, 2, 100_000);
        physicEngine.addObject(plane);

/*        CellularNet basketNet = new CellularNet("flex.png");
        basketNet.initialize();
        physicEngine.addObjects(basketNet.getNetObjects());*/


        graphicScene.tryRepresentingAsGraphic(physicEngine.getScene().getAllObjects());

        graphicScene.getCamera().setPosition(new Vector3d(-0.056768052754865406, 2.6988419722616674, -4.659554678586445));
        graphicScene.getCamera().setYaw(0);
        graphicScene.getCamera().setPitch(33);

        initializeApplication(graphicEngine, physicEngine, window);
        startApplication();

    }
}
