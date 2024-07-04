package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.MassEssence;
import me.udnekjupiter.physic.object.RKMObject;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class PhysicsScene implements PhysicScene {

    private CellularNet net;
    public void tick() {net.updateNet();}
    public CellularNet getNet() {return net;}
    public void initialize(){
        net = new CellularNet();
        net.initiateNet();
        net.initiateNeighbours();
        net.setupVerticesVariables();
    }

    public void setInitialDeviation(int x, int z, double xNew, double yNew, double zNew) {
        if (net.getVertex(x, z) == null) return;
        net.getVertex(x, z).setPosition(new Vector3d(xNew, yNew, zNew));
    }

    public List<RKMObject> RKMObjects;

    public void initializeObjectHandler(){
        RKMObjects = new ArrayList<>();
    }
    public void addMassEssence(Vector3d position){
        RKMObjects.add(new MassEssence(position));
    }

    public void addObject(RKMObject object){
        RKMObjects.add(object);
    }

    public void updateObjectsCoefficients() {
        for (RKMObject object : RKMObjects) {
            object.calculateCoefficient1();
        }
        for (RKMObject object : RKMObjects) {
            object.updateRKMPhaseVector1();
        }
        for (RKMObject object : RKMObjects) {
            object.calculateCoefficient2();
        }
        for (RKMObject object : RKMObjects) {
            object.updateRKMPhaseVector2();
        }
        for (RKMObject object : RKMObjects) {
            object.calculateCoefficient3();
        }
        for (RKMObject object : RKMObjects) {
            object.updateRKMPhaseVector3();
        }
        for (RKMObject object : RKMObjects) {
            object.calculateCoefficient4();
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
        updateObjectsCoefficients();
        updateObjectsPositionDifferentials();
        updateObjectsPositions();
    }
}
