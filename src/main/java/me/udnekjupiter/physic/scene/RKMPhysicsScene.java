package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.object.RKMObject;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public abstract class RKMPhysicsScene implements PhysicScene {
    protected List<RKMObject> RKMObjects;

    public void addObject(RKMObject object){
        RKMObjects.add(object);
    }

    public void updateNextObjectsCoefficients() {
        for (RKMObject object : RKMObjects) {
            object.calculateNextCoefficient();
        }
    }
    public void updateNextObjectsPhaseVectors(){
        for (RKMObject object : RKMObjects) {
            object.calculateNextPhaseVector();
        }
    }

    // TODO Should try optimising it
    public void updateColliders(){
        for (RKMObject object : RKMObjects) {
            object.clearCollidingObjects();
        }
        for (RKMObject targetObject : RKMObjects) {
            for (RKMObject anotherObject : RKMObjects) {
                if (targetObject == anotherObject) continue;
                if (!targetObject.getCollider().isCollidingWith(anotherObject.getCollider())) continue;
                if (targetObject.collidingObjectIsAlreadyListed(anotherObject)) continue;

                targetObject.addCollidingObject(anotherObject);
                anotherObject.addCollidingObject(targetObject);
            }
        }
    }

    public void updateObjectsPositionDifferentials(){
        for (RKMObject object : RKMObjects) {
            object.RKMCalculatePositionDifferential();
        }
    }
    public void updateObjectsPositions(){
        for (RKMObject object : RKMObjects) {
            object.updatePosition();
        }
    }
    public void syncObjectsRKMPhaseVectors(){
        for (RKMObject object : RKMObjects) {
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
        for (int i = 0; i < EnvironmentSettings.ENVIRONMENT_SETTINGS.iterationsPerTick; i++) {
            updateObjects();
        }
    }
}
