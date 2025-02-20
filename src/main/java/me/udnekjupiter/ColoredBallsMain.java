package me.udnekjupiter;

import me.udnekjupiter.app.util.ApplicationSettings;
import me.udnekjupiter.app.window.opengl.GlWindow;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.graphic.engine.opengl.GlEngine;
import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.object.renderable.MassEssenceObject;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.collision.AABB;
import me.udnekjupiter.physic.collision.shape.SphereCollider;
import me.udnekjupiter.physic.engine.EulerPhysicEngine;
import me.udnekjupiter.physic.engine.PhysicEngine3d;
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.sphere.SphereObject;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import me.udnekjupiter.util.Vector3d;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ColoredBallsMain extends Main{
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
        environmentSettings.deltaTime = 0.0001;
        return environmentSettings;
    }

    @Override
    public void run() {

        PhysicScene3d physicScene = new PhysicScene3d();
        PhysicEngine3d physicEngine = new EulerPhysicEngine(physicScene, initializePhysicsSettings());
        GraphicScene3d graphicScene = new GraphicScene3d(new Camera());

        GlWindow window = new GlWindow();
        GraphicEngine graphicEngine = new GlEngine(window, graphicScene);


        for (int i = 0; i < 5; i++) {
            SphereObject sphereObject = new SphereObject(i+1, 1000);
            Vector3d position = new Vector3d(0, i * 5 + 2, 0);
            sphereObject.getContainer().position = position;
            sphereObject.getContainer().initialPosition = position.dup();
            sphereObject.getContainer().mass = (i+1) * 2;
            physicEngine.addObject(sphereObject);
        }
        SphereObject sphereObject = new SphereObject(5, 10000);
        Vector3d position = new Vector3d(0, 5, 0);
        sphereObject.getContainer().position = position;
        sphereObject.getContainer().initialPosition = position.dup();
        sphereObject.getContainer().mass = 4702;
        physicEngine.addObject(sphereObject);


        CellularNet basketNet = new CellularNet(
                "big_frame.png",
                new Vector3d(-5, -1, -5),
                50_000,
                0.8,
                0.7,
                0.8f);
        basketNet.initialize();
        physicEngine.addObjects(basketNet.getNetObjects());

        AABB testBoundingBox = new AABB(new Vector3d(0, 0,0), new Vector3d(1, 1, 1));
        SphereObject testSphere = new SphereObject(1, 1);
        testSphere.setPosition(new Vector3d(0, 1, 0));

        graphicScene.tryRepresentingAsGraphic(physicEngine.getScene().getAllObjects());

        for (@NotNull GraphicObject3d object : graphicScene.getObjects()) {
            color(object, new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f).getRGB());
        }
        
        graphicScene.getCamera().setPosition(new Vector3d(-0.056768052754865406, 2.6988419722616674, -4.659554678586445));
        graphicScene.getCamera().setYaw(-180);
        graphicScene.getCamera().setPitch(-33);

        initializeApplication(graphicEngine, physicEngine, window);
        startApplication();

        //        for (RenderableObject3d traceableObject : graphicScene.getTraceableObjects()) {
//            if (traceableObject instanceof MassEssenceObject massEssenceObject){
//                for (RenderableTriangle renderTriangle : massEssenceObject.getUnsafeRenderTriangles()) {
//                    ((ColoredTriangle) renderTriangle).setColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f).getRGB());
//                }
//            }
//        }



    }

    public void color(GraphicObject3d object3d, int color){
        if (!(object3d instanceof MassEssenceObject massEssenceObject)) return;
        for (RenderableTriangle renderTriangle : massEssenceObject.getUnsafeRenderTriangles()) {
            ((ColoredTriangle) renderTriangle).setColor(color);
        }
    }
}












