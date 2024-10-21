package me.udnekjupiter.physic.scene;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.physic.object.StandardObject;

import java.util.ArrayList;
import java.util.List;

public abstract class EulerPhysicScene {
    protected List<StandardObject> allObjects = new ArrayList<>();
    protected List<StandardObject> collisionInitiators = new ArrayList<>();

    public void addObject(StandardObject object){
        allObjects.add(object);}
    public List<StandardObject> getAllObjects(){
        return allObjects;
    }
    public void addCollisionInitiator(StandardObject object){
        collisionInitiators.add(object);
    }
    public List<StandardObject> getCollisionInitiators(){
        return collisionInitiators;
    }

    // TODO Should try optimising it
    public void updateColliders(){
        for (StandardObject object : allObjects) {
            object.clearCollidingObjects();
        }
        for (StandardObject targetObject : collisionInitiators) {
            for (StandardObject anotherObject : allObjects) {
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
        for (StandardObject object : allObjects) {
            object.calculatePhaseDifferential();
        }
    }
    public void updateObjectsPositions(){
        for (StandardObject object : allObjects) {
            object.updatePosition();
        }
    }

    public void updateObjects(){
        updateColliders();
        updateObjectsPositionDifferentials();
        updateObjectsPositions();
    }

    public void tick() {
        for (int i = 0; i < Application.ENVIRONMENT_SETTINGS.iterationsPerTick; i++) {
            updateObjects();
        }
    }

    public void reset() {
        for (PhysicObject object : allObjects) {
            object.reset();
        }
    }
}
