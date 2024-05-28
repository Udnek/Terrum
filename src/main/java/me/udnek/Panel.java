package me.udnek;

import me.udnek.scene.Camera;
import me.udnek.scene.Scene;
import me.udnek.utils.UserAction;
import org.realityforge.vecmath.Vector3d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Panel extends JPanel {

    private boolean screenIsOpen = true;
    private Scene scene;

    private boolean renderInProgress = false;
    public Panel(){
        setBackground(Color.GRAY);

        scene = new Scene();
    }

    @Override
    public void paint(Graphics graphics) {
        BufferedImage bufferedImage = scene.renderFrame(getWidth(), getHeight(), 4);
        graphics.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);
        renderInProgress = false;
    }

    public void nextFrame(){
        renderInProgress = true;
        repaint();
    }

    public void handleKeyInput(KeyEvent keyEvent){
        UserAction userAction = UserAction.getByCode(keyEvent.getKeyCode());
        Camera camera = scene.getCamera();
        final float moveSpeed = 0.05f;
        final float rotateSpeed = 1.5f;
        switch (userAction){
            case FORWARD -> camera.moveAlongDirection(new Vector3d(0, 0, moveSpeed));
            case BACKWARD -> camera.moveAlongDirection(new Vector3d(0, 0, -moveSpeed));
            case RIGHT -> camera.moveAlongDirection(new Vector3d(moveSpeed, 0, 0));
            case LEFT -> camera.moveAlongDirection(new Vector3d(-moveSpeed, 0, 0));

            case CAMERA_UP -> camera.rotatePitch(-rotateSpeed);
            case CAMERA_DOWN -> camera.rotatePitch(rotateSpeed);
            case CAMERA_RIGHT -> camera.rotateYaw(-rotateSpeed);
            case CAMERA_LEFT -> camera.rotateYaw(rotateSpeed);
        }

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

            System.out.println("RenderTime: " + ((System.nanoTime() - startTime)/Math.pow(10, 9)));
        }
    }
}
