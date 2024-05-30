//package me.jupiter;
//
//import me.jupiter.image_reader.ImageReader;
//import me.jupiter.net.CellularNet;
//import org.realityforge.vecmath.Vector3d;
//
//import static me.jupiter.image_reader.ImageReader.getImageDirectory;
//
//public class Simulator {
//    private CellularNet net;
//    private NetScene scene;
//    Simulator(){}
//    public void setupField(double springStiffness,
//                           double springRelaxedLength,
//                           double vertexMass,
//                           double deltaTime,
//                           String imageFileName){
//        net = new CellularNet(getImageDirectory() + imageFileName);
//        net.initiateNet();
//        net.initiateNeighbours();
//        net.setupVerticesVariables(springStiffness, springRelaxedLength, vertexMass, deltaTime);
//        scene = new NetScene(net);
//        scene.init();
//    }
//
//    public void setInitialDeviation(int x, int z, double yShift) {
//        net.getVertex(x, z).setPosition(new Vector3d(x, yShift, z));
//    }
//    public void runSimulation(int iterations){
//        for (int i = 0; i < iterations; i++) {
//            System.out.println(net.getVertex(4, 4).getPosition().asString());
//            net.updateVerticesPositionDifferentials();
//            net.updateVerticesPositions();
//            scene.tick();
//        }
//    }
//}
