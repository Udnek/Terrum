package me.udnekjupiter.physic.scene;

import me.udnekjupiter.app.StandartApplication;
import me.udnekjupiter.physic.object.ImplementedPhysicObject3d;
import me.udnekjupiter.physic.object.StandardObject3d;

import java.util.ArrayList;
import java.util.List;

public abstract class StandardPhysicScene{
    protected List<StandardObject3d> allObjects = new ArrayList<>();
    protected List<StandardObject3d> collisionInitiators = new ArrayList<>();

    public void addObject(StandardObject3d object){
        allObjects.add(object);}
    public List<StandardObject3d> getAllObjects(){
        return allObjects;
    }
    public void addCollisionInitiator(StandardObject3d object){
        collisionInitiators.add(object);
    }
    public List<StandardObject3d> getCollisionInitiators(){
        return collisionInitiators;
    }

    // TODO Should try optimising it
    public void updateColliders(){
        for (StandardObject3d object : allObjects) {
            object.clearCollidingObjects();
        }
        for (StandardObject3d targetObject : collisionInitiators) {
            for (StandardObject3d anotherObject : allObjects) {
                if (targetObject == anotherObject) continue;
                if (targetObject.isCollisionIgnored(anotherObject)) continue;
                if (targetObject.collidingObjectIsAlreadyListed(anotherObject)) continue;
                if (!targetObject.getCollider().isCollidingWith(anotherObject.getCollider())) continue;
                targetObject.addCollidingObject(anotherObject);
                anotherObject.addCollidingObject(targetObject);
            }
        }
    }

    public void updateObjectsPositionDifferentials(){
        for (StandardObject3d object : allObjects) {
            object.calculatePhaseDifferential();
        }
    }
    public void updateObjectsPositions(){
        for (StandardObject3d object : allObjects) {
            object.updatePosition();
        }
    }

    public void updateObjects(){
        updateColliders();
        updateObjectsPositionDifferentials();
        updateObjectsPositions();
    }

    public void tick() {
        for (int i = 0; i < StandartApplication.ENVIRONMENT_SETTINGS.iterationsPerTick; i++) {
            updateObjects();
        }
    }

    public void reset() {
        for (ImplementedPhysicObject3d object : allObjects) {
            object.reset();
        }
    }
}
