package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import me.udnekjupiter.util.Resettable;

import java.util.List;

public class NetPhysicsScene extends RKMPhysicsScene implements Resettable {
    private final CellularNet[] nets;
    public NetPhysicsScene(CellularNet ...nets){
        this.nets = nets;
    }
    public CellularNet getNet(int id){return nets[id];}
    public CellularNet[] getNets(){return nets;}

    public void initialize(){
        for (CellularNet net : nets) {
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
    }

    public void reset(){
        super.reset();
        for (CellularNet net : nets) {
            net.reset();
        }
    }
}
