package me.udnek.scene;


import me.udnek.app.AppSettings;
import me.udnek.app.DebugMenu;
import me.udnek.app.console.Command;
import me.udnek.app.console.ConsoleHandler;
import me.udnek.app.controller.ControllerHandler;
import me.udnek.app.controller.InputKey;
import me.udnek.object.SceneObject;
import me.udnek.object.SpringObject;
import me.udnek.object.light.LightSource;
import me.udnek.util.Triangle;
import me.udnek.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene implements ConsoleHandler, ControllerHandler {

    protected Camera camera;
    protected List<? extends SceneObject> sceneObjects = new ArrayList<>();
    protected RayTracer rayTracer;
    protected LightSource lightSource;

    protected int width;
    protected int height;

    protected SceneObject draggingObject = null;

    public Scene(){

    }

    public void init(){
        camera = initCamera();
        lightSource = initLightSource();
        sceneObjects = initSceneObjects();
        rayTracer = new RayTracer(camera, sceneObjects, lightSource);
    }

    protected abstract Camera initCamera();
    protected abstract List<? extends SceneObject> initSceneObjects();
    protected abstract LightSource initLightSource();
    public abstract void tick();

    public void addExtraDebug(DebugMenu debugMenu){
        debugMenu.addTextToLeft("draggingObject: "+ draggingObject);
    }

    public void updateSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public BufferedImage renderFrame(final int width, final int height){
        int renderWidth = width/ AppSettings.globalSettings.pixelScaling;
        int renderHeight = height/ AppSettings.globalSettings.pixelScaling;

        BufferedImage bufferedImage = new BufferedImage(renderWidth, renderHeight, BufferedImage.TYPE_INT_RGB);

        int[] frame = rayTracer.renderFrame(renderWidth, renderHeight);

        bufferedImage.setRGB(0, 0, renderWidth, renderHeight, frame, 0, renderWidth);
        return bufferedImage;
    }


    @Override
    public void keyEvent(InputKey inputKey, boolean pressed) {}
    public void mouseDragClick(Point mousePosition, boolean pressed){
        if (pressed) draggingObject = findObjectCursorLookingAt(mousePosition);
        else draggingObject = null;
    }

    public void keyContinuouslyPressed(InputKey inputKey){
        final float moveSpeed = 0.04f;
        final float rotateSpeed = 1f;
        switch (inputKey){
            case MOVE_FORWARD -> camera.moveAlongDirectionParallelXZ(new Vector3d(0, 0, moveSpeed));
            case MOVE_BACKWARD -> camera.moveAlongDirectionParallelXZ(new Vector3d(0, 0, -moveSpeed));
            case MOVE_LEFT -> camera.moveAlongDirectionParallelXZ(new Vector3d(-moveSpeed, 0, 0));
            case MOVE_RIGHT -> camera.moveAlongDirectionParallelXZ(new Vector3d(moveSpeed, 0, 0));
            case MOVE_UP -> camera.move(new Vector3d(0, moveSpeed, 0));
            case MOVE_DOWN -> camera.move(new Vector3d(0, -moveSpeed*2, 0));

            case CAMERA_UP -> camera.rotatePitch(-rotateSpeed);
            case CAMERA_DOWN -> camera.rotatePitch(rotateSpeed);
            case CAMERA_RIGHT -> camera.rotateYaw(-rotateSpeed);
            case CAMERA_LEFT -> camera.rotateYaw(rotateSpeed);
        }
    }
    public void handleMousePressedDifference(Point mouseDifference, InputKey key){
        if (key == InputKey.MOUSE_CAMERA_DRAG){
            float sensitivity = 0.15f;
            camera.rotateYaw(mouseDifference.x*-sensitivity);
            camera.rotatePitch(mouseDifference.y*sensitivity);
        }
        else {
            if (draggingObject == null) return;
            float sensitivity = 0.01f;
            Vector3d moveDirection = new Vector3d(mouseDifference.x*sensitivity, -mouseDifference.y*sensitivity, 0);
            camera.rotateVector(moveDirection);
            draggingObject.move(moveDirection);
        }

    }

    public SceneObject findObjectCursorLookingAt(Point mousePosition){

        Vector3d cameraPosition = camera.getPosition();


        Vector3d direction = new Vector3d(
                (mousePosition.x - width/2f),
                ((height-mousePosition.y-1) - height/2f),
                width/camera.getFov()
        );


        camera.rotateVector(direction);

        SceneObject nearestObject = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (SceneObject object : sceneObjects) {
            if (object instanceof SpringObject) continue;
            Vector3d objectPosition = object.getPosition();
            for (Triangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);

                Vector3d intersection = VectorUtils.triangleRayIntersection(direction, plane);
                if (intersection != null){
                    if (intersection.lengthSquared() < nearestDistance){
                        nearestObject = object;
                        nearestDistance = intersection.lengthSquared();
                    }
                }
            }
        }
        return nearestObject;
    }



    public Camera getCamera() { return camera;}
    @Override
    public void handleCommand(Command command, Object[] args) {}
}
