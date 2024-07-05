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

        updateNextObjectsCoefficients(); //coefficient1
        updateNextObjectsPhaseVectors(); //step1
        updateNextObjectsCoefficients(); //coefficient2
        updateNextObjectsPhaseVectors(); //step2
        updateNextObjectsCoefficients(); //coefficient3
        updateNextObjectsPhaseVectors(); //step3
        updateNextObjectsCoefficients(); //coefficient4

        updateObjectsPositionDifferentials();
        updateObjectsPositions();
    }

    public void tick() {
        for (int i = 0; i < EnvironmentSettings.ENVIRONMENT_SETTINGS.iterationsPerTick; i++) {
            updateObjects();
        }
    }
}
