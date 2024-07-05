
package me.udnekjupiter.physic.net;

import me.udnekjupiter.file.ImageWrapper;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.object.vertex.NetDynamicVertex;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CellularNet {
    private final EnvironmentSettings settings;
    private int sizeX;
    private int sizeZ;
    public double potentialEnergy;
    public double kineticEnergy;
    public double fullEnergy;
    private final String imageName;
    private NetVertex[][] netMap;

    public CellularNet()
    {
        this.settings = EnvironmentSettings.ENVIRONMENT_SETTINGS;
        this.imageName = settings.imageFileName;
    }

    public int getSizeX(){return this.sizeX;}
    public int getSizeZ(){return this.sizeZ;}
    public NetVertex getVertex(int x, int z){ return netMap[z][x];}
    public void setVertex(NetVertex vertex, int x, int z){netMap[z][x] = vertex;}
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

    public void initiateNet() {
        ImageWrapper reader = new ImageWrapper();
        reader.readImage(imageName);

        sizeX = reader.getWidth();
        sizeZ = reader.getHeight();

        netMap = new NetVertex[sizeZ][sizeX];

        for (int z = 0; z < sizeZ; z++) {
            for (int x = 0; x < sizeX; x++) {
                Color color = reader.getColor(x, z);
                NetVertex netVertex = VertexColor.getVertex(color);
                if (netVertex == null) continue;
                netVertex.setPosition(new Vector3d(x, 0, z));
                setVertex(netVertex, x, z);
            }
        }
    }
    public void initiateNeighbours(){
        for (int z = 0; z < sizeZ; z++) {
            for (int x = 0; x < sizeX; x++) {
                NetVertex netVertex = getVertex(x, z);
                if (netVertex == null) continue;

                List<NetVertex> neighbourVertices = getNeighbourVertices(x, z);
                netVertex.addNeighbors(neighbourVertices);
            }
        }
    }
    public void setupVerticesVariables(){
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex){
                    ((NetDynamicVertex) getVertex(j, i)).setVariables(settings);
                }
            }
        }
    }
}
