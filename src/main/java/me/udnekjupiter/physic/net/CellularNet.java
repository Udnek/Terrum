
package me.udnekjupiter.physic.net;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.file.ImageWrapper;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.object.vertex.NetDynamicVertex;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import me.udnekjupiter.util.Freezable;
import me.udnekjupiter.util.Initializable;
import me.udnekjupiter.util.Resettable;
import me.udnekjupiter.util.Vector3x3;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CellularNet implements Initializable, Freezable, Resettable {
    private Vector3d globalOffset;
    private Vector3x3 perPositionMultiplier;
    //TODO Try scaling net, so system would look more authentic
    private final EnvironmentSettings settings;
    private boolean frozen;
    private int sizeX;
    private int sizeZ;
    public double potentialEnergy;
    public double kineticEnergy;
    public double fullEnergy;
    private final String mapImageName;
    private NetVertex[][] netMap;

    public CellularNet(String mapImageName, Vector3d globalOffset, Vector3x3 perPositionMultiplier) {
        this.settings = Application.ENVIRONMENT_SETTINGS;
        this.mapImageName = mapImageName;
        this.globalOffset = globalOffset;
        this.perPositionMultiplier = perPositionMultiplier;
    }
    public CellularNet(String mapImageName, Vector3d globalOffset) {
        this(mapImageName, globalOffset, new Vector3x3(
                new Vector3d(1, 0, 0),
                new Vector3d(),
                new Vector3d(0, 0, 1)
        ));
    }
    public CellularNet(String mapImageName) {
        this(mapImageName, new Vector3d());
    }

    public int getSizeX(){return this.sizeX;}
    public int getSizeZ(){return this.sizeZ;}
    public NetVertex getVertex(int x, int z){ return netMap[z][x];}
    public void setVertex(NetVertex vertex, int x, int z){netMap[z][x] = vertex;}

    public void resetVertexPosition(int x, int z){
        Vector3d multiplier = new Vector3d();
        multiplier.add(perPositionMultiplier.x.dup().mul(x));
        multiplier.add(perPositionMultiplier.z.dup().mul(z));

        getVertex(x, z).setPosition(multiplier.add(globalOffset));
    }
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
    public List<NetVertex> getVerticesObjects(){
        List<NetVertex> vertices = new ArrayList<>();
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                vertices.add(getVertex(j, i));
            }
        }
        return vertices;
    }

    @Override
    public void initialize() {
        initializeNet();
        initializeNeighbours();
        initializeVerticesVariables();
    }

    public void reset(){
        for (int z = 0; z < sizeZ; z++) {
            for (int x = 0; x < sizeX; x++) {
                if (getVertex(x, z) == null) continue;
                getVertex(x, z).reset();
                resetVertexPosition(x, z);
            }
        }
    }

    private void initializeNet() {
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
                setVertex(netVertex, x, z);
                resetVertexPosition(x, z);
            }
        }
    }
    private void initializeNeighbours(){
        for (int z = 0; z < sizeZ; z++) {
            for (int x = 0; x < sizeX; x++) {
                NetVertex netVertex = getVertex(x, z);
                if (netVertex == null) continue;

                List<NetVertex> neighbourVertices = getNeighbourVertices(x, z);
                netVertex.addNeighbors(neighbourVertices);
            }
        }
    }
    private void initializeVerticesVariables(){
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex){
                    ((NetDynamicVertex) getVertex(j, i)).setVariables(settings);
                }
            }
        }
    }

    @Override
    public void freeze() {
        for (NetVertex[] netRow : netMap) {
            for (NetVertex netVertex : netRow) {
                netVertex.freeze();
            }
        }
        this.frozen = true;
    }

    @Override
    public void unfreeze() {
        for (NetVertex[] netRow : netMap) {
            for (NetVertex netVertex : netRow) {
                netVertex.unfreeze();
            }
        }
        this.frozen = false;
    }

    @Override
    public boolean isFrozen() {
        return frozen;
    }
}
