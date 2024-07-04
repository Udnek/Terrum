package me.udnekjupiter.app;

import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.ControllerListener;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.app.window.WindowManager;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.physic.engine.PhysicEngine;
import me.udnekjupiter.util.Utils;

import java.awt.image.BufferedImage;

public class Application implements ConsoleListener, ControllerListener {

    // TODO: 7/4/2024 SOMETHING ABOUT TPS, IPT, DELTA TIME
    public static final int PHYSIC_TICKS_PER_SECOND = 40;
    private PhysicEngine physicEngine;
    private GraphicEngine graphicEngine;
    private WindowManager windowManager;
    private Console console;
    private ApplicationData applicationData;
    private ApplicationSettings settings;
    private VideoRecorder videoRecorder;
    public static final DebugMenu debugMenu = new DebugMenu();

    private static Application instance;

    private Application(){}

    public static double getFrameDeltaTime(){
        return getInstance().applicationData.frameRenderTime;
    }

    public static Application getInstance() {
        if (instance == null){
            instance = new Application();
        }
        return instance;
    }

    public void initialize(GraphicEngine graphicEngine, PhysicEngine physicEngine){
        windowManager = WindowManager.getInstance();

        this.graphicEngine = graphicEngine;
        this.physicEngine = physicEngine;
        this.console = Console.getInstance();
        this.applicationData = new ApplicationData();
        this.settings = ApplicationSettings.GLOBAL;
        console.addListener(this);
        Controller.getInstance().addListener(this);
        videoRecorder = new VideoRecorder();
        videoRecorder.start(settings);
    }

    private void physicLoop(){
        while (true){
            applicationData.physicTickStarted();

            physicEngine.tick();

            applicationData.physicTickPerformed();

            while (System.nanoTime() < applicationData.estimatedNextTickTime){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ignored) {}
            }



        }
    }

    private void graphicLoop(){
        while (true){
            windowManager.tick();
            debugMenu.reset();
            addDebugInformation();

            int renderWidth;
            int renderHeight;
            int textSize = 15;
            if (settings.recordVideo){
                renderWidth = settings.videoWidth;
                renderHeight = settings.videoHeight;
            }
            else {
                renderWidth = windowManager.getWidth();
                renderHeight = windowManager.getHeight();
            }


            BufferedImage rendered = graphicEngine.renderFrame(renderWidth, renderHeight);
            videoRecorder.addFrame(rendered);

            int windowWidth = windowManager.getWidth();
            int windowHeight = windowManager.getHeight();
            BufferedImage frame = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
            frame.getGraphics().drawImage(rendered, 0, 0, windowWidth, windowHeight, null);
            debugMenu.draw(frame, textSize);
            windowManager.setFrame(frame);

            applicationData.framePerformed();
        }
    }

    public void start(){
        windowManager.initialize();
        physicEngine.initialize();
        graphicEngine.initialize();
        console.start();

        Thread physicThread = new Thread(new Runnable() {
            @Override
            public void run() {physicLoop();}
        });
        physicThread.setName("PhysicLoop");
        Thread graphicThread = new Thread(new Runnable() {
            @Override
            public void run() {graphicLoop();}
        });
        graphicThread.setName("GraphicLoop");


        physicThread.start();
        graphicThread.start();

    }

    public void stop(){
        videoRecorder.save();
    }

    public void addDebugInformation(){
        if (!debugMenu.isEnabled()) return;
        debugMenu.addTextToRight("FPS: " + Utils.roundToPrecision(applicationData.averageFpsForLastTimes, 3));
        debugMenu.addTextToRight("RenderTime: " + Utils.roundToPrecision(applicationData.frameRenderTime, 5));
        debugMenu.addTextToRight(
                "Cores: " + settings.cores + " Total Available: " + Runtime.getRuntime().availableProcessors()
        );
        debugMenu.addTextToRight(
                "Size: " + windowManager.getWidth() + "x" + windowManager.getHeight()
        );
        debugMenu.addTextToRight("PixelScaling: " + settings.pixelScaling);

        debugMenu.addTextToRight("");

        double workingTime = (System.nanoTime() - applicationData.applicationStartTime) / Math.pow(10, 9);
        double physicTime = applicationData.physicTicks / (double)PHYSIC_TICKS_PER_SECOND;

        debugMenu.addTextToRight(
                "ApplicationWorkingTime: " +
                Utils.roundToPrecision(workingTime, 3)
        );

        debugMenu.addTextToRight("PhysicSeconds: " + Utils.roundToPrecision(physicTime, 3));
        debugMenu.addTextToRight("PhysicBehindTime: " + Utils.roundToPrecision(workingTime-physicTime, 3));
        debugMenu.addTextToRight("PhysicTicks: " + applicationData.physicTicks);


    }

    @Override
    public void handleCommand(Command command, Object[] args) {
        ApplicationSettings settings = ApplicationSettings.GLOBAL;
        switch (command){
            case SET_CORES -> settings.cores = (int) args[0];
            case SET_DO_LIGHT -> settings.doLight = (boolean) args[0];
            case SET_DEBUG_COLORIZE_PLANES -> settings.debugColorizePlanes = (boolean) args[0];
            case SET_PIXEL_SCALING -> settings.pixelScaling = (int) args[0];
            case SET_WINDOW_SIZE -> windowManager.setSize((int) args[0], (int) args[1]);
        }
    }

    @Override
    public void keyEvent(InputKey inputKey, boolean pressed) {
        if (inputKey == InputKey.DEBUG_MENU && pressed) debugMenu.toggle();
    }
}
