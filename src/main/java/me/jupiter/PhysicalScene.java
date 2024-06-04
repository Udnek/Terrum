package me.jupiter;

import me.jupiter.net.CellularNet;
import me.udnek.scene.instances.NetScene;
import org.realityforge.vecmath.Vector3d;

public class PhysicalScene extends NetScene {


    @Override
    public void tick() {
        for (int i = 0; i < 4; i++) {
            net.updateVerticesPositionDifferentials();
            net.updateVerticesPositions();
        }
        synchroniseObjects();
    }

    public void setup(double springStiffness,
                      double springRelaxedLength,
                      double vertexMass,
                      double deltaTime,
                      double decayCoefficient,
                      String imageFileName){
        net = new CellularNet(imageFileName);
        net.initiateNet();
        net.initiateNeighbours();
        net.setupVerticesVariables(springStiffness, springRelaxedLength, vertexMass, deltaTime, decayCoefficient);
    }

    public void setInitialDeviation(int x, int z, double xNew, double yNew, double zNew) {
        net.getVertex(x, z).setPosition(new Vector3d(xNew, yNew, zNew));
    }

}
