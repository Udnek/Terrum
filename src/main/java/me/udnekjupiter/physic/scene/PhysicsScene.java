package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.net.CellularNet;
import org.realityforge.vecmath.Vector3d;

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
}
