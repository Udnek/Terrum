package me.jupiter;

import me.jupiter.image_reader.ImageReader;
import me.jupiter.net.CellularNet;
import me.jupiter.object.NetDynamicVertex;
import me.jupiter.object.NetStaticVertex;
import org.realityforge.vecmath.Vector3d;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        CellularNet net = new CellularNet(
                1,
                1,
                ImageReader.getImageDirectory() + "frame.png");


        Instant start = Instant.now();
        net.initiateNet();
        Instant end = Instant.now();
        System.out.println("Net initialization took " + Duration.between(start, end).toMillis() + " milliseconds");

        start = Instant.now();
        net.initiateNeighbours();
        end = Instant.now();
        System.out.println("Neighbours initialization took " + Duration.between(start, end).toMillis() + " milliseconds" + "\n");

        for (int i = 0; i < net.getSizeZ(); i++) {
            for (int j = 0; j < net.getSizeX(); j++) {
                if (net.netMap[i][j] instanceof NetStaticVertex){
                    System.out.print("[ ]");
                } else if (net.netMap[i][j] instanceof NetDynamicVertex) {
                    System.out.print(" - ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }

        //System.out.println(net.netMap[0][1].getNeighbours().toString());
        if (net.netMap[1][1] instanceof  NetDynamicVertex) {
            net.netMap[1][1].setPosition(new Vector3d(1, -5, 1));
            for (int i = 0; i < 1000; i++) {
                System.out.println((net.netMap[1][1]).getPosition().asString());
                ((NetDynamicVertex) net.netMap[1][1]).calculatePositionDifferential(0.01);
                ((NetDynamicVertex) net.netMap[1][1]).updatePosition();
                //System.out.println(((NetDynamicVertex) net.netMap[1][1]).getNormalizedDirection(net.netMap[0][0].getPosition(), net.netMap[1][1].getPosition()).asString());
            }
        }
    }
}
