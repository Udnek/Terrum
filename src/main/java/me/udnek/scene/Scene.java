package me.udnek.scene;


import me.udnek.objects.PolygonObject;
import me.udnek.objects.SceneObject;
import me.udnek.utils.Triangle;
import org.realityforge.vecmath.Vector3d;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Scene{

    private Camera camera;
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();


    public Scene(){
        camera = new Camera();
        //sceneObjects.add(new PointObject(0.2, 0, 0.5));
        //sceneObjects.add(new PointObject(-0.5, 0.5, 0.5));
        //sceneObjects.add(new PointObject(1, 1, 0.5));
        //sceneObjects.add(new PointObject(0, 1, 0.5));
        //sceneObjects.add(new PointObject(3, 0, 0.5));
        sceneObjects.add(
                new PolygonObject(
                        new Vector3d(0, 0, 0),
                        new Triangle(
                                new Vector3d(0, 0, 0.3),
                                new Vector3d(0, 1, 0.3),
                                new Vector3d(2, 0, 0.3)
                        )
                )
        );
    }

    public BufferedImage renderFrame(int width, int height){

        RayTracer rayTracer = new RayTracer(camera.getPosition(), sceneObjects);

        Vector3d cameraDirection = camera.getDirection();

        float xOffset = -width/2f;
        float yOffset = -height/2f;


        float fovMultiplayer = 10f;
        fovMultiplayer = fovMultiplayer/width;

        fovMultiplayer = 0.05f;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {

                Vector3d direction = new Vector3d(
                        (i+xOffset) * fovMultiplayer,
                        (j+yOffset) * fovMultiplayer,
                        -1
                );

                int color = rayTracer.rayCast(direction);
                bufferedImage.setRGB(i, j, color);
            }
        }
        return bufferedImage;
    }
}
