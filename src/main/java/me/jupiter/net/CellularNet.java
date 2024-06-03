package me.jupiter.net;

import me.jupiter.image_reader.ImageReader;
import me.jupiter.object.NetDynamicVertex;
import me.jupiter.object.NetStaticVertex;
import me.jupiter.object.NetVertex;
import me.jupiter.object.NetVoidVertex;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CellularNet {
    private int sizeX;
    private int sizeZ;
    private final String imagePath;
    private NetVertex[][] netMap;

    public CellularNet(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public int getSizeX(){return this.sizeX;}
    public int getSizeZ(){return this.sizeZ;}

    public void initiateNet() {
        ImageReader reader = new ImageReader();
        reader.readImage(imagePath);

        sizeX = reader.getWidth();
        sizeZ = reader.getHeight();

        netMap = new NetVertex[sizeZ][sizeX];

        for (int z = 0; z < sizeZ; z++) {
            for (int x = 0; x < sizeX; x++) {
                Color color = reader.getColor(x, z);
                NetVertex vertex = VertexColor.getVertex(color);
                vertex.setPosition(new Vector3d(x, 0, z));
                setVertex(vertex, x, z);
            }
        }
    }

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

                vertices.add(getVertex(x, z));
            }
        }
        return vertices;
    }

    public void initiateNeighbours(){
        for (int z = 0; z < sizeZ; z++) {
            for (int x = 0; x < sizeX; x++) {
                NetVertex netVertex = getVertex(x, z);
                if (netVertex instanceof NetVoidVertex) continue;

                List<NetVertex> neighbourVertices = getNeighbourVertices(x, z);
                netVertex.addNeighbours(neighbourVertices);
            }
        }
    }

    public void setupVerticesVariables(double springStiffness,
                                       double springRelaxedLength,
                                       double vertexMass,
                                       double deltaTime,
                                       double decayCoefficient){
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex){
                    ((NetDynamicVertex) getVertex(j, i)).setVariables(springStiffness,
                            springRelaxedLength,
                            vertexMass,
                            deltaTime,
                            decayCoefficient);
                }
            }
        }
    }

    public void updateVerticesPositionDifferentials(){
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex){
                    ((NetDynamicVertex) getVertex(j, i)).calculateAppliedForce();
                    ((NetDynamicVertex) getVertex(j, i)).calculatePositionDifferential();
                }
            }
        }
    }

    public void updateVerticesPositions(){
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex){
                    ((NetDynamicVertex) getVertex(j, i)).updatePosition();
                }
            }
        }
    }

    public void printMap(){
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetStaticVertex){
                    System.out.print("[ ]");
                } else if (getVertex(j, i) instanceof NetDynamicVertex) {
                    System.out.print(" - ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }
}
