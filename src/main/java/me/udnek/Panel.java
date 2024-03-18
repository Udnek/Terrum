package me.udnek;

import me.udnek.scene.Scene;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private boolean screenIsOpen = true;
    private Scene scene;
    public Panel(){
        setBackground(Color.GRAY);

        scene = new Scene();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        super.paint(graphics);
        //System.out.println("DRAWCALL");
        graphics.drawImage(scene.renderFrame(this.getWidth(), this.getHeight()), 0, 0, null);

    }

    public void nextFrame(){
        repaint();
    }


    public void loop(){
        int fps = 20;

        int timeBetweenUpdate = (int) (Math.pow(10, 9) / fps);

        while (true) {
            long startTime = System.nanoTime();

            long nextFrameTime = startTime + timeBetweenUpdate;

            this.nextFrame();

            //long waitedInTotal = 0;
            while(System.nanoTime() < nextFrameTime) {
                    //waitedInTotal ++;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //System.out.println(waitedInTotal);
        }
    }


/*    @Override
    public void windowClosing(WindowEvent e) {
        screenIsOpen = false;
        frame.dispose();
    }*/

}
