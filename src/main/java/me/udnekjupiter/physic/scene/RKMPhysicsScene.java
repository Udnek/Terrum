package me.udnekjupiter.physic.scene;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.physic.object.RKMObject3d;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public abstract class RKMPhysicsScene {
    protected List<RKMObject3d> allObjects = new ArrayList<>();
    protected List<RKMObject3d> collisionInitiators = new ArrayList<>();

    public void addObject(RKMObject3d object){
        allObjects.add(object);}
    public List<RKMObject3d> getAllObjects(){
        return allObjects;
    }
    public void addCollisionInitiator(RKMObject3d object){
        collisionInitiators.add(object);
    }
    public List<RKMObject3d> getCollisionInitiators(){
        return collisionInitiators;
    }
    public void updateNextObjectsCoefficients() {
        for (RKMObject3d object : allObjects) {
            object.calculateNextCoefficient();
        }
    }
    public void updateNextObjectsPhaseVectors(){
        for (RKMObject3d object : allObjects) {
            object.calculateNextPhaseVector();
        }
    }

    // TODO Should try optimising it
    public void updateColliders(){
        for (RKMObject3d object : allObjects) {
            object.clearCollidingObjects();
        }
        for (RKMObject3d targetObject : collisionInitiators) {
            for (RKMObject3d anotherObject : allObjects) {
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
        for (RKMObject3d object : allObjects) {
            object.RKMCalculatePositionDifferential();
        }
    }
    public void updateObjectsPositions(){
        for (RKMObject3d object : allObjects) {
            object.updatePosition();
        }
    }
    public void syncObjectsRKMPhaseVectors(){
        for (RKMObject3d object : allObjects) {
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

    public void reset() {
        for (RKMObject3d object : allObjects) {
            object.reset();
        }
    }
}