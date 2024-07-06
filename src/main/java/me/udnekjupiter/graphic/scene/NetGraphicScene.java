
package me.udnekjupiter.graphic.scene;

import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.fixedsize.FixedSizeObject;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.object.light.PointLight;
import me.udnekjupiter.graphic.object.traceable.*;
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.MassEssence;
import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.physic.object.vertex.NetStaticVertex;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import me.udnekjupiter.physic.scene.NetPhysicsScene;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetGraphicScene extends GraphicScene3d {
    private final NetPhysicsScene netPhysicsScene;
    public NetGraphicScene(NetPhysicsScene netPhysicsScene){
        this.netPhysicsScene = netPhysicsScene;
    }

    @Override
    protected List<TraceableObject> initializeSceneObjects() {
        List<VertexObject> vertices = new ArrayList<>();
        List<SpringObject> springs = new ArrayList<>();

        CellularNet net = netPhysicsScene.getNet();
        HashMap<NetVertex, List<NetVertex>> addedNeighbours = new HashMap<>();

        for (int x = 0; x < net.getSizeX(); x++) {
            for (int z = 0; z < net.getSizeZ(); z++) {

                NetVertex netVertex = net.getVertex(x, z);

                if (netVertex == null) continue;
                if (netVertex instanceof NetStaticVertex) continue;

                if (addedNeighbours.containsKey(netVertex)) continue;


                VertexObject vertexObject = new VertexObject(new Vector3d(netVertex.getPosition()), netVertex);
                vertices.add(vertexObject);
                List<NetVertex> addedNeighbourVertices = addedNeighbours.getOrDefault(netVertex, null);
                if (addedNeighbourVertices == null) {
                    addedNeighbourVertices = new ArrayList<>();
                    addedNeighbours.put(netVertex, addedNeighbourVertices);
                }

                // TODO: 5/31/2024 FIX AND OPTIMIZE

                for (NetVertex neighbour : netVertex.getNeighbors()) {
                    if (addedNeighbours.containsKey(neighbour)) continue;

                    VertexObject neighbourObject = new VertexObject(new Vector3d(neighbour.getPosition()), neighbour);
                    vertices.add(neighbourObject);
                    springs.add(new DoubleSpringObject(new Vector3d(), vertexObject, neighbourObject));

                }
            }
        }

        List<TraceableObject> graphicObjects = new ArrayList<>();
;
        List<RKMObject> rkmObjects = netPhysicsScene.getRKMObjects();
        for (RKMObject object : rkmObjects) {
            if (!(object instanceof MassEssence massEssence)) continue;
            graphicObjects.add(new MassEssenceObject(massEssence));
        }

        
        graphicObjects.addAll(vertices);
        graphicObjects.addAll(springs);

        return graphicObjects;
    }


    @Override
    protected Camera initializeCamera() {
        Camera camera = new Camera(new Vector3d(5, 4.5, -2.5));
        camera.rotatePitch(35);
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

