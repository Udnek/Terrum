//package me.udnekjupiter.physic.scene;
//
//import me.udnekjupiter.physic.net.CellularNet;
//import me.udnekjupiter.physic.object.vertex.NetVertex;
//
//import java.util.List;
//
//public class NetPhysicsScene extends PhysicScene3d {
//    private final CellularNet[] nets;
//
//    public NetPhysicsScene(CellularNet ...nets){
//        this.nets = nets;
//    }
//    public CellularNet getNet(int id){return nets[id];}
//    public CellularNet[] getNets(){return nets;}
//
//    public void initialize(){
//        for (CellularNet net : nets) {
//            net.initialize();
//            List<NetVertex> vertices = net.getVerticesObjects();
//            for (NetVertex vertex : vertices) {
//                if (vertex != null) {
//                    addObject(vertex);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void reset(){
//        for (CellularNet net : nets) {
//            net.reset();
//        }
//    }
//}
