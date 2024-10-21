package me.udnekjupiter.graphic.engine.raytrace;

import me.udnekjupiter.app.StandartApplication;
import me.udnekjupiter.app.window.WindowManager;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.ScreenTracer;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.frame.GraphicFrame;
import me.udnekjupiter.graphic.frame.LeftUpFrame;
import me.udnekjupiter.graphic.object.fixedsize.AxisCrosshair;
import me.udnekjupiter.graphic.polygonholder.DefaultPolygonHolder;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.polygonholder.SmartPolygonHolder;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.util.Collections;

public class RayTracingEngine extends GraphicEngine3d {

    protected RayTracer rayTracer;
    protected final GraphicFrame frame;
    protected PolygonHolder polygonHolder;
    protected final ScreenTracer screenTracer;
    protected final AxisCrosshair axisCrosshair = new AxisCrosshair();

    public RayTracingEngine(GraphicScene3d graphicScene){
        super(graphicScene);
        this.frame = new LeftUpFrame();
        this.screenTracer = new ScreenTracer();
    }

    @Override
    public void initialize() {
        super.initialize();

        rayTracer = new RayTracer(scene.getLightSource());
        if (StandartApplication.APPLICATION_SETTINGS.polygonHolderType == PolygonHolder.Type.SMART){
            polygonHolder = new SmartPolygonHolder(scene.getTraceableObjects(), scene.getCamera());
        } else {
            polygonHolder = new DefaultPolygonHolder(scene.getTraceableObjects(), scene.getCamera(), scene.getLightSource());
        }
    }

    @Override
    public @NotNull BufferedImage renderFrame(final int width, final int height){

        scene.beforeFrameUpdate(WindowManager.getInstance().getWidth(), WindowManager.getInstance().getHeight());

        int renderWidth = Math.max(width / StandartApplication.APPLICATION_SETTINGS.pixelScaling, 1);
        int renderHeight = Math.max(height / StandartApplication.APPLICATION_SETTINGS.pixelScaling, 1);

        frame.reset(renderWidth, renderHeight);
        polygonHolder.recacheObjects(renderWidth, renderHeight);

        Camera camera = scene.getCamera();
        rayTracer.renderFrame(frame, polygonHolder, camera);
        screenTracer.renderFrame(frame, scene.getFixedSizeObjects(), camera);

        if (StandartApplication.DEBUG_MENU.isEnabled()){
            axisCrosshair.setPosition(camera.getPosition().add(camera.getDirection()));
            screenTracer.renderFrame(frame, Collections.singletonList(axisCrosshair), camera);
        }

        return frame.toImage();
    }

}
