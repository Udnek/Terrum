package me.jupiter;

import me.jupiter.image_reader.NetMapReader;
import me.jupiter.object.NetDynamicVertex;
import me.jupiter.object.NetStaticVertex;
import me.jupiter.object.NetVertex;
import me.jupiter.object.NetVoidVertex;
import org.realityforge.vecmath.Vector3d;

public class Net {
    private int sizeX;
    private int sizeZ;
    private final String imagePath;
    private final float springStiffness;
    private final float springRelaxedLength;
    public NetVertex[][] netMap;
    Net()
    {
        this.springStiffness = 1;
        this.springRelaxedLength = 1;
        this.imagePath = "C:/Coding/images/test_netmap.png";
    }
    Net(float springStiffness, float springRelaxedLength, String imagePath)
    {
        this.springStiffness = springStiffness;
        this.springRelaxedLength = springRelaxedLength;
        this.imagePath = imagePath;
    }

    public double calculateDistance(Vector3d point1, Vector3d point2) {
        return Math.sqrt(
                Math.pow((point2.x - point1.x), 2) +
                Math.pow((point2.y - point1.y), 2) +
                Math.pow((point2.z - point1.z), 2));
    }
    public int getSizeX(){return this.sizeX;}
    public int getSizeZ(){return this.sizeZ;}

    public void initiateNet() {
        NetMapReader reader = new NetMapReader();
        reader.readNetMap(imagePath);
        sizeX = reader.getWidth();
        System.out.println("Image width is " + sizeX);
        sizeZ = reader.getHeight();
        System.out.println("Image height is " + sizeZ + "\n");
        netMap = new NetVertex[sizeZ][sizeX];
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (reader.getColor(j, i).getBlue() == 255){
                    netMap[i][j] = new NetDynamicVertex(new Vector3d(j, 0, i));
                } else if (reader.getColor(j, i).getRed() == 255) {
                    netMap[i][j] = new NetVoidVertex(new Vector3d(j, 0, i));
                } else {
                    netMap[i][j] = new NetStaticVertex(new Vector3d(j, 0, i));
                }
            }
        }
    }

    public NetVertex[] locateNeighbourDynamicVertices(int x, int z) {
        NetVertex[] result = new NetVertex[4];
        if (z-1 >= 0) {
            if (netMap[z-1][x] instanceof NetDynamicVertex){
                result[0] = netMap[z-1][x];
            }
        }
        if (x+1 < sizeX){
            if (netMap[z][x+1] instanceof NetDynamicVertex){
                result[1] = netMap[z][x+1];
            }
        }
        if (z+1 < sizeZ){
            if (netMap[z+1][x] instanceof NetDynamicVertex){
                result[2] = netMap[z+1][x];
            }
        }
        if (x-1 >= 0){
            if (netMap[z][x-1] instanceof NetDynamicVertex){
                result[3] = netMap[z][x-1];
            }
        }
        return result;
    }

    public void initiateNeighbours(){
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (netMap[i][j] instanceof NetStaticVertex) {
                    netMap[i][j].setupNeighbours(locateNeighbourDynamicVertices(j, i));
                }
            }
        }
    }
}
