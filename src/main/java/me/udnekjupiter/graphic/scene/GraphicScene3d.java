package me.udnekjupiter.graphic.scene;

import me.udnekjupiter.Main;
import me.udnekjupiter.app.DebugMenu;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.ControllerListener;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.Draggable;
import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.object.PhysicLinked;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.renderable.DoubleSpringObject;
import me.udnekjupiter.graphic.object.renderable.MassEssenceObject;
import me.udnekjupiter.graphic.object.renderable.SurfaceObject;
import me.udnekjupiter.graphic.object.renderable.VertexObject;
import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.object.PlaneObject;
import me.udnekjupiter.physic.object.SphereObject;
import me.udnekjupiter.physic.object.SpringObject;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import me.udnekjupiter.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphicScene3d implements GraphicScene<GraphicObject3d>, ControllerListener {

    protected Camera camera;
    protected List<GraphicObject3d> renderableObjects = new ArrayList<>();
    protected LightSource lightSource;

    protected int width;
    protected int height;

    protected Draggable draggingObject = null;
    protected Selectable selectedObject = null;

    protected Controller controller;
    protected DebugMenu debugMenu;

    public GraphicScene3d(@NotNull Camera camera){
        this.camera = camera;
    }

    @Override
    public void initialize(){
        controller = Controller.getInstance();
        controller.addListener(this);
        debugMenu = Main.getMain().getApplication().getDebugMenu();
    }

    @Override
    @NotNull
    public Camera getCamera() {return camera;}

    // TODO TRY IMPLEMENTING SAME BEHAVIOUR IN ANOTHER WAY
    public void tryRepresentingAsGraphic(@NotNull Iterable<? extends @NotNull PhysicObject3d> physicObjects){
        for (@NotNull PhysicObject3d physicObject : physicObjects) {
            switch (physicObject) {
                case SphereObject sphereObject -> addObject(new MassEssenceObject(sphereObject));
                case NetVertex vertex -> addObject(new VertexObject(vertex));
                case SpringObject springObject -> addObject(new DoubleSpringObject(springObject));
                case PlaneObject planeObject -> addObject(new SurfaceObject(planeObject, null));
                default -> {}
            }
        }
    }

    @Override
    public void addObject(@NotNull GraphicObject3d object) {
        renderableObjects.add(object);
    }

    @Override
    public @NotNull List<? extends @NotNull GraphicObject3d> getObjects() {
        return renderableObjects;
    }

    public @Nullable LightSource getLightSource() {return lightSource;}
    public @NotNull Selectable getSelectedObject() {return selectedObject;}

    public void beforeFrameUpdate(int width, int height) {
        this.width = width;
        this.height = height;

        for (GraphicObject3d renderableObject : renderableObjects) {
            if (renderableObject instanceof Tickable tickable) tickable.tick();
            if (renderableObject instanceof PhysicLinked physicLinked) physicLinked.synchronizeWithPhysic();
        }

        for (InputKey pressedKey : controller.getPressedKeys()) {
            keyContinuouslyPressed(pressedKey);
        }
        if (controller.mouseIsPressed()){
            handleMousePressedDifference();
        }


        if (!debugMenu.isEnabled()) return;

        debugMenu.addTextToLeft("DraggingObject: " + draggingObject);
        debugMenu.addTextToLeft("SelectedObject: " + selectedObject);
        if (selectedObject != null && selectedObject instanceof PhysicLinked linkedObject){
            debugMenu.addTextToLeft("SelectedObjectPos: " + linkedObject.getPhysicRepresentation().getPosition().asString());
            debugMenu.addTextToLeft("SelectedObjectVelocity: " + linkedObject.getPhysicRepresentation().getVelocity().asString());
            debugMenu.addTextToLeft("SelectedObjectAcceleration: " + linkedObject.getPhysicRepresentation().getContainer().velocityDifferential.asString());
        }


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
        debugMenu.addTextToLeft("FOV: " + camera.getFov());
    }

    @Override
    public void keyEvent(InputKey inputKey, boolean pressed) {
        if (inputKey != InputKey.MOUSE_OBJECT_DRAG) return;
        if (pressed) {
            Selectable objectCursorLookingAt = findObjectCursorLookingAt(controller.getMouseCurrentPosition());
            if (objectCursorLookingAt == null) return;
            if (selectedObject != null) selectedObject.unselect();
            selectedObject = objectCursorLookingAt;
            selectedObject.select();
            if (objectCursorLookingAt instanceof Draggable draggable){
                setObjectPhysicFrozen(objectCursorLookingAt, true);
                draggingObject = draggable;
            }
        }
        else {
            setObjectPhysicFrozen(draggingObject, false);
            draggingObject = null;
        }
    }

    public void setObjectPhysicFrozen(Object object, boolean frozen){
        if (!(object instanceof PhysicLinked physicLinked)) return;
        if (!(physicLinked.getPhysicRepresentation() instanceof Freezable freezable)) return;
        freezable.setFrozen(frozen);
    }
    public boolean isObjectPhysicFrozen(Object object){
        if (!(object instanceof PhysicLinked physicLinked)) return false;
        if (!(physicLinked.getPhysicRepresentation() instanceof Freezable freezable)) return false;
        return freezable.isFrozen();
    }


    public void keyContinuouslyPressed(InputKey inputKey){
        final float deltaTime = (float) Main.getMain().getApplication().getFrameDeltaTime();
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
            float sensitivity = (float) (20f * Math.min(Main.getMain().getApplication().getFrameDeltaTime(), 0.01));
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
                height/camera.getFov()
        );
        camera.rotateVector(direction);
        return direction;
    }

    public Selectable findObjectCursorLookingAt(Point mousePosition){

        Vector3d cameraPosition = camera.getPosition();

        Vector3d direction = getMouseDirection(mousePosition);


        Selectable nearestObject = null;
        double nearestDistance = Double.POSITIVE_INFINITY;


        for (GraphicObject3d object : renderableObjects) {
            if (!(object instanceof Selectable selectable)) continue;
            Vector3d objectPosition = object.getPosition();

            for (Triangle plane: object.getRenderTriangles()) {
                plane.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);

                Vector3d intersection = VectorUtils.triangleRayIntersection(direction, plane);
                if (intersection == null) continue;
                if (intersection.lengthSquared() < nearestDistance){
                    nearestObject = selectable;
                    nearestDistance = intersection.lengthSquared();
                }
            }
        }
        return nearestObject;
    }
}
