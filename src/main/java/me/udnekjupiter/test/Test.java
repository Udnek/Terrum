package me.udnekjupiter.test;

import java.awt.*;

public class Test {
    public static void run() {
        Color color = new Color(new Color(255, 0, 0, 128).getRGB(), true);
        System.out.println(color.getRGB());
        System.out.println(color.getRed());
        System.out.println(color.getGreen());
        System.out.println(color.getBlue());
        System.out.println(color.getAlpha());
    }

}
