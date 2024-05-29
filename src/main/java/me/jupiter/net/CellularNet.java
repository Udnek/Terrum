package me.jupiter.net;

import me.jupiter.image_reader.ImageReader;
import me.jupiter.object.NetVertex;
import me.jupiter.object.NetVoidVertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CellularNet {
    private int sizeX;
    private int sizeZ;
    private final String imagePath;
    private final float springStiffness;
    private final float springRelaxedLength;
    public NetVertex[][] netMap;

    public CellularNet(float springStiffness, float springRelaxedLength, String imagePath)
    {
        this.springStiffness = springStiffness;
        this.springRelaxedLength = springRelaxedLength;
        this.imagePath = imagePath;
    }

    public int getSizeX(){return this.sizeX;}
    public int getSizeZ(){return this.sizeZ;}

    public void initiateNet() {
        ImageReader reader = new ImageReader();
        reader.readNetMap(imagePath);

        sizeX = reader.getWidth();
        sizeZ = reader.getHeight();

        netMap = new NetVertex[sizeZ][sizeX];

        for (int z = 0; z < sizeZ; z++) {
            for (int x = 0; x < sizeX; x++) {
                Color color = reader.getColor(x, z);
                NetVertex vertex = VertexColor.getVertex(color);
                setVertex(vertex, x, z);
            }
        }
    }

    public NetVertex getVertex(int x, int z){ return netMap[z][x];}
    public void setVertex(NetVertex vertex, int x, int z){netMap[z][x] = vertex;}

    public boolean isInBounds(int x, int z){
        return (x > 0 && x < sizeX && z > 0 && z < sizeZ);
    }
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
}
