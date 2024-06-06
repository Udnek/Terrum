package me.jupiter.net;

import me.jupiter.file_managment.ImageWrapper;
import me.jupiter.object.NetDynamicVertex;
import me.jupiter.object.NetStaticVertex;
import me.jupiter.object.NetVertex;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CellularNet {
    private NetSettings settings;
    private int sizeX;
    private int sizeZ;
    public double potentialEnergy;
    public double kineticEnergy;
    public double fullEnergy;
    private final String imageName;
    private NetVertex[][] netMap;

    public CellularNet(String imageName)
    {
        this.imageName = imageName;
    }

    public int getSizeX(){return this.sizeX;}
    public int getSizeZ(){return this.sizeZ;}

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

    public void initiateNeighbours(){
        for (int z = 0; z < sizeZ; z++) {
            for (int x = 0; x < sizeX; x++) {
                NetVertex netVertex = getVertex(x, z);
                if (netVertex == null) continue;

                List<NetVertex> neighbourVertices = getNeighbourVertices(x, z);
                netVertex.addNeighbours(neighbourVertices);
            }
        }
    }

    public void setupVerticesVariables(NetSettings settings){
        this.settings = settings;
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex){
                    ((NetDynamicVertex) getVertex(j, i)).setVariables(settings);
                }
            }
        }
    }
    public void updateVerticesCoefficients() {
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex) {
                    ((NetDynamicVertex) getVertex(j, i)).calculateCoefficient1();
                }
            }
        }
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex) {
                    ((NetDynamicVertex) getVertex(j, i)).updateRKMPhaseVector1();
                }
            }
        }
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex){
                    ((NetDynamicVertex) getVertex(j, i)).calculateCoefficient2();
                }
            }
        }
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex) {
                    ((NetDynamicVertex) getVertex(j, i)).updateRKMPhaseVector2();
                }
            }
        }
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex) {
                    ((NetDynamicVertex) getVertex(j, i)).calculateCoefficient3();
                }
            }
        }
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex) {
                    ((NetDynamicVertex) getVertex(j, i)).updateRKMPhaseVector3();
                }
            }
        }
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex) {
                    ((NetDynamicVertex) getVertex(j, i)).calculateCoefficient4();
                }
            }
        }
    }

    public void updateVerticesPositionDifferentials(){
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex){
                    ((NetDynamicVertex) getVertex(j, i)).RKMethodCalculatePositionDifferential();
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

    public void updateNetKineticEnergy(){
        double kineticSum = 0;
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex){
                    kineticSum += ((NetDynamicVertex) getVertex(j, i)).getKineticEnergy();
                }
            }
        }
        this.kineticEnergy = kineticSum;
    }
    public void updateNetPotentialEnergy(){
        double potentialSum = 0;
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (getVertex(j, i) instanceof NetDynamicVertex){
                    potentialSum += ((NetDynamicVertex) getVertex(j, i)).getPotentialEnergy();
                }
            }
        }
        this.potentialEnergy = potentialSum;
    }
    public void updateNetFullEnergy(){
        this.fullEnergy = kineticEnergy+potentialEnergy;
    }

    public void updateNet(){
        for (int i = 0; i < settings.iterationsPerTick; i++) {
            updateNetPotentialEnergy();
            updateNetKineticEnergy();
            updateNetFullEnergy();
            updateVerticesCoefficients();
            updateVerticesPositionDifferentials();
            updateVerticesPositions();
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
