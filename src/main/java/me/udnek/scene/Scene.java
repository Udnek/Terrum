package me.udnek.scene;


import me.udnek.objects.IcosphereObject;
import me.udnek.objects.SceneObject;
import org.realityforge.vecmath.Vector3d;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Scene{

    private Camera camera;
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();
    private RayTracer rayTracer;

    public Scene(){
        camera = new Camera();

        sceneObjects.add(new IcosphereObject(new Vector3d(0, 0, 3), 2.3));


        //sceneObjects.add(new IcosahedronObject(new Vector3d(0, 0, 5), 1));

/*        sceneObjects.add(
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
        );*/

        rayTracer = new RayTracer(sceneObjects);
    }

    public BufferedImage renderFrame(final int width, final int height, final int pixelScaling){

        int renderWidth = width/pixelScaling;
        int renderHeight = height/pixelScaling;

        float xOffset = -renderWidth/2f;
        float yOffset = -renderHeight/2f;

        final float fovMultiplayer = 20f/pixelScaling;

        rayTracer.recacheObjects(camera.getPosition());

        BufferedImage bufferedImage = new BufferedImage(renderWidth, renderHeight, BufferedImage.TYPE_INT_RGB);

        int[] image = new int[renderHeight*renderWidth];

        for (int x = 0; x < renderWidth; x++) {
            for (int y = 0; y < renderHeight; y++) {

                Vector3d direction = new Vector3d(
                        (x+xOffset),
                        (y+yOffset),
                        10*fovMultiplayer
                );
                camera.rotateVector(direction);

                int color = rayTracer.rayTrace(direction);
                image[(renderHeight-y-1)*renderWidth + x] = color;
            }
        }
        bufferedImage.setRGB(0, 0, renderWidth, renderHeight, image, 0, renderWidth);
        return bufferedImage;
    }

    public Camera getCamera(){
        return camera;
    }

}
