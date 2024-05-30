package me.udnek.scene.example;

import me.jupiter.net.CellularNet;
import me.jupiter.object.NetVertex;
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

public class NetScene extends Scene {
    private CellularNet net;
    private List<VertexObject> vertices;
    private List<Spring> springs;
    public NetScene(CellularNet net){
        this.net = net;
        initSceneObjects();
    }


    @Override
    protected List<? extends SceneObject> initSceneObjects() {

        if (net == null) return new ArrayList<>();

        vertices = new ArrayList<>();
        springs = new ArrayList<>();

        for (int x = 0; x < net.getSizeX(); x++) {
            for (int z = 0; z < net.getSizeZ(); z++) {
                NetVertex netVertex = net.getVertex(x, z);
                vertices.add(new VertexObject(new Vector3d(netVertex.getPosition()), netVertex));
            }
        }


        return vertices;

    }

    @Override
    protected Camera initCamera() {
        return new Camera();
    }


    @Override
    protected LightSource initLightSource() {
        return new PointLight(new Vector3d(0, 2, 0));
    }

    @Override
    public void tick() {
        for (VertexObject vertex : vertices) {
            vertex.update();
        }
    }
}
