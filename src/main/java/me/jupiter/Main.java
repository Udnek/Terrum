package me.jupiter;

import me.jupiter.image_reader.NetMapReader;

public class Main {
    public static void main(String[] args) {
        NetMapReader test = new NetMapReader();
        test.readNetMap("C:/Coding/images/test_highNetMap.png");
        System.out.println(test.getWidth());
    }
}
