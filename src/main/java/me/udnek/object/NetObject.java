package me.udnek.object;

import me.udnek.util.Triangle;
import org.realityforge.vecmath.Vector3d;

public class NetObject extends SceneObject{
    public NetObject(Vector3d position) {
        super(position);
    }

/*    public NetScene(){

    }

    protected init(NetVertex){
        vertices = new ArrayList<>();
        springs = new ArrayList<>();

        for (int x = 0; x < net.getSizeX(); x++) {
            for (int z = 0; z < net.getSizeZ(); z++) {
                NetVertex netVertex = net.getVertex(x, z);
                vertices.add(new VertexObject(new Vector3d(netVertex.getPosition()), netVertex));
            }
        }


        return vertices;
    }*/


    @Override
    public Triangle[] getRenderTriangles() {
        return new Triangle[0];
    }
}
