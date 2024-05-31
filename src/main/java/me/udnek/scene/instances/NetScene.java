package me.udnek.scene.instances;

import me.jupiter.net.CellularNet;
import me.jupiter.object.NetVertex;
import me.jupiter.object.NetVoidVertex;
import me.udnek.objects.SceneObject;
import me.udnek.objects.SpringObject;
import me.udnek.objects.VertexObject;
import me.udnek.objects.light.LightSource;
import me.udnek.objects.light.PointLight;
import me.udnek.scene.Camera;
import me.udnek.scene.Scene;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public abstract class NetScene extends Scene {
    protected CellularNet net;
    protected List<VertexObject> vertices;
    protected List<SpringObject> springs;
    public NetScene(){}


    @Override
    protected List<? extends SceneObject> initSceneObjects() {
        vertices = new ArrayList<>();
        springs = new ArrayList<>();
        List<NetVertex> addedNetVertices = new ArrayList<>();

        for (int x = 0; x < net.getSizeX(); x++) {
            for (int z = 0; z < net.getSizeZ(); z++) {

                NetVertex netVertex = net.getVertex(x, z);

                if (addedNetVertices.contains(netVertex)) continue;
                if (netVertex instanceof NetVoidVertex) continue;

                VertexObject vertexObject = new VertexObject(new Vector3d(netVertex.getPosition()), netVertex);
                vertices.add(vertexObject);
                addedNetVertices.add(netVertex);
                for (NetVertex neighbour : netVertex.getNeighbours()) {
                    VertexObject neighbourObject = new VertexObject(new Vector3d(neighbour.getPosition()), neighbour);
                    vertices.add(neighbourObject);
                    addedNetVertices.add(neighbour);
                    springs.add(new SpringObject(new Vector3d(), vertexObject, neighbourObject));
                }
            }
        }

        List<SceneObject> sceneObjects = new ArrayList<>();
        sceneObjects.addAll(vertices);
        sceneObjects.addAll(springs);
        return sceneObjects;
    }

    public void synchroniseObjects(){
        for (VertexObject vertex : vertices) {
            vertex.update();
        }
        for (SpringObject springObject : springs) {
            springObject.update();
        }
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
    public boolean doLight() {
        return false;
    }
}

