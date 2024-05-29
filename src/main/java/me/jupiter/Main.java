package me.jupiter;

import me.jupiter.object.NetDynamicVertex;
import me.jupiter.object.NetStaticVertex;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        CellularNet net = new CellularNet(1, 1, System.getProperty("user.dir") + "/src/main/assets/image/chaos.png");

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

        System.out.println(net.netMap[0][1].getNeighbours().toString());
    }
}
