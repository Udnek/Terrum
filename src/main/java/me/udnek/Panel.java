package me.udnek;

import me.udnek.scene.Camera;
import me.udnek.scene.Scene;
import me.udnek.utils.UserAction;
import org.realityforge.vecmath.Vector3d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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
