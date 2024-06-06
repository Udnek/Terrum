package me.udnek.app;

import me.jupiter.file_managment.FileManager;
import me.udnek.app.console.Command;
import me.udnek.app.console.ConsoleHandler;
import me.udnek.scene.Camera;
import me.udnek.scene.Scene;
import me.udnek.util.UserAction;
import org.decimal4j.util.DoubleRounder;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.realityforge.vecmath.Vector3d;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Panel extends JPanel implements ConsoleHandler {

    private Scene scene;
    private final Frame frame;
    private AWTSequenceEncoder videoEncoder;
    private final DebugMenu debugMenu;

    private boolean mousePressed = false;
    private Point previousMouseLocation;

    private boolean renderInProgress = false;

    // STATS
    private int framesAmount = 0;
    private double fpsSum = 0;
    private double renderTime;
    private double averageFpsLastSecond;

    private final AppSettings settings = AppSettings.globalSettings;

    public Panel(Frame frame, Scene scene){
        setBackground(Color.GRAY);
        this.frame = frame;
        this.scene = scene;
        if (settings.recordVideo){
            File file = FileManager.readFile(FileManager.Directory.VIDEO, settings.videoName+".mp4");
            try {videoEncoder = AWTSequenceEncoder.createSequenceEncoder(file, 25);
            } catch (IOException e) {throw new RuntimeException(e);}
        }
        scene.init();
        this.debugMenu = new DebugMenu(15);

        //setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));

    }

    @Override
    public void paint(Graphics graphics) {
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
            } catch (IOException e) {throw new RuntimeException(e);}
        }

        graphics.drawImage(frame, 0, 0, getWidth(), getHeight(), null);

        if (debugMenu.isEnabled()){
            debugMenu.resetForNewFrame(graphics, getWidth());
            showDebug();
        }

        renderInProgress = false;
    }

    private void showDebug(){
        Camera camera = scene.getCamera();
        Vector3d position = camera.getPosition();
        debugMenu.addTextToRight(
                "FPS: "+DoubleRounder.round(averageFpsLastSecond, 2) +
                " SPF: "+DoubleRounder.round(renderTime, 4)
        );
        debugMenu.addTextToRight(
                "Cores: " + settings.cores + " Total: " + Runtime.getRuntime().availableProcessors()
        );
        debugMenu.addTextToRight(
                "x:"+ DoubleRounder.round(position.x, 2) +
                " y:" + DoubleRounder.round(position.y, 2) +
                " z:" + DoubleRounder.round(position.z, 2) +
                " yaw:" + DoubleRounder.round(camera.getYaw(), 2) +
                " pitch:" + DoubleRounder.round(camera.getPitch(), 2)
        );
        String[] extraDebug = scene.getExtraDebug();
        if (extraDebug == null) return;
        for (String text : extraDebug) {
            debugMenu.addTextToLeft(text);
        }
    }

    public void nextFrame(){
        scene.tick();

        if (mousePressed) mousePressedTick();

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
        System.out.println();
        System.out.println("Frames: "+ framesAmount);
        System.out.println("Avg FPS: "+ fpsSum/framesAmount);

    }
    public void handleKeyInput(UserAction userAction) {
        if (userAction == UserAction.DEBUG_MENU){
            debugMenu.toggle();
        }
        scene.handleUserInput(userAction);
    }
    @Override
    public void handleCommand(Command command, Object[] args) {
        scene.handleCommand(command, args);
        switch (command){
            case SET_DO_LIGHT -> settings.doLight = (boolean) args[0];
            case SET_CORES -> settings.cores = (int) args[0];
            case SET_PIXEL_SCALING -> settings.pixelScaling = (int) args[0];
            case SET_WINDOW_SIZE -> setPreferredSize((Integer) args[0], (Integer) args[1]);
        }
    }

    public void setPreferredSize(Integer width, Integer height) {
        setPreferredSize(new Dimension(width, height));
        frame.pack();
    }

    public void setMousePressed(boolean pressed){;
        this.mousePressed = pressed;
        if (pressed){
            previousMouseLocation = MouseInfo.getPointerInfo().getLocation();
        }
    }

    public void mousePressedTick(){
        Point newMouseLocation = MouseInfo.getPointerInfo().getLocation();
        int xDifference = newMouseLocation.x - previousMouseLocation.x;
        int yDifference = newMouseLocation.y - previousMouseLocation.y;
        previousMouseLocation = newMouseLocation;
        scene.handleMousePressedDifference(xDifference, yDifference);
    }

    public void loop(){
        double timeSinceAverageFpsUpdate = 0;
        double fpsSumLastTime = 0;
        double framesSinceAverageFpsUpdate = 0;
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

            timeSinceAverageFpsUpdate += renderTime;
            fpsSumLastTime += 1/renderTime;
            framesSinceAverageFpsUpdate++;

            if (timeSinceAverageFpsUpdate >= 0.5){
                averageFpsLastSecond = fpsSumLastTime/framesSinceAverageFpsUpdate;
                timeSinceAverageFpsUpdate = 0;
                fpsSumLastTime = 0;
                framesSinceAverageFpsUpdate = 0;
            }

            frame.setTitle("WRLS" + " ("+getWidth()+"x"+getHeight()+")");
            // stats
            fpsSum += 1/renderTime;
            framesAmount++;
        }
    }

}
