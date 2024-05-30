package me.udnek;

import me.udnek.scene.Scene;
import me.udnek.utils.UserAction;

import javax.swing.*;
import java.awt.Frame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Panel extends JPanel {

    private boolean screenIsOpen = true;
    private Scene scene;
    private final Frame frame;

    private boolean renderInProgress = false;
    public Panel(Frame frame, Scene scene){
        setBackground(Color.GRAY);
        this.frame = frame;
        this.scene = scene;
    }

    @Override
    public void paint(Graphics graphics) {
        scene.tick();
        BufferedImage bufferedImage = scene.renderFrame(getWidth(), getHeight(), 2);
        graphics.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);
        renderInProgress = false;
    }

    public void nextFrame(){
        renderInProgress = true;
        repaint();
    }

    public void handleKeyInput(KeyEvent e) {
        scene.handleUserInput(UserAction.getByCode(e.getKeyCode()));
    }

    public void loop(){

        while (true) {
            long startTime = System.nanoTime();
            this.nextFrame();

            while (renderInProgress) {
                try {
                    Thread.sleep(1, 0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            double renderTime = (System.nanoTime() - startTime)/Math.pow(10, 9);
            frame.setTitle("RenderTime: " + renderTime);
            if (renderTime > 1) System.out.println("RenderTime: " + renderTime);
        }
    }

}
