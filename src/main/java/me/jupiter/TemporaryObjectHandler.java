package me.jupiter;

import me.jupiter.object.MassEssence;
import me.jupiter.object.RKMObject;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class TemporaryObjectHandler {
    public List<RKMObject> objects;

    public void initializeObjectHandler(){
        objects = new ArrayList<>();
    }
    public void addMassEssence(Vector3d position){
        objects.add(new MassEssence(position));
    }

    public void addObject(RKMObject object){
        objects.add(object);
    }

    public void updateObjectsCoefficients() {
        for (RKMObject object : objects) {
            object.calculateCoefficient1();
        }
        for (RKMObject object : objects) {
            object.updateRKMPhaseVector1();
        }
        for (RKMObject object : objects) {
            object.calculateCoefficient2();
        }
        for (RKMObject object : objects) {
            object.updateRKMPhaseVector2();
        }
        for (RKMObject object : objects) {
            object.calculateCoefficient3();
        }
        for (RKMObject object : objects) {
            object.updateRKMPhaseVector3();
        }
        for (RKMObject object : objects) {
            object.calculateCoefficient4();
        }
    }
    public void updateObjectsPositionDifferentials(){
        for (RKMObject object : objects) {
            object.RKMCalculatePositionDifferential();
        }
    }
    public void updateObjectsPositions(){
        for (RKMObject object : objects) {
            object.updatePosition();
        }
    }
    public void syncObjectsRKMPhaseVectors(){
        for (RKMObject object : objects) {
            object.setCurrentRKMPhaseVector(new Vector3d[]{object.getPosition(), object.getVelocity()});
        }
    }

    public void updateObjects(){
        syncObjectsRKMPhaseVectors();
        updateObjectsCoefficients();
        updateObjectsPositionDifferentials();
        updateObjectsPositions();
    }
}
