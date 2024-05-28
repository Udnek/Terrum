package me.jupiter;

import me.jupiter.image_reader.NetMapReader;
import me.jupiter.object.NetDynamicVertex;
import me.jupiter.object.NetStaticVertex;

public class Main {
    public static void main(String[] args) {
        Net net = new Net();
        net.initiateNet();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (net.netMap[i][j] instanceof NetStaticVertex){
                    System.out.print("x ");
                } else if (net.netMap[i][j] instanceof NetDynamicVertex) {
                    System.out.print("o ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
