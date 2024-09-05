package me.udnekjupiter.app;

import me.udnekjupiter.Main;
import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.ControllerListener;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.app.window.WindowManager;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.graphic.engine.raytrace.KernelRayTracingEngine;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PhysicEngine;
import me.udnekjupiter.util.Utils;

import java.awt.image.BufferedImage;

public class Application implements ConsoleListener, ControllerListener {

    // sdTODO: 7/4/2024 SOMETHING ABOUT TPS, IPT, DELTA TIME
    public static final int PHYSIC_TICKS_PER_SECOND = 50;
    public static final DebugMenu DEBUG_MENU = new DebugMenu();
    public static final ApplicationSettings APPLICATION_SETTINGS = Main.getMain().initializeGraphicsSettings();
    public static final EnvironmentSettings ENVIRONMENT_SETTINGS = Main.getMain().initializePhysicsSettings();

    private PhysicEngine physicEngine;
    private GraphicEngine graphicEngine;
    private WindowManager windowManager;
    private Console console;
    private ApplicationData applicationData;
    private VideoRecorder videoRecorder;

    private static Application instance;

    private Application(){}

    public static double getFrameDeltaTime(){
        return (double) getInstance().applicationData.frameRenderTime / Utils.NANOS_IN_SECOND;
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
        console.addListener(this);
        Controller.getInstance().addListener(this);
        videoRecorder = new VideoRecorder();
        videoRecorder.start(APPLICATION_SETTINGS);
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

        int renderWidth = APPLICATION_SETTINGS.videoWidth;
        int renderHeight = APPLICATION_SETTINGS.videoHeight;

        while (true){

            for (int i = 0; i < physicTicks; i++) {
                physicTick();
            }

            for (int i = 0; i < graphicTicks; i++) {

                windowManager.tick();
                DEBUG_MENU.reset();
                addDebugInformation();

                BufferedImage rendered = graphicEngine.renderFrame(renderWidth, renderHeight);
                videoRecorder.addFrame(rendered);

                BufferedImage frame = Utils.resizeImage(rendered, windowManager.getWidth(), windowManager.getHeight());
                graphicEngine.postVideoRender(frame);

                windowManager.setFrame(frame);
                applicationData.framePerformed();
            }

        }
    }

    private void liveGraphicLoop(){
        while (true){
            windowManager.tick();
            DEBUG_MENU.reset();
            addDebugInformation();

            int width = windowManager.getWidth();
            int height = windowManager.getHeight();

            BufferedImage rendered = graphicEngine.renderFrame(width, height);
            BufferedImage frame = Utils.resizeImage(rendered, width, height);
            // TODO: 7/12/2024 MOVE DEBUG RENDER INTO GRAPHIC ENGINE
            DEBUG_MENU.draw(frame, 15);
            windowManager.setFrame(frame);

            applicationData.framePerformed();
        }
    }

    public void start(){
        windowManager.initialize();
        physicEngine.initialize();
        graphicEngine.initialize();
        console.start();

        if (APPLICATION_SETTINGS.recordVideo){
            Thread renderThread = new Thread(this::videoRenderLoop);
            renderThread.setName("RenderThread");
            renderThread.start();

        } else {
            Thread physicThread = new Thread(this::livePhysicLoop);
            physicThread.setName("PhysicLoop");
            Thread graphicThread = new Thread(this::liveGraphicLoop);
            graphicThread.setName("GraphicLoop");

            physicThread.start();
            graphicThread.start();
        }

    }

    public void stop(){
        if (graphicEngine instanceof KernelRayTracingEngine kernel){
            kernel.stop();
        }
        videoRecorder.save();
    }

    public void addDebugInformation(){
        if (!DEBUG_MENU.isEnabled()) return;

        DEBUG_MENU.addTextToRight("FPS: " + Utils.roundToPrecision(applicationData.averageFpsForLastTimes, 3));
        DEBUG_MENU.addTextToRight(
                "Cores: " + APPLICATION_SETTINGS.cores + " Total Available: " + Runtime.getRuntime().availableProcessors()
        );
        DEBUG_MENU.addTextToRight(
                "Size: " + windowManager.getWidth() + "x" + windowManager.getHeight()
        );
        DEBUG_MENU.addTextToRight("RenderTime: " + Utils.roundToPrecision((double) applicationData.frameRenderTime / Utils.NANOS_IN_SECOND, 5));
        DEBUG_MENU.addTextToRight("FramesRendered: " + applicationData.framesAmount);
        DEBUG_MENU.addTextToRight("PixelScaling: " + APPLICATION_SETTINGS.pixelScaling);

        DEBUG_MENU.addTextToRight("");

        double workingTime = (double) (System.nanoTime() - applicationData.applicationStartTime) / Utils.NANOS_IN_SECOND;
        double physicSeconds = (double) applicationData.physicTicks /PHYSIC_TICKS_PER_SECOND;

        DEBUG_MENU.addTextToRight(
                "ApplicationWorkingTime: " +
                Utils.roundToPrecision(workingTime, 3)
        );
        DEBUG_MENU.addTextToRight("PhysicTickTime: " + Utils.roundToPrecision((double) applicationData.physicTickTime /Utils.NANOS_IN_SECOND, 5));
        DEBUG_MENU.addTextToRight("PhysicSeconds: " + Utils.roundToPrecision(physicSeconds, 3));
        DEBUG_MENU.addTextToRight("PhysicBehindTime: " + Utils.roundToPrecision(workingTime-physicSeconds, 3));
        DEBUG_MENU.addTextToRight("PhysicTicks: " + applicationData.physicTicks);


    }

    @Override
    public void handleCommand(Command command, Object[] args) {
        switch (command){
            case SET_CORES -> APPLICATION_SETTINGS.cores = (int) args[0];
            case SET_DO_LIGHT -> APPLICATION_SETTINGS.doLight = (boolean) args[0];
            case SET_DEBUG_COLORIZE_PLANES -> APPLICATION_SETTINGS.debugColorizePlanes = (boolean) args[0];
            case SET_PIXEL_SCALING -> APPLICATION_SETTINGS.pixelScaling = (int) args[0];
            case SET_WINDOW_SIZE -> windowManager.setSize((int) args[0], (int) args[1]);
            case SET_DRAW_PLANES -> APPLICATION_SETTINGS.drawPlanes = (boolean) args[0];
            case SET_DRAW_WIREFRAME -> APPLICATION_SETTINGS.drawWireframe = (boolean) args[0];
        }
    }

    @Override
    public void keyEvent(InputKey inputKey, boolean pressed) {
        if (inputKey == InputKey.DEBUG_MENU && pressed) DEBUG_MENU.toggle();
    }
}
