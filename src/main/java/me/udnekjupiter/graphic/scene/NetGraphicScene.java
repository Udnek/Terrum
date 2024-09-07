
package me.udnekjupiter.graphic.scene;

import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.fixedsize.FixedSizeObject;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.light.PointLight;
import me.udnekjupiter.graphic.object.renderable.*;
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.physic.object.SphereObject;
import me.udnekjupiter.physic.object.StandardObject;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import me.udnekjupiter.physic.scene.NetPhysicsScene;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetGraphicScene extends GraphicScene3d {
    private final NetPhysicsScene netPhysicsScene;
    public NetGraphicScene(NetPhysicsScene netPhysicsScene){
        super();
        this.netPhysicsScene = netPhysicsScene;
    }

    private boolean containsAnywhere(Map<NetVertex, List<NetVertex>> map, NetVertex netVertex){
        if (map.containsKey(netVertex)) return true;
        return containsInKeys(map, netVertex);
    }

    private boolean containsInKeys(Map<NetVertex, List<NetVertex>> map, NetVertex netVertex){
        for (List<NetVertex> value : map.values()) {
            for (NetVertex vertex : value) {
                if (vertex == netVertex) return true;
            }
        }
        return false;
    }

    @Override
    protected List<RenderableObject> initializeSceneObjects() {

        List<RenderableObject> graphicObjects = new ArrayList<>();

        for (CellularNet net : netPhysicsScene.getNets()) {
            initializeNet(graphicObjects, net);
        }


        List<StandardObject> physicObjects = netPhysicsScene.getAllObjects();
        for (PhysicObject object : physicObjects) {
            if (!(object instanceof SphereObject sphereObject)) continue;
            graphicObjects.add(new MassEssenceObject(sphereObject));
        }

        return graphicObjects;
    }

    protected void initializeNet(List<RenderableObject> objects, CellularNet net){
        List<VertexObject> vertices = new ArrayList<>();
        List<SpringObject> springs = new ArrayList<>();

        Map<NetVertex, List<NetVertex>> addedNeighbours = new HashMap<>();
        Map<NetVertex, VertexObject> graphicRepresentation = new HashMap<>();

        for (int x = 0; x < net.getSizeX(); x++) {
            for (int z = 0; z < net.getSizeZ(); z++) {

                NetVertex netVertex = net.getVertex(x, z);

                if (netVertex == null) continue;
                if (addedNeighbours.containsKey(netVertex)) continue;


                VertexObject vertexObject;
                if (!graphicRepresentation.containsKey(netVertex)){
                    vertexObject = new VertexObject(new Vector3d(netVertex.getPosition()), netVertex);
                    vertices.add(vertexObject);
                    graphicRepresentation.put(netVertex, vertexObject);
                }else {
                    vertexObject = graphicRepresentation.get(netVertex);
                }


                List<NetVertex> neighbors = new ArrayList<>();
                addedNeighbours.put(netVertex, neighbors);

                // TODO: 5/31/2024 FIX AND OPTIMIZE
                // 7/7/2024 was kinda fixed

                for (NetVertex neighbor : netVertex.getNeighbors()) {
                    if (neighbors.contains(neighbor)) continue;

                    neighbors.add(neighbor);

                    if (addedNeighbours.getOrDefault(neighbor, new ArrayList<>()).contains(netVertex)) continue;

                    VertexObject neighbourObject;
                    if (!graphicRepresentation.containsKey(neighbor)){
                        neighbourObject = new VertexObject(new Vector3d(neighbor.getPosition()), neighbor);
                        graphicRepresentation.put(neighbor, neighbourObject);
                        vertices.add(neighbourObject);
                    } else {
                        neighbourObject = graphicRepresentation.get(neighbor);
                    }
                    springs.add(new DoubleSpringObject(vertexObject, neighbourObject));


                }
            }
        }

        objects.addAll(vertices);
        objects.addAll(springs);
    }


    @Override
    protected Camera initializeCamera() {
        Camera camera = new Camera(new Vector3d(5, 8, -9));
        camera.rotatePitch(40);
        return camera;
    }

    @Override
    protected LightSource initializeLightSource() {
        return new PointLight(new Vector3d(0, 2, 0));
    }

    @Override
    protected List<FixedSizeObject> initializeFixedSizeObjects() {
        return null;
    }
}

