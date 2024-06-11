package me.jupiter;

import me.jupiter.net.CellularNet;
import me.jupiter.net.NetSettings;
import me.udnek.app.DebugMenu;
import me.udnek.app.console.Command;
import me.udnek.scene.Camera;
import me.udnek.scene.instances.NetScene;
import org.realityforge.vecmath.Vector3d;

public class PhysicalScene extends NetScene {
    private NetSettings settings;
    @Override
    public void addExtraDebug(DebugMenu debugMenu) {
        super.addExtraDebug(debugMenu);
        debugMenu.addTextToLeft("KE: " + net.kineticEnergy);
        debugMenu.addTextToLeft("PE: " + net.potentialEnergy);
        debugMenu.addTextToLeft("FE: " + net.fullEnergy);
    }

    @Override
    public void handleCommand(Command command, Object[] args) {
        if (command == Command.SET_ITERATIONS_PER_TICK) {
            settings.iterationsPerTick = (int) args[0];
        }
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
        if (net.getVertex(x, z) == null) return;
        net.getVertex(x, z).setPosition(new Vector3d(xNew, yNew, zNew));
    }

    @Override
    public Camera initCamera() {
        Camera camera = new Camera(new Vector3d(5, 4, -1.5));
        camera.rotatePitch(40);
        return camera;
    }
}
