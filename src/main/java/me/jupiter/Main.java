package me.jupiter;

import me.jupiter.image_reader.NetMapReader;
import me.jupiter.object.NetDynamicVertex;
import me.jupiter.object.NetStaticVertex;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Net net = new Net(1, 1, "C:/Coding/images/theForm.png");

        Instant start = Instant.now();
        net.initiateNet();
        Instant end = Instant.now();
        System.out.println("Net initialization took " + Duration.between(start, end).toMillis() + " milliseconds");

        start = Instant.now();
        net.initiateNeighbours();
        end = Instant.now();
        System.out.println("Neighbours initialization took " + Duration.between(start, end).toMillis() + " milliseconds" + "\n");

        System.out.println(Arrays.deepToString(net.netMap[0][0].getNeighboursCoordinates()));
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
    }
}
