package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.net.NetSettings;
import org.realityforge.vecmath.Vector3d;

public class NetPhysicScene implements PhysicScene {

    private CellularNet net;
    public void tick() {net.updateNet();}
    public CellularNet getNet() {return net;}
    public void initialize(){
        net = new CellularNet("small_frame.png");
        net.initiateNet();
        net.initiateNeighbours();
        NetSettings settings = new NetSettings(5000, 1, 5, 0.01, 10, "", 1);
        net.setupVerticesVariables(settings);
    }

    public void setInitialDeviation(int x, int z, double xNew, double yNew, double zNew) {
        if (net.getVertex(x, z) == null) return;
        net.getVertex(x, z).setPosition(new Vector3d(xNew, yNew, zNew));
    }
}
