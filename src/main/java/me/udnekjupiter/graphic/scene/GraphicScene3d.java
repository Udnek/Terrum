package me.udnekjupiter.graphic.scene;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.app.DebugMenu;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.ControllerListener;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.PhysicLinked;
import me.udnekjupiter.graphic.object.fixedsize.FixedSizeObject;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.traceable.SpringObject;
import me.udnekjupiter.graphic.object.traceable.TraceableObject;
import me.udnekjupiter.util.*;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GraphicScene3d implements GraphicScene, ControllerListener {

    protected Camera camera;
    protected List<TraceableObject> traceableObjects;
    protected LightSource lightSource;
    protected List<FixedSizeObject> fixedSizeObjects;

    protected int width;
    protected int height;

    protected TraceableObject draggingObject = null;

    protected Controller controller;
    protected DebugMenu debugMenu;

    @Override
    public void initialize(){
        camera = initializeCamera();
        lightSource = initializeLightSource();

        traceableObjects = initializeSceneObjects();
        if (traceableObjects == null){
            traceableObjects = new ArrayList<>();
        }

        fixedSizeObjects = initializeFixedSizeObjects();
        if (fixedSizeObjects == null){
            fixedSizeObjects = new ArrayList<>();
        }

        controller = Controller.getInstance();
        controller.addListener(this);
        debugMenu = Application.DEBUG_MENU;

    }

    protected abstract Camera initializeCamera();
    protected abstract List<TraceableObject> initializeSceneObjects();
    protected abstract LightSource initializeLightSource();
    protected abstract List<FixedSizeObject> initializeFixedSizeObjects();

    public Camera getCamera() { return camera;}
    public LightSource getLightSource() {return lightSource;}
    public List<TraceableObject> getTraceableObjects() {return traceableObjects;}
    public List<FixedSizeObject> getFixedSizeObjects() {return fixedSizeObjects;}

    public void beforeFrameUpdate(int width, int height) {
        this.width = width;
        this.height = height;

        for (TraceableObject traceableObject : traceableObjects) {
            if (traceableObject instanceof Tickable tickable) tickable.tick();
            if (traceableObject instanceof PhysicLinked physicLinked) physicLinked.synchronizeWithPhysic();
        }

        for (InputKey pressedKey : controller.getPressedKeys()) {
            keyContinuouslyPressed(pressedKey);
        }
        if (controller.mouseIsPressed()){
            handleMousePressedDifference();
        }

        if (!debugMenu.isEnabled()) return;

        debugMenu.addTextToLeft("DraggingObject: " + draggingObject);
        debugMenu.addTextToLeft("DraggingObjectPhysicalFrozen: " + isObjectPhysicFrozen(draggingObject));

        debugMenu.addTextToLeft("MouseCurrentPosition: " + controller.getMouseCurrentPosition());
        Vector3d position = camera.getPosition();
        debugMenu.addTextToLeft(
                "XYZ: " +
                        Utils.roundToPrecision(position.x, 2) + " " +
                        Utils.roundToPrecision(position.y, 2) + " " +
                        Utils.roundToPrecision(position.z, 2) + " "
        );
        debugMenu.addTextToLeft(
                "Yaw, Pitch: " +
                        Utils.roundToPrecision(camera.getYaw(), 2) + ", " +
                        Utils.roundToPrecision(camera.getPitch(), 2)
        );
    }

    @Override
    public void keyEvent(InputKey inputKey, boolean pressed) {
        if (inputKey != InputKey.MOUSE_OBJECT_DRAG) return;
        if (pressed) {
            TraceableObject objectCursorLookingAt = findObjectCursorLookingAt(controller.getMouseCurrentPosition());
            setObjectPhysicFrozen(objectCursorLookingAt, true);
            draggingObject = objectCursorLookingAt;
        }
        else {
            setObjectPhysicFrozen(draggingObject, false);
            draggingObject = null;
        }
    }

    public void setObjectPhysicFrozen(TraceableObject object, boolean frozen){
        if (!(object instanceof PhysicLinked physicLinked)) return;
        if (!(physicLinked.getPhysicRepresentation() instanceof Freezable freezable)) return;
        freezable.setFrozen(frozen);
    }
    public boolean isObjectPhysicFrozen(TraceableObject object){
        if (!(object instanceof PhysicLinked physicLinked)) return false;
        if (!(physicLinked.getPhysicRepresentation() instanceof Freezable freezable)) return false;
        return freezable.isFrozen();
    }


    public void keyContinuouslyPressed(InputKey inputKey){
        final float deltaTime = (float) Application.getFrameDeltaTime();
        final float moveSpeed = 5f * deltaTime;
        final float rotateSpeed = 60f * deltaTime;
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
            float sensitivity = (float) (10f * Application.getFrameDeltaTime() * ApplicationSettings.GLOBAL.pixelScaling);
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

            // TODO: 7/6/2024 THINK ABOUT PHYSICAL LINK
            draggingObject.setPosition(camera.getPosition().add(mouseDirection));
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

    public TraceableObject findObjectCursorLookingAt(Point mousePosition){

        Vector3d cameraPosition = camera.getPosition();

        Vector3d direction = getMouseDirection(mousePosition);


        TraceableObject nearestObject = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (TraceableObject object : traceableObjects) {
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
