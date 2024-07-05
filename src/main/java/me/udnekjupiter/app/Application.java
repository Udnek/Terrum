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

    ///////////////////////////////////////////////////////////////////////////
    // LOOPS
    ///////////////////////////////////////////////////////////////////////////

    private void physicTick(){
        applicationData.physicTickStarted();
        physicEngine.tick();
        applicationData.physicTickPerformed();
    }

    private void livePhysicLoop(){
        while (true){

            physicTick();

            while (System.nanoTime() < applicationData.estimatedNextTickTime){
                try {Thread.sleep(1);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    private void videoRenderLoop(){

        final int FPS = VideoRecorder.VIDEO_FPS;
        final int TPS = PHYSIC_TICKS_PER_SECOND;

        int physicTicks;
        int graphicTicks;

        if (TPS < FPS){
            physicTicks = 1;
            graphicTicks = Math.max(FPS / TPS, 1);
        } else {
            physicTicks = Math.max(TPS / FPS, 1);
            graphicTicks = 1;
        }

        int renderWidth = settings.videoWidth;
        int renderHeight = settings.videoHeight;

        while (true){

            for (int i = 0; i < physicTicks; i++) {
                physicTick();
            }

            for (int i = 0; i < graphicTicks; i++) {

                windowManager.tick();
                debugMenu.reset();
                addDebugInformation();

                BufferedImage rendered = graphicEngine.renderFrame(renderWidth, renderHeight);
                videoRecorder.addFrame(rendered);

                BufferedImage frame = Utils.resizeImage(rendered, windowManager.getWidth(), windowManager.getHeight());

                debugMenu.draw(frame, 15);
                windowManager.setFrame(frame);


                applicationData.framePerformed();
            }

        }
    }

    private void liveGraphicLoop(){
        while (true){
            windowManager.tick();
            debugMenu.reset();
            addDebugInformation();

            int width = windowManager.getWidth();
            int height = windowManager.getHeight();

            BufferedImage rendered = graphicEngine.renderFrame(width, height);
            BufferedImage frame = Utils.resizeImage(rendered, width, height);
            debugMenu.draw(frame, 15);
            windowManager.setFrame(frame);

            applicationData.framePerformed();
        }
    }

    public void start(){
        windowManager.initialize();
        physicEngine.initialize();
        graphicEngine.initialize();
        console.start();

        if (settings.recordVideo){
            Thread renderThread = new Thread(new Runnable() {
                @Override
                public void run() {videoRenderLoop();}
            });
            renderThread.setName("RenderThread");
            renderThread.start();

        } else {
            Thread physicThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    livePhysicLoop();}
            });
            physicThread.setName("PhysicLoop");
            Thread graphicThread = new Thread(new Runnable() {
                @Override
                public void run() {liveGraphicLoop();}
            });
            graphicThread.setName("GraphicLoop");

            physicThread.start();
            graphicThread.start();
        }

    }

    public void stop(){
        videoRecorder.save();
    }

    public void addDebugInformation(){
        if (!debugMenu.isEnabled()) return;
        debugMenu.addTextToRight("FPS: " + Utils.roundToPrecision(applicationData.averageFpsForLastTimes, 3));
        debugMenu.addTextToRight("RenderTime: " + Utils.roundToPrecision(applicationData.frameRenderTime, 5));
        debugMenu.addTextToRight("FramesRendered: " + applicationData.framesAmount);
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
