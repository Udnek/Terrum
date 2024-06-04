package me.udnek.scene;


import me.udnek.app.Settings;
import me.udnek.object.SceneObject;
import me.udnek.object.light.LightSource;
import me.udnek.util.UserAction;
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

    }

    public void init(Settings.PolygonHolderType polygonHolderType){
        camera = initCamera();
        lightSource = initLightSource();
        sceneObjects = initSceneObjects();
        rayTracer = new RayTracer(camera, sceneObjects, lightSource, doLight(), polygonHolderType);
    }

    protected abstract Camera initCamera();
    protected abstract List<? extends SceneObject> initSceneObjects();
    protected abstract LightSource initLightSource();
    public abstract void tick();

    public boolean doLight(){return true;};




    public BufferedImage renderFrame(final int width, final int height, final int pixelScaling, int cores){

        int renderWidth = width/pixelScaling;
        int renderHeight = height/pixelScaling;

        BufferedImage bufferedImage = new BufferedImage(renderWidth, renderHeight, BufferedImage.TYPE_INT_RGB);

        int[] frame = rayTracer.renderFrame(renderWidth, renderHeight, cores);

        bufferedImage.setRGB(0, 0, renderWidth, renderHeight, frame, 0, renderWidth);
        return bufferedImage;
    }



    public void handleUserInput(UserAction userAction){;
        final float moveSpeed = 0.07f;
        final float rotateSpeed = 2f;
        switch (userAction){
            case MOVE_FORWARD -> camera.moveAlongDirectionParallelXZ(new Vector3d(0, 0, moveSpeed));
            case MOVE_BACKWARD -> camera.moveAlongDirectionParallelXZ(new Vector3d(0, 0, -moveSpeed));
            case MOVE_RIGHT -> camera.moveAlongDirectionParallelXZ(new Vector3d(moveSpeed, 0, 0));
            case MOVE_LEFT -> camera.moveAlongDirectionParallelXZ(new Vector3d(-moveSpeed, 0, 0));

            case MOVE_UP -> camera.move(new Vector3d(0, moveSpeed, 0));
            case MOVE_DOWN -> camera.move(new Vector3d(0, -moveSpeed*2, 0));

            case CAMERA_UP -> camera.rotatePitch(-rotateSpeed);
            case CAMERA_DOWN -> camera.rotatePitch(rotateSpeed);
            case CAMERA_RIGHT -> camera.rotateYaw(-rotateSpeed);
            case CAMERA_LEFT -> camera.rotateYaw(rotateSpeed);
        }

    }

    public Camera getCamera() {
        return camera;
    }
}
