package me.udnekjupiter.graphic.scene;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.ControllerListener;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.GraphicObject;
import me.udnekjupiter.graphic.object.SpringObject;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.util.Triangle;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GraphicScene3d implements GraphicScene, ControllerListener {

    protected Camera camera;
    protected List<GraphicObject> objects = new ArrayList<>();
    protected LightSource lightSource;

    protected int width;
    protected int height;

    protected GraphicObject draggingObject = null;

    protected Controller controller;

    @Override
    public void initialize(){
        camera = initializeCamera();
        lightSource = initializeLightSource();
        objects = initializeSceneObjects();
        controller = Controller.getInstance();
        controller.addListener(this);

    }

    protected abstract Camera initializeCamera();
    protected abstract List<GraphicObject> initializeSceneObjects();
    protected abstract LightSource initializeLightSource();

    public Camera getCamera() { return camera;}
    public LightSource getLightSource() {return lightSource;}
    public List<GraphicObject> getObjects() {
        return objects;
    }


    public void beforeFrameUpdate(int width, int height) {
        this.width = width;
        this.height = height;

        Application.debugMenu.addTextToLeft("DraggingObject: " + draggingObject);
        Application.debugMenu.addTextToLeft("MouseCurrentPosition: " + controller.getMouseCurrentPosition());

        for (InputKey pressedKey : controller.getPressedKeys()) {
            keyContinuouslyPressed(pressedKey);
        }
        if (controller.mouseIsPressed()){
            handleMousePressedDifference();
        }
    }

    @Override
    public void keyEvent(InputKey inputKey, boolean pressed) {
        if (inputKey != InputKey.MOUSE_OBJECT_DRAG) return;
        if (pressed) draggingObject = findObjectCursorLookingAt(controller.getMouseCurrentPosition());
        else draggingObject = null;
    }

    public void keyContinuouslyPressed(InputKey inputKey){
        final float deltaTime = (float) Application.getFrameDeltaTime();
        final float moveSpeed = 5f * deltaTime;
        final float rotateSpeed = 30f * deltaTime;
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
    public void handleMousePressedDifference(){
        InputKey mouseKey = controller.getMouseKey();
        if (mouseKey == InputKey.MOUSE_CAMERA_DRAG){
            float sensitivity = (float) (3f * Application.getFrameDeltaTime());
            Point mouseDifference = controller.getMouseDifference();
            camera.rotateYaw(mouseDifference.x*-sensitivity);
            camera.rotatePitch(mouseDifference.y*sensitivity);
        }
        else if (mouseKey == InputKey.MOUSE_OBJECT_DRAG){
            if (draggingObject == null) return;

            Point mousePosition = Controller.getInstance().getMouseCurrentPosition();
            Vector3d mouseDirection = getMouseDirection(mousePosition);

            double distance = VectorUtils.distance(draggingObject.getPosition(), camera.getPosition());
            mouseDirection.normalize().mul(distance);

            draggingObject.setPosition(camera.getPosition().add(mouseDirection));

/*            float sensitivity = (float) (0.15f * Application.getFrameDeltaTime());
            Vector3d moveDirection = new Vector3d(mouseDifference.x*sensitivity, -mouseDifference.y*sensitivity, 0);
            camera.rotateVector(moveDirection);
            draggingObject.move(moveDirection);*/


        }

    }

    public Vector3d getMouseDirection(Point mousePosition){
        Vector3d direction = new Vector3d(
                (mousePosition.x - width/2f),
                ((height-mousePosition.y-1) - height/2f),
                width/camera.getFov()
        );
        camera.rotateVector(direction);
        return direction;
    }

    public GraphicObject findObjectCursorLookingAt(Point mousePosition){

        Vector3d cameraPosition = camera.getPosition();

        Vector3d direction = getMouseDirection(mousePosition);


        GraphicObject nearestObject = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (GraphicObject object : objects) {
            // TODO: 7/2/2024 SOMETHING ABOUT SPRINGS? MAYBE PROPERTY FOR ANY GRAOHICOBJECTS
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
}
