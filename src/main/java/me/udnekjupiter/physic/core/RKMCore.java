package me.udnekjupiter.physic.core;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.physic.object.RKMObject;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public abstract class RKMCore implements PhysicCore {
    protected List<RKMObject> allObjects = new ArrayList<>();
    protected List<RKMObject> collisionInitiators = new ArrayList<>();

    public void addObject(RKMObject object){
        allObjects.add(object);}
    public List<RKMObject> getAllObjects(){
        return allObjects;
    }
    public void addCollisionInitiator(RKMObject object){
        collisionInitiators.add(object);
    }
    public List<RKMObject> getCollisionInitiators(){
        return collisionInitiators;
    }
    public void updateNextObjectsCoefficients() {
        for (RKMObject object : allObjects) {
            object.calculateNextCoefficient();
        }
    }
    public void updateNextObjectsPhaseVectors(){
        for (RKMObject object : allObjects) {
            object.calculateNextPhaseVector();
        }
    }

    // TODO Should try optimising it
    public void updateColliders(){
        for (RKMObject object : allObjects) {
            object.clearCollidingObjects();
        }
        for (RKMObject targetObject : collisionInitiators) {
            for (RKMObject anotherObject : allObjects) {
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
        for (RKMObject object : allObjects) {
            object.RKMCalculatePositionDifferential();
        }
    }
    public void updateObjectsPositions(){
        for (RKMObject object : allObjects) {
            object.updatePosition();
        }
    }
    public void syncObjectsRKMPhaseVectors(){
        for (RKMObject object : allObjects) {
            object.setCurrentRKMPhaseVector(new Vector3d[]{object.getPosition(), object.getVelocity()});
        }
    }

    public void updateObjects(){
        syncObjectsRKMPhaseVectors();

        for (int i = 0; i < 4; i++) {
            updateColliders();
            updateNextObjectsCoefficients(); //coefficient1-2-3-4
            updateNextObjectsPhaseVectors(); //step1-2-3, last one is not executed
        }

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
        for (RKMObject object : allObjects) {
            object.reset();
        }
    }
}
