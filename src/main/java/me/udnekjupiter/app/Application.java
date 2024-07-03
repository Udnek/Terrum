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
import org.decimal4j.util.DoubleRounder;

import java.awt.image.BufferedImage;

public class Application implements ConsoleListener, ControllerListener {

    private PhysicEngine physicEngine;
    private GraphicEngine graphicEngine;
    private WindowManager windowManager;
    private Console console;
    private DebugData debugData;
    private ApplicationSettings settings;
    private VideoRecorder videoRecorder;
    // TODO: 7/3/2024 TEXT SIZE DEPENDS ON PIXEL SCALING
    public static final DebugMenu debugMenu = new DebugMenu(13);
    private boolean running = false;

    private static Application instance;

    private Application(){}

    public static double getFrameDeltaTime(){
        return getInstance().debugData.renderTime;
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
        this.debugData = new DebugData();
        this.settings = ApplicationSettings.GLOBAL;
        console.addListener(this);
        Controller.getInstance().addListener(this);
        videoRecorder = new VideoRecorder();
        videoRecorder.start(settings);
    }

    private void loop(){
        while (true){
            debugMenu.reset();
            addDebugInformation();
            physicEngine.tick();

            // TODO: 7/3/2024 DRAW DEBUG MENU ON SCREEN, NOT FRAME

            BufferedImage frame;
            if (settings.recordVideo){
                frame = graphicEngine.renderFrame(
                        settings.videoWidth,
                        settings.videoHeight);
                debugMenu.draw(frame);
                windowManager.setFrame(frame);
            }
            else {
                frame = graphicEngine.renderFrame(
                        windowManager.getWidth(),
                        windowManager.getHeight());
                debugMenu.draw(frame);
                windowManager.setFrame(frame);
               //windowManager.setFrame(frame);
                //debugMenu.draw(windowManager.getGraphics(), windowManager.getWidth(), windowManager.getHeight());
            }


            videoRecorder.addFrame(frame);
            debugData.framePerformed();

/*
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
        }
    }

    public void start(){
        windowManager.initialize();
        physicEngine.initialize();
        graphicEngine.initialize();
        console.start();

        running = true;
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                loop();
            }
        });
        thread.setName("MainLoop");
        thread.start();
    }

    public void stop(){
        running = false;
        videoRecorder.save();
    }

    public void addDebugInformation(){
        if (!debugMenu.isEnabled()) return;
        debugMenu.addTextToRight("FPS: " + DoubleRounder.round(debugData.averageFpsForLastTimes, 3));
        debugMenu.addTextToRight("RenderTime: " + DoubleRounder.round(debugData.renderTime, 5));
        debugMenu.addTextToRight(
                "Cores: " + settings.cores + " Total Available: " + Runtime.getRuntime().availableProcessors()
        );
        debugMenu.addTextToRight(
                "Size: " + windowManager.getWidth() + "x" + windowManager.getHeight()
        );
        debugMenu.addTextToRight("PixelScaling: " + settings.pixelScaling);
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
