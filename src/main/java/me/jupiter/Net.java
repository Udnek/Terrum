package me.jupiter;

import me.jupiter.image_reader.NetMapReader;
import me.jupiter.object.NetDynamicVertex;
import me.jupiter.object.NetStaticVertex;
import me.jupiter.object.NetVertex;
import me.jupiter.object.NetVoidVertex;
import org.realityforge.vecmath.Vector3d;

public class Net {
    public final int sizeX;
    public final int sizeZ;
    private final String imagePath;
    private final float springStiffness;
    private final float springRelaxedLength;
    public NetVertex[][] netMap;
    Net()
    {
        this.sizeX = 4;
        this.sizeZ = 4;
        this.springStiffness = 1;
        this.springRelaxedLength = 1;
        this.imagePath = "C:/Coding/images/test_netmap.png";
    }
    Net(int sizeX, int sizeZ, float springStiffness, float springRelaxedLength, String imagePath)
    {
        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
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

    public void initiateNet() {
        NetMapReader reader = new NetMapReader();
        reader.readNetMap(imagePath);
        netMap = new NetVertex[reader.getWidth()][reader.getHeight()];
        for (int i = 0; i < reader.getWidth(); i++) {
            for (int j = 0; j < reader.getHeight(); j++) {
                if (reader.getColor(i, j).getBlue() == 255){
                    netMap[i][j] = new NetDynamicVertex(new Vector3d(i, 0, j));
                } else if (reader.getColor(i, j).getRed() == 255) {
                    netMap[i][j] = new NetVoidVertex(new Vector3d(i, 0, j));
                } else {
                    netMap[i][j] = new NetStaticVertex(new Vector3d(i, 0, j));
                }
            }
        }

    }
}
