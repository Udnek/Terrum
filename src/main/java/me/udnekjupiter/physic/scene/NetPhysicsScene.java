package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.MassEssence;
import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.physic.object.vertex.NetDynamicVertex;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

public class NetPhysicsScene extends RKMPhysicsScene {

    //TODO Remove net usage as variable
    private CellularNet net;
    public CellularNet getNet(){return net;}


    public void initialize(){
        RKMObjects = new ArrayList<>();
        net = new CellularNet();
        net.initiateNet();
        net.initiateNeighbours();
        net.setupVerticesVariables();
        List<NetVertex> vertices = net.getVerticesObjects();
        for (NetVertex vertex : vertices) {
            if (vertex instanceof NetDynamicVertex) {
                addObject(vertex);
            }
        }
    }

//    public void setInitialDeviation(int x, int z, double xNew, double yNew, double zNew) {
//        if (net.getVertex(x, z) == null) return;
//        net.getVertex(x, z).setPosition(new Vector3d(xNew, yNew, zNew));
//    }

    public void addMassEssence(Vector3d position){
        RKMObjects.add(new MassEssence(position));
    }
    public void setVertexPosition(int posX, int posZ, Vector3d newPos){
        net.getVertex(posX, posZ).setPosition(newPos);
    }
}
