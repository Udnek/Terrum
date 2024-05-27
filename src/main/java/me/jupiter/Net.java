package me.jupiter;

import me.jupiter.object.NetVertex;
import org.realityforge.vecmath.Vector3d;

public class Net {
    public final int sizeX;
    public final int sizeZ;
    public final float springStiffness;
    private NetVertex[][] net;

    Net()
    {
        this.sizeX = 4;
        this.sizeZ = 4;
        this.springStiffness = 1;
        this.net = new NetVertex[sizeX][sizeZ];
    }
    Net(int sizeX, int sizeZ, float springStiffness)
    {
        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
        this.springStiffness = springStiffness;
    }

    public double calculateDistance(Vector3d point1, Vector3d point2) {
        return Math.sqrt(
                Math.pow((point2.x - point1.x), 2) +
                Math.pow((point2.y - point1.y), 2) +
                Math.pow((point2.z - point1.z), 2));
    }
}
