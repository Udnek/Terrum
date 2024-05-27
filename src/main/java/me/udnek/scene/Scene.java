package me.udnek.scene;


import me.udnek.objects.SceneObject;
import me.udnek.objects.TetrahedronObject;
import org.realityforge.vecmath.Vector3d;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Scene{

    private Camera camera;
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();

    public Scene(){
        camera = new Camera();

        sceneObjects.add(
                new TetrahedronObject(
                        //pos
                        new Vector3d(0, 0, 1),
                        //up
                        new Vector3d(0.5, 1, 0.5),
                        //bottom
                        new Vector3d(-1, -0.5, 1),
                        new Vector3d(0.6, 0, 0),
                        new Vector3d(-0.1, 0, 0)

                )
        );
        sceneObjects.add(
                new TetrahedronObject(
                        //pos
                        new Vector3d(0, 0, -1.1),
                        //up
                        new Vector3d(0.5, 1, 0.5),
                        //bottom
                        new Vector3d(-1, -0.5, 1),
                        new Vector3d(0.6, 0, 0),
                        new Vector3d(-0.1, 0, 0)

                )
        );
    }

    public BufferedImage renderFrame(int width, int height){

        RayTracer rayTracer = new RayTracer(sceneObjects);

        float xOffset = -width/2f;
        float yOffset = -height/2f;

        float fovMultiplayer = 0.05f;


        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                Vector3d direction = new Vector3d(
                        (i+xOffset) * fovMultiplayer,
                        (j+yOffset) * fovMultiplayer,
                        10
                );
                camera.rotateVector(direction);

                int color = rayTracer.rayTrace(camera.getPosition(), direction).getSuggestedColor();

                bufferedImage.setRGB(i, height-j-1, color);
            }
        }
        return bufferedImage;
    }

    public Camera getCamera(){
        return camera;
    }

}
