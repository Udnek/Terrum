package me.udnekjupiter.physic.scene;

import me.udnekjupiter.graphic.object.traceable.MassEssenceObject;
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.physic.object.SphereObject;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import me.udnekjupiter.util.Resettable;
import org.realityforge.vecmath.Vector3d;
import org.w3c.dom.ls.LSOutput;

import java.util.List;

public class NetPhysicsScene extends RKMPhysicsScene implements Resettable {
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

        for (RKMObject object : getAllObjects()) {
            if (!(object instanceof NetVertex)){
                addCollisionInitiator(object);
            }
        }

    }

    public void reset(){
        net.reset();
    }

    public void addSphereObject(Vector3d position, double colliderRadius, double mass){
        System.out.println(getAllObjects());
        addObject(new SphereObject(position, colliderRadius, mass));
    }
    public void setVertexPosition(int posX, int posZ, Vector3d newPos){
        net.getVertex(posX, posZ).setPosition(newPos);
    }
}
