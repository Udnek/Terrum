package me.jupiter;

import me.jupiter.net.CellularNet;
import me.jupiter.net.NetSettings;
import me.udnek.app.console.Command;
import me.udnek.scene.instances.NetScene;
import org.realityforge.vecmath.Vector3d;

public class PhysicalScene extends NetScene {
    private NetSettings settings;
    @Override
    public String[] getExtraDebug() {
        return new String[]{"KE: " + net.kineticEnergy,
                            "PE: " + net.potentialEnergy,
                            "FE: " + net.fullEnergy};
    }

    @Override
    public void handleCommand(Command command, String[] args) {
        super.handleCommand(command, args);
    }

    @Override
    public void tick() {
        net.updateNet();
        synchroniseObjects();
    }

    public void setup(NetSettings settings){
        this.settings = settings;
        net = new CellularNet(settings.imageFileName);
        net.initiateNet();
        net.initiateNeighbours();
        net.setupVerticesVariables(settings);
    }

    public void setInitialDeviation(int x, int z, double xNew, double yNew, double zNew) {
        net.getVertex(x, z).setPosition(new Vector3d(xNew, yNew, zNew));
    }

}
