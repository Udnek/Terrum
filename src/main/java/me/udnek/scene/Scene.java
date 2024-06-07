package me.udnek.scene;


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

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene implements ConsoleHandler, ControllerHandler {

    protected Camera camera;
    protected List<? extends SceneObject> sceneObjects = new ArrayList<>();
    protected RayTracer rayTracer;
    protected LightSource lightSource;

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
        debugMenu.addTextToLeft("draggingObject: "+draggingObject);
    }


    public BufferedImage renderFrame(final int width, final int height, final int pixelScaling, int cores){

        int renderWidth = width/pixelScaling;
        int renderHeight = height/pixelScaling;

        BufferedImage bufferedImage = new BufferedImage(renderWidth, renderHeight, BufferedImage.TYPE_INT_RGB);

        int[] frame = rayTracer.renderFrame(renderWidth, renderHeight);

        bufferedImage.setRGB(0, 0, renderWidth, renderHeight, frame, 0, renderWidth);
        return bufferedImage;
    }


    @Override
    public void keyEvent(InputKey inputKey, boolean pressed) {
        switch (inputKey) {
            case MOUSE_OBJECT_DRAG -> {
                if (pressed) draggingObject = findObjectCursorLookingAt();
                else draggingObject = null;
            }
        }
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
    public void handleMousePressedDifference(int xDifference, int yDifference, InputKey key){
        if (key == InputKey.MOUSE_CAMERA_DRAG){
            camera.rotateYaw(xDifference/-10f);
            camera.rotatePitch(yDifference/10f);
        }
        else {
            if (draggingObject == null) return;
            float sensitivity = 0.01f;
            Vector3d moveDirection = new Vector3d(xDifference*sensitivity, -yDifference*sensitivity, 0);
            camera.rotateVector(moveDirection);
            draggingObject.move(moveDirection);
        }

    }

    public SceneObject findObjectCursorLookingAt(){
        Vector3d cameraPosition = camera.getPosition();

        Vector3d direction = camera.getDirection();

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
