package me.udnek.app;

import me.udnek.scene.Camera;
import me.udnek.scene.Scene;
import me.udnek.util.UserAction;
import org.decimal4j.util.DoubleRounder;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.realityforge.vecmath.Vector3d;

import javax.swing.*;
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
    private final DebugMenu debugMenu;

    private boolean renderInProgress = false;

    // STATS
    private int framesAmount = 0;
    private double fpsSum = 0;
    private double renderTime;


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
        scene.init(settings.polygonHolderType);
        this.debugMenu = new DebugMenu(settings);

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

        if (debugMenu.isEnabled()){
            debugMenu.resetForNewFrame(frame);
            showDebug();
        }


        if (settings.recordVideo){
            try {
                videoEncoder.encodeImage(frame);
            } catch (IOException e) {throw new RuntimeException(e);}
        }

        graphics.drawImage(frame, 0, 0, getWidth(), getHeight(), null);
        renderInProgress = false;
    }

    private void showDebug(){
        Camera camera = scene.getCamera();
        Vector3d position = camera.getPosition();
        debugMenu.addText(
                "FPS: "+DoubleRounder.round(1/renderTime, 2) +
                " SPF: "+DoubleRounder.round(renderTime, 4)
        );
        debugMenu.addText(
                "x:"+ DoubleRounder.round(position.x, 2) +
                " y:" + DoubleRounder.round(position.y, 2) +
                " z:" + DoubleRounder.round(position.z, 2) +
                " yaw:" + camera.getYaw() +
                " pitch:" + camera.getPitch()
        );
        String[] extraDebug = scene.getExtraDebug();
        if (extraDebug == null) return;
        for (String text : extraDebug) {
            debugMenu.addText(text);
        }
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
        System.out.println("Frames: "+ framesAmount);
        System.out.println("Avg FPS: "+ fpsSum/framesAmount);

    }
    public void handleKeyInput(KeyEvent e) {
        UserAction userAction = UserAction.getByCode(e.getKeyCode());
        if (userAction == UserAction.DEBUG_MENU){
            debugMenu.toggle();
        }
        scene.handleUserInput(userAction);
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

            renderTime = (System.nanoTime() - startTime)/Math.pow(10, 9);

            frame.setTitle("WRLS" + " ("+getWidth()+"x"+getHeight()+")");
            if (renderTime > 1) System.out.println("RenderTime: " + renderTime);

            fpsSum += 1/renderTime;
            framesAmount++;
        }
    }

}
