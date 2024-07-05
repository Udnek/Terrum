package me.udnekjupiter.graphic.engine;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.app.window.WindowManager;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.GraphicFrame;
import me.udnekjupiter.graphic.RayTracer;
import me.udnekjupiter.graphic.ScreenTracer;
import me.udnekjupiter.graphic.object.fixedsize.AxisCrosshair;
import me.udnekjupiter.graphic.polygonholder.DefaultPolygonHolder;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.polygonholder.SmartPolygonHolder;
import me.udnekjupiter.graphic.scene.GraphicScene3d;

import java.awt.image.BufferedImage;
import java.util.Collections;

public class GraphicEngine3d implements GraphicEngine{

    private final GraphicScene3d scene;
    private RayTracer rayTracer;
    private final GraphicFrame frame;
    private PolygonHolder polygonHolder;
    private final ScreenTracer screenTracer;
    private final AxisCrosshair axisCrosshair = new AxisCrosshair();

    public GraphicEngine3d(GraphicScene3d graphicScene){
        this.scene = graphicScene;
        this.frame = new GraphicFrame();
        this.screenTracer = new ScreenTracer();
    }

    @Override
    public void initialize() {
        scene.initialize();
        rayTracer = new RayTracer(scene.getLightSource());
        if (ApplicationSettings.GLOBAL.polygonHolderType == PolygonHolder.Type.SMART){
            polygonHolder = new SmartPolygonHolder(scene.getTraceableObjects(), scene.getCamera());
        } else {
            polygonHolder = new DefaultPolygonHolder(scene.getTraceableObjects(), scene.getCamera(), scene.getLightSource());
        }
    }

    @Override
    public BufferedImage renderFrame(final int width, final int height){

        scene.beforeFrameUpdate(WindowManager.getInstance().getWidth(), WindowManager.getInstance().getHeight());

        int renderWidth = Math.max(width / ApplicationSettings.GLOBAL.pixelScaling, 1);
        int renderHeight = Math.max(height / ApplicationSettings.GLOBAL.pixelScaling, 1);

        frame.reset(renderWidth, renderHeight);
        polygonHolder.recacheObjects(width, height);

        Camera camera = scene.getCamera();
        rayTracer.renderFrame(frame, polygonHolder, camera);
        screenTracer.renderFrame(frame, scene.getFixedSizeObjects(), camera);

        if (Application.DEBUG_MENU.isEnabled()){
            axisCrosshair.setPosition(camera.getPosition().add(camera.getDirection()));
            screenTracer.renderFrame(frame, Collections.singletonList(axisCrosshair), camera);
        }

        return frame.toImage();
    }
}
