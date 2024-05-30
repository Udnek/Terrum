package me.udnek.scene;


import me.udnek.objects.SceneObject;
import me.udnek.objects.light.LightSource;
import me.udnek.utils.UserAction;
import org.realityforge.vecmath.Vector3d;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene{

    protected Camera camera;
    protected List<? extends SceneObject> sceneObjects = new ArrayList<>();
    protected RayTracer rayTracer;
    protected LightSource lightSource;

    public Scene(){
        camera = initCamera();
        lightSource = initLightSource();
        sceneObjects = initSceneObjects();
        rayTracer = new RayTracer(sceneObjects, lightSource);
    }

    protected abstract Camera initCamera();
    protected abstract List<? extends SceneObject> initSceneObjects();
    protected abstract LightSource initLightSource();
    public abstract void tick();

    public BufferedImage renderFrame(final int width, final int height, final int pixelScaling){

        int renderWidth = width/pixelScaling;
        int renderHeight = height/pixelScaling;
        float xOffset = -renderWidth/2f;
        float yOffset = -renderHeight/2f;
        final float fovMultiplayer = 20f/pixelScaling;

        // TODO: 5/28/2024 CACHE PLAYER POSITION

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

    public void handleUserInput(UserAction userAction){;
        final float moveSpeed = 0.07f;
        final float rotateSpeed = 2f;
        switch (userAction){
            case MOVE_FORWARD -> camera.moveAlongDirection(new Vector3d(0, 0, moveSpeed));
            case MOVE_BACKWARD -> camera.moveAlongDirection(new Vector3d(0, 0, -moveSpeed));
            case MOVE_RIGHT -> camera.moveAlongDirection(new Vector3d(moveSpeed, 0, 0));
            case MOVE_LEFT -> camera.moveAlongDirection(new Vector3d(-moveSpeed, 0, 0));

            case MOVE_UP -> camera.move(new Vector3d(0, moveSpeed, 0));
            case MOVE_DOWN -> camera.move(new Vector3d(0, -moveSpeed*2, 0));

            case CAMERA_UP -> camera.rotatePitch(-rotateSpeed);
            case CAMERA_DOWN -> camera.rotatePitch(rotateSpeed);
            case CAMERA_RIGHT -> camera.rotateYaw(-rotateSpeed);
            case CAMERA_LEFT -> camera.rotateYaw(rotateSpeed);
        }

    }

}
