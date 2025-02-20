package me.udnekjupiter.app;

import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.ControllerListener;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.app.util.ApplicationData;
import me.udnekjupiter.app.util.ApplicationSettings;
import me.udnekjupiter.app.util.DebugMenu;
import me.udnekjupiter.app.util.VideoRecorder;
import me.udnekjupiter.app.window.Window;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.graphic.engine.raytrace.KernelRayTracingEngine;
import me.udnekjupiter.physic.engine.PhysicEngine;
import me.udnekjupiter.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class StandartApplication implements ConsoleListener, ControllerListener, Application {

    protected PhysicEngine<?> physicEngine;
    protected GraphicEngine graphicEngine;
    protected Window window;
    protected Console console;
    protected ApplicationData applicationData;
    protected VideoRecorder videoRecorder;
    protected final ApplicationSettings settings;
    protected DebugMenu debugMenu = new DebugMenu();
    protected boolean isRunning = false;

    public StandartApplication(@NotNull ApplicationSettings settings,
                               @NotNull GraphicEngine graphicEngine,
                               @NotNull PhysicEngine<?> physicEngine,
                               @NotNull Window window)
    {
        this.settings = settings;
        this.graphicEngine = graphicEngine;
        this.physicEngine = physicEngine;
        this.window = window;
    }

    @Override
    public double getFrameDeltaTime(){
        return (double) applicationData.frameRenderTime / Utils.NANOS_IN_SECOND;
    }

    @Override
    @NotNull
    public DebugMenu getDebugMenu() {return debugMenu;}

    @Override
    public @NotNull Window getWindow() {
        return window;
    }

    @Override
    @NotNull
    public ApplicationSettings getSettings() {return settings;}
    @Override
    public @NotNull PhysicEngine<?> getPhysicEngine() {return physicEngine;}

    @Override
    public void initialize(){
        console = Console.getInstance();
        applicationData = new ApplicationData();
        console.addListener(this);
        Controller.getInstance().addListener(this);
        videoRecorder = new VideoRecorder();
        videoRecorder.start(settings);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    ///////////////////////////////////////////////////////////////////////////
    // LOOPS
    ///////////////////////////////////////////////////////////////////////////

    protected void physicTick(){
        applicationData.physicTickStarted();
        physicEngine.tick();
        applicationData.physicTickPerformed();
    }

    protected void livePhysicLoop(){
        physicEngine.initialize();
        while (isRunning){
            physicTick();

            while (System.nanoTime() < applicationData.estimatedNextTickTime){
                try {Thread.sleep(1);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    protected void videoRenderLoop(){
        window.initialize();
        physicEngine.initialize();
        graphicEngine.initialize();


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

        while (isRunning){
            for (int i = 0; i < physicTicks; i++) {
                physicTick();
            }

            for (int i = 0; i < graphicTicks; i++) {
                window.tick();
                debugMenu.reset();
                addDebugInformation();

                BufferedImage rendered = graphicEngine.renderFrame(renderWidth, renderHeight);
                if (rendered != null){
                    videoRecorder.addFrame(rendered);
                    BufferedImage frame = Utils.resizeImage(rendered, window.getWidth(), window.getHeight());
                    graphicEngine.postVideoRender(frame);

                    window.setFrame(frame);
                }
                applicationData.framePerformed();
            }
        }
    }

    protected void liveGraphicLoop(){
        window.initialize();
        graphicEngine.initialize();
        while (isRunning){
            window.tick();
            debugMenu.reset();
            addDebugInformation();

            int width = window.getWidth();
            int height = window.getHeight();

            BufferedImage rendered = graphicEngine.renderFrame(width, height);
            if (rendered != null){
                graphicEngine.postVideoRender(rendered);
                window.setFrame(rendered);
            }
            applicationData.framePerformed();
        }
    }

    @Override
    public void start(){
        console.start();
        isRunning = true;

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
        isRunning = false;
        if (graphicEngine instanceof KernelRayTracingEngine kernel){
            kernel.stop();
        }
        videoRecorder.save();
        System.exit(0);
    }

    @Override
    public void addDebugInformation(){
        window.setTitle(Window.TITLE + " FPS: " + applicationData.averageFpsForLastTimes);

        if (!debugMenu.isEnabled()) return;

        debugMenu.addTextToRight("FPS: " + Utils.roundToPrecision(applicationData.averageFpsForLastTimes, 3));
        debugMenu.addTextToRight(
                "Cores: " + settings.cores + " Total Available: " + Runtime.getRuntime().availableProcessors()
        );
        debugMenu.addTextToRight(
                "Size: " + window.getWidth() + "x" + window.getHeight()
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
            case SET_WINDOW_SIZE -> window.setSize((int) args[0], (int) args[1]);
            case SET_DRAW_PLANES -> settings.drawPlanes = (boolean) args[0];
            case SET_DRAW_WIREFRAME -> settings.drawWireframe = (boolean) args[0];
        }
    }

    @Override
    public void keyEvent(@NotNull InputKey inputKey, boolean pressed) {
        if (inputKey == InputKey.DEBUG_MENU && pressed) debugMenu.toggle();
    }
}
