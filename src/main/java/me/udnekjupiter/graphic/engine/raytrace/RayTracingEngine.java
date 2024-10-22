package me.udnekjupiter.graphic.engine.raytrace;

import me.udnekjupiter.Main;
import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.app.window.WindowManager;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.frame.GraphicFrame;
import me.udnekjupiter.graphic.frame.LeftUpFrame;
import me.udnekjupiter.graphic.polygonholder.DefaultPolygonHolder;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.polygonholder.SmartPolygonHolder;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class RayTracingEngine extends GraphicEngine3d {

    protected RayTracer rayTracer;
    protected final GraphicFrame frame;
    protected PolygonHolder polygonHolder;

    public RayTracingEngine(@NotNull GraphicScene3d graphicScene){
        super(graphicScene);
        this.frame = new LeftUpFrame();
    }

    @Override
    public void initialize() {
        super.initialize();

        rayTracer = new RayTracer(scene.getLightSource());
        if (Main.getMain().getApplication().getSettings().polygonHolderType == PolygonHolder.Type.SMART){
            polygonHolder = new SmartPolygonHolder(scene.getTraceableObjects(), scene.getCamera());
        } else {
            polygonHolder = new DefaultPolygonHolder(scene.getTraceableObjects(), scene.getCamera(), scene.getLightSource());
        }
    }

    @Override
    public @NotNull BufferedImage renderFrame(final int width, final int height){
        ApplicationSettings settings = Main.getMain().getApplication().getSettings();
        scene.beforeFrameUpdate(WindowManager.getInstance().getWidth(), WindowManager.getInstance().getHeight());

        int renderWidth = Math.max(width / settings.pixelScaling, 1);
        int renderHeight = Math.max(height / settings.pixelScaling, 1);

        frame.reset(renderWidth, renderHeight);
        polygonHolder.recacheObjects(renderWidth, renderHeight);

        Camera camera = scene.getCamera();
        rayTracer.renderFrame(frame, polygonHolder, camera);

        return frame.toImage();
    }

}
