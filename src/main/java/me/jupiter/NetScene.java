package me.jupiter;

import me.jupiter.net.CellularNet;
import me.jupiter.object.NetVertex;
import me.jupiter.object.NetVoidVertex;
import me.udnek.objects.SceneObject;
import me.udnek.objects.VertexObject;
import me.udnek.objects.light.LightSource;
import me.udnek.objects.light.PointLight;
import me.udnek.scene.Camera;
import me.udnek.scene.Scene;
import org.realityforge.vecmath.Vector3d;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static me.jupiter.image_reader.ImageReader.getImageDirectory;

public class NetScene extends Scene {
    private CellularNet net;
    private List<VertexObject> vertices;
    private List<Spring> springs;
    public NetScene(){}


    @Override
    protected List<? extends SceneObject> initSceneObjects() {
        vertices = new ArrayList<>();
        springs = new ArrayList<>();

        for (int x = 0; x < net.getSizeX(); x++) {
            for (int z = 0; z < net.getSizeZ(); z++) {
                NetVertex netVertex = net.getVertex(x, z);
                if (netVertex instanceof NetVoidVertex) continue;
                vertices.add(new VertexObject(new Vector3d(netVertex.getPosition()), netVertex));
            }
        }


        return vertices;

    }

    @Override
    protected Camera initCamera() {
        Camera camera = new Camera(new Vector3d(2, 4, 2));
        camera.rotatePitch(90);
        return camera;
    }


    @Override
    protected LightSource initLightSource() {
        return new PointLight(new Vector3d(0, 2, 0));
    }

    @Override
    public void tick() {
        System.out.println(net.getVertex(4, 4).getPosition().asString());
        net.updateVerticesPositionDifferentials();
        net.updateVerticesPositions();
        for (VertexObject vertex : vertices) {
            vertex.update();
        }
    }

    @Override
    public boolean doLight() {
        return false;
    }

    public void setup(double springStiffness,
                           double springRelaxedLength,
                           double vertexMass,
                           double deltaTime,
                           double decayCoefficient,
                           String imageFileName){
        net = new CellularNet(getImageDirectory() + imageFileName);
        net.initiateNet();
        net.initiateNeighbours();
        net.setupVerticesVariables(springStiffness, springRelaxedLength, vertexMass, deltaTime, decayCoefficient);
        init();
    }

    public void setInitialDeviation(int x, int z, double yShift) {
        net.getVertex(x, z).setPosition(new Vector3d(x, yShift, z));
    }
}

