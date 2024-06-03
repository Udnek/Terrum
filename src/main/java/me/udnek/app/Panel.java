package me.udnek.app;

import me.udnek.scene.Camera;
import me.udnek.scene.Scene;
import me.udnek.utils.UserAction;
import org.decimal4j.util.DoubleRounder;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.realityforge.vecmath.Vector3d;

import javax.swing.*;
import java.awt.Frame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Panel extends JPanel {

    private boolean screenIsOpen = true;
    private Scene scene;
    private final Frame frame;
    private AWTSequenceEncoder videoEncoder;
    private final Settings settings;

    private boolean renderInProgress = false;
    public Panel(Frame frame, Scene scene, Settings settings){
        setBackground(Color.GRAY);
        this.frame = frame;
        this.scene = scene;
        this.settings = settings;
        if (settings.recordVideo){
            File file = new File(System.getProperty("user.dir") + "/src/main/assets/video/" + settings.videoName+".mp4");
            try {videoEncoder = AWTSequenceEncoder.createSequenceEncoder(file, 25);
            } catch (IOException e) {throw new RuntimeException(e);}
        }

    }

    @Override
    public void paint(Graphics graphics) {
        scene.tick();

        int renderWidth;
        int renderHeight;
        if (settings.recordVideo){
            renderWidth = settings.videoWidth;
            renderHeight = settings.videoHeight;
        }
        else {
            renderWidth = getWidth();
            renderHeight = getHeight();
        }


        BufferedImage frame = scene.renderFrame(renderWidth, renderHeight, settings.pixelScaling, settings.cores);

        if (settings.recordVideo){
            try {
                videoEncoder.encodeImage(frame);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        graphics.drawImage(frame, 0, 0, getWidth(), getHeight(), null);
        renderInProgress = false;
    }

    public void nextFrame(){
        renderInProgress = true;
        repaint();
    }

    public void onWindowClosed(){
        if (settings.recordVideo){
            try {
                videoEncoder.finish();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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
            Camera camera = scene.getCamera();
            Vector3d pos = camera.getPosition();
            frame.setTitle(
                    "FPS: "+DoubleRounder.round(1/renderTime, 2) +
                    " SPF: "+DoubleRounder.round(renderTime, 4) +
                    " ("+getWidth()+"x"+getHeight()+")" +
                    " x:"+ DoubleRounder.round(pos.x, 2) + " y:"+DoubleRounder.round(pos.y, 2) + " z:" + DoubleRounder.round(pos.z, 2) + " yaw:"+camera.getYaw() + " pitch:"+camera.getPitch()
                    );
            if (renderTime > 1) System.out.println("RenderTime: " + renderTime);
        }
    }

}
