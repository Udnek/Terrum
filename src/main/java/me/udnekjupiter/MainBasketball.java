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
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.net.SpringSphereNet;
import me.udnekjupiter.physic.object.PlaneObject;
import me.udnekjupiter.physic.object.sphere.SphereObject;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import me.udnekjupiter.util.vector.Vector3d;
import org.jetbrains.annotations.NotNull;

public class MainBasketball extends Main{
    @Override
    public @NotNull ApplicationSettings initializeGraphicsSettings() {
        return ApplicationSettings.noRecording();
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

        //CellularNet launcherNet = new CellularNet("small_launcher.png", new Vector3d(15, 2, 0), launcherOffsets);
        SphereObject sphere = new SphereObject(4, 100_000);
        sphere.setPosition(new Vector3d(5, 5, 5));
        sphere.getContainer().initialPosition = new Vector3d(5, 5, 5);
        sphere.getContainer().mass = 100;
        physicEngine.addObject(sphere);

        PlaneObject plane = new PlaneObject(0, 1, 0, 3, 10_000);
        physicEngine.addObject(plane);


        CellularNet basketNet = new CellularNet("big_square.png");
        basketNet.initialize();
        physicEngine.addObjects(basketNet.getNetObjects());

        //637

        physicEngine.addObjects(SpringSphereNet.createFromCuboid(2, 2d/4d, 10_000));


        graphicScene.tryRepresentingAsGraphic(physicEngine.getScene().getAllObjects());


        graphicScene.getCamera().setPosition(new Vector3d(-0.5617638750838255, -0.773763021469116, -13.138238168168582));
        graphicScene.getCamera().setYaw(-1.1f);
        graphicScene.getCamera().setPitch(-12.0199995f);

        initializeApplication(graphicEngine, physicEngine, window);
        startApplication();

    }
}
