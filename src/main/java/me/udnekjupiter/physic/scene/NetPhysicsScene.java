package me.udnekjupiter.physic.scene;

import me.udnekjupiter.app.StandartApplication;
import me.udnekjupiter.physic.core.EulerCore;
import me.udnekjupiter.physic.core.PhysicCore;
import me.udnekjupiter.physic.core.RKMCore;
import me.udnekjupiter.physic.net.CellularNet;
import me.udnekjupiter.physic.object.StandardObject3d;
import me.udnekjupiter.physic.object.vertex.NetVertex;

import java.util.List;

public class NetPhysicsScene extends PhysicScene3d {
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

            for (StandardObject3d object : getAllObjects()) {
                if (!(object instanceof NetVertex)){
                    addCollisionInitiator(object);
                }
            }
        }
    }

    @Override
    public void reset(){
        for (CellularNet net : nets) {
            net.reset();
        }
    }
}
