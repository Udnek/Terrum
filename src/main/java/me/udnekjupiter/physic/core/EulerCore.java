package me.udnekjupiter.physic.core;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.physic.object.StandardObject;

import java.util.ArrayList;
import java.util.List;

public class EulerCore implements PhysicCore {
    protected List<PhysicObject> allObjects = new ArrayList<>();
    protected List<PhysicObject> collisionInitiators = new ArrayList<>();

    public void addObject(PhysicObject object){allObjects.add(object);}
    public List<PhysicObject> getAllObjects(){
        return allObjects;
    }
    public void addCollisionInitiator(PhysicObject object){
        collisionInitiators.add(object);
    }
    public List<PhysicObject> getCollisionInitiators(){
        return collisionInitiators;
    }

    // TODO Should try optimising it
    public void updateColliders(){
        for (PhysicObject object : allObjects) {
            object.clearCollidingObjects();
        }
        for (PhysicObject targetObject : collisionInitiators) {
            for (PhysicObject anotherObject : allObjects) {
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
        for (PhysicObject object:allObjects) {
            (RKMObject) object.calculatePhaseDifferential();
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

    @Override
    public void reset() {
        for (PhysicObject object : allObjects) {
            object.reset();
        }
    }
}
