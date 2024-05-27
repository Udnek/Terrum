package me.udnek.scene;


import me.udnek.objects.SceneObject;
import me.udnek.objects.TetrahedronObject;
import org.realityforge.vecmath.Vector3d;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Scene{

    private Camera camera;
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();

    @Deprecated
    private float rotation = 0.0f;

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

        //Vector3d cameraDirection = camera.getDirection();

        //sceneObjects.get(0).move(new Vector3d(0.00, 0.01, 0));
        //sceneObjects.get(0).move(new Vector3d(0, 0, -0.03));
        //System.out.println(sceneObjects.get(0).getPosition().asString());
        //System.out.println(sceneObjects.get(0).getRenderTriangles()[0].asString());

        float xOffset = -width/2f;
        float yOffset = -height/2f;

        float fovMultiplayer = 0.05f;

        camera.move(new Vector3d(0, 0, 0.1));


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



}
