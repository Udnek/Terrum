package me.udnek;

import me.udnek.scene.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.SplittableRandom;

public class Panel extends JPanel {

    private boolean screenIsOpen = true;
    private Scene scene;
    public Panel(){
        setBackground(Color.GRAY);

        scene = new Scene();
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawImage(scene.renderFrame(this.getWidth(), this.getHeight()), 0, 0, null);
    }

    public void nextFrame(){
        repaint();
    }


    public void loop(){

        int fps = 30;

        int timeBetweenUpdate = (int) (Math.pow(10, 9) / fps);

        while (true) {
            long startTime = System.nanoTime();
            long nextFrameTime = startTime + timeBetweenUpdate;
            this.nextFrame();

            while (System.nanoTime() < nextFrameTime) {
                try {
                    Thread.sleep(0, 1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
