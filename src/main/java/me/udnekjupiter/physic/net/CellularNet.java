
package me.udnekjupiter.physic.net;

import me.udnekjupiter.file.ImageWrapper;
import me.udnekjupiter.physic.container.PhysicVariableContainer;
import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.object.SpringObject;
import me.udnekjupiter.physic.object.vertex.NetStaticVertex;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import me.udnekjupiter.util.Vector3x3;
import org.jetbrains.annotations.Nullable;
import me.udnekjupiter.util.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO Try scaling net, so system would look more authentic
//TODO Implement angular net rotation
public class CellularNet {
    private Vector3d globalOffset;
    private Vector3x3 perPositionMultiplier;
    private int sizeX;
    private int sizeZ;
    private final String mapImageName;
    private NetVertex[][] netMap;
    private List<PhysicObject3d> objects = new ArrayList<>();
    private double springStiffness;
    private double springRelaxedLength;
    private double vertexMass;

    public CellularNet(String mapImageName, Vector3d globalOffset, Vector3x3 perPositionMultiplier, double springStiffness, double springRelaxedLength, double vertexMass) {
        this.mapImageName = mapImageName;
        this.globalOffset = globalOffset;
        this.perPositionMultiplier = perPositionMultiplier;
        this.springStiffness = springStiffness;
        this.springRelaxedLength = springRelaxedLength;
        this.vertexMass = vertexMass;
    }
    public CellularNet(String mapImageName, Vector3d globalOffset, double springStiffness, double springRelaxedLength, double vertexMass) {
        this(mapImageName, globalOffset, new Vector3x3(new Vector3d(1, 0, 0),
                                                        new Vector3d(),
                                                        new Vector3d(0, 0, 1)),
                springStiffness,
                springRelaxedLength,
                vertexMass);
    }
    public CellularNet(String mapImageName) {
        this(mapImageName, new Vector3d(), new Vector3x3(new Vector3d(1, 0, 0),
                                                         new Vector3d(),
                                                         new Vector3d(0, 0, 1)),
                10_000, 1, 1);
    }

    public int getSizeX(){return this.sizeX;}
    public int getSizeZ(){return this.sizeZ;}

    public void resetVertexPosition(int x, int z){
        Vector3d multiplier = new Vector3d();
        multiplier.add(perPositionMultiplier.x.dup().mul(x));
        multiplier.add(perPositionMultiplier.z.dup().mul(z));

        getVertex(x, z).setPosition(multiplier.add(globalOffset));
    }
    public @Nullable NetVertex getVertex(int x, int z){ return netMap[z][x];}
    public void setVertex(@Nullable NetVertex vertex, int x, int z){netMap[z][x] = vertex;}

    public boolean isInBounds(int x, int z) {return (x >= 0 && x < sizeX && z >= 0 && z < sizeZ);}
    public List<NetVertex> getNeighbourVertices(int posX, int posZ) {
        List<NetVertex> vertices = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // diagonals
                if (Math.abs(i+j) != 1) continue;

                int x = posX + i;
                int z = posZ + j;

                if (!isInBounds(x, z)) continue;

                NetVertex netVertex = getVertex(x, z);
                if (netVertex == null) continue;
                vertices.add(netVertex);
            }
        }
        return vertices;
    }
    public List<PhysicObject3d> getNetObjects(){
        return objects;
    }

    public void initialize() {
        initializeVertices();
        initializeSprings();
    }

    private void initializeVertices() {
        ImageWrapper reader = new ImageWrapper();
        reader.readImage(mapImageName);

        sizeX = reader.getWidth();
        sizeZ = reader.getHeight();

        netMap = new NetVertex[sizeZ][sizeX];

        for (int z = 0; z < sizeZ; z++) {
            for (int x = 0; x < sizeX; x++) {
                Color color = reader.getColor(x, z);
                NetVertex netVertex = VertexColor.getVertex(color);
                if (netVertex == null) continue;
                objects.add(netVertex);
                netVertex.setContainer(new PhysicVariableContainer(new Vector3d(x, 0, z).add(globalOffset)));
                netVertex.getContainer().mass = vertexMass;
                setVertex(netVertex, x, z);
            }
        }
    }
//    private void initializeNeighbours(){
//        for (int z = 0; z < sizeZ; z++) {
//            for (int x = 0; x < sizeX; x++) {
//                NetVertex netVertex = getVertex(x, z);
//                if (netVertex == null) continue;
//                List<NetVertex> neighbourVertices = getNeighbourVertices(x, z);
//                netVertex.addNeighbors(neighbourVertices);
//            }
//        }
//    }

    private void initializeSprings(){
        ArrayList<ArrayList<NetVertex>> pairedVertices = new ArrayList<>();
        List<SpringObject> springs = new ArrayList<>();
        for (int z = 0; z < sizeZ; z++) {
            for (int x = 0; x < sizeX; x++) {
                NetVertex netVertex = getVertex(x, z);
                if (netVertex == null) continue;
                List<NetVertex> neighbourVertices = getNeighbourVertices(x, z);
                for (NetVertex neighbourVertex : neighbourVertices) {
                    if (netVertex instanceof NetStaticVertex && neighbourVertex instanceof NetStaticVertex) continue;
                    ArrayList<NetVertex> pair = new ArrayList<>(Arrays.asList(neighbourVertex, netVertex));
                    if (pairedVertices.contains(pair)) continue;
                    pairedVertices.add(new ArrayList<>(pair.reversed()));
                    springs.add(new SpringObject(netVertex, neighbourVertex, springRelaxedLength, springStiffness));
                }
            }
        }
        objects.addAll(springs);
    }
}
