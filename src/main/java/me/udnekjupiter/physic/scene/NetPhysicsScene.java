package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.MassEssence;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public class NetPhysicsScene extends RKMPhysicsScene {
    private final CellularNet net;
    public NetPhysicsScene(String mapImageName){
        this.net = new CellularNet(mapImageName);
    }
    public CellularNet getNet(){return net;}

    public void initialize(){
        net.initialize();
        List<NetVertex> vertices = net.getVerticesObjects();
        for (NetVertex vertex : vertices) {
            if (vertex != null) {
                addObject(vertex);
            }
        }
        addMassEssence(new Vector3d(2, 10, 2), 2, 75);

    }

    public void addMassEssence(Vector3d position, double colliderRadius, double mass){
        addObject(new MassEssence(position, colliderRadius, mass));
    }
    public void setVertexPosition(int posX, int posZ, Vector3d newPos){
        net.getVertex(posX, posZ).setPosition(newPos);
    }
}
