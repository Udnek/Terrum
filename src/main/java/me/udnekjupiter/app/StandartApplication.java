package me.udnekjupiter.app;

import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.ControllerListener;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.app.window.WindowManager;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.graphic.engine.raytrace.KernelRayTracingEngine;
import me.udnekjupiter.physic.engine.PhysicEngine;
import me.udnekjupiter.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class StandartApplication implements ConsoleListener, ControllerListener, Application {

    private PhysicEngine<?> physicEngine;
    private GraphicEngine graphicEngine;
    private WindowManager windowManager;
    private Console console;
    private ApplicationData applicationData;
    private VideoRecorder videoRecorder;
    private final ApplicationSettings settings;
    private final DebugMenu debugMenu = new DebugMenu();

    public StandartApplication(@NotNull ApplicationSettings settings){
        this.settings = settings;
    }

    @Override
    public double getFrameDeltaTime(){
        return (double) applicationData.frameRenderTime / Utils.NANOS_IN_SECOND;
    }

    @Override
    @NotNull
    public DebugMenu getDebugMenu() {return debugMenu;}
    @Override
    @NotNull
    public ApplicationSettings getSettings() {return settings;}
    @Override
    public @NotNull PhysicEngine<?> getPhysicEngine() {return physicEngine;}
    @Override
    public @NotNull GraphicEngine getGraphicEngine() {return graphicEngine;}

    @Override
    public void initialize(@NotNull GraphicEngine graphicEngine, @NotNull PhysicEngine<?> physicEngine){
        windowManager = WindowManager.getInstance();

        this.graphicEngine = graphicEngine;
        this.physicEngine = physicEngine;
        this.console = Console.getInstance();
        this.applicationData = new ApplicationData();
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
                graphicEngine.postVideoRender(frame);

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
            // TODO: 7/12/2024 MOVE DEBUG RENDER INTO GRAPHIC ENGINE
            debugMenu.draw(frame, 15);
            windowManager.setFrame(frame);

            applicationData.framePerformed();
        }
    }

    @Override
    public void start(){
        windowManager.initialize();
        physicEngine.initialize();
        graphicEngine.initialize();
        console.start();

        if (settings.recordVideo){
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

    @Override
    public void stop(){
        if (graphicEngine instanceof KernelRayTracingEngine kernel){
            kernel.stop();
        }
        videoRecorder.save();
    }

    @Override
    public void addDebugInformation(){
        if (!debugMenu.isEnabled()) return;

        debugMenu.addTextToRight("FPS: " + Utils.roundToPrecision(applicationData.averageFpsForLastTimes, 3));
        debugMenu.addTextToRight(
                "Cores: " + settings.cores + " Total Available: " + Runtime.getRuntime().availableProcessors()
        );
        debugMenu.addTextToRight(
                "Size: " + windowManager.getWidth() + "x" + windowManager.getHeight()
        );
        debugMenu.addTextToRight("RenderTime: " + Utils.roundToPrecision((double) applicationData.frameRenderTime / Utils.NANOS_IN_SECOND, 5));
        debugMenu.addTextToRight("FramesRendered: " + applicationData.framesAmount);
        debugMenu.addTextToRight("PixelScaling: " + settings.pixelScaling);

        debugMenu.addTextToRight("");

        double workingTime = (double) (System.nanoTime() - applicationData.applicationStartTime) / Utils.NANOS_IN_SECOND;
        double physicSeconds = (double) applicationData.physicTicks /PHYSIC_TICKS_PER_SECOND;

        debugMenu.addTextToRight(
                "ApplicationWorkingTime: " +
                Utils.roundToPrecision(workingTime, 3)
        );
        debugMenu.addTextToRight("PhysicTickTime: " + Utils.roundToPrecision((double) applicationData.physicTickTime /Utils.NANOS_IN_SECOND, 5));
        debugMenu.addTextToRight("PhysicSeconds: " + Utils.roundToPrecision(physicSeconds, 3));
        debugMenu.addTextToRight("PhysicBehindTime: " + Utils.roundToPrecision(workingTime-physicSeconds, 3));
        debugMenu.addTextToRight("PhysicTicks: " + applicationData.physicTicks);


    }

    @Override
    public void handleCommand(@NotNull Command command, Object[] args) {
        switch (command){
            case SET_CORES -> settings.cores = (int) args[0];
            case SET_DO_LIGHT -> settings.doLight = (boolean) args[0];
            case SET_DEBUG_COLORIZE_PLANES -> settings.debugColorizePlanes = (boolean) args[0];
            case SET_PIXEL_SCALING -> settings.pixelScaling = (int) args[0];
            case SET_WINDOW_SIZE -> windowManager.setSize((int) args[0], (int) args[1]);
            case SET_DRAW_PLANES -> settings.drawPlanes = (boolean) args[0];
            case SET_DRAW_WIREFRAME -> settings.drawWireframe = (boolean) args[0];
        }
    }

    @Override
    public void keyEvent(InputKey inputKey, boolean pressed) {
        if (inputKey == InputKey.DEBUG_MENU && pressed) debugMenu.toggle();
    }
}
