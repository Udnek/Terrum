package me.udnekjupiter.graphic.engine.rasterization;

import me.udnekjupiter.Main;
import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.util.ApplicationSettings;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.frame.TransparentRasterizationFrame;
import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.Vector3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class RasterizationEngine extends GraphicEngine3d {

    public static final int WIREFRAME_COLOR = Color.CYAN.getRGB();
    private final TransparentRasterizationFrame frame;
    private int width;
    private int height;
    private Camera camera;
    private ApplicationSettings settings;

    public RasterizationEngine(GraphicScene3d graphicScene) {
        super(graphicScene);
        frame = new TransparentRasterizationFrame();
    }

    @Override
    public void initialize() {
        super.initialize();
        settings = Main.getMain().getApplication().getSettings();
    }

    @Override
    public @NotNull BufferedImage renderFrame(final int rawWidth, final int rawHeight) {
        Application application = Main.getMain().getApplication();
        ApplicationSettings settings = application.getSettings();
        scene.beforeFrameUpdate(application.getWindow().getWidth(), application.getWindow().getHeight());

        width = Math.max(rawWidth / settings.pixelScaling, 1);
        height = Math.max(rawHeight / settings.pixelScaling, 1);

        frame.reset(width, height);

        camera = scene.getCamera();

        List<RenderableTriangle> polygons = new ArrayList<>();

        Vector3d cameraPosition = camera.getPosition();
        for (GraphicObject3d object : scene.getObjects()) {
            Vector3d objectPosition = object.getPosition();
            object.getRenderTriangles(triangle -> {
                triangle.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);
                camera.rotateBackTriangle(triangle);
                polygons.add(triangle);
            });
        }

        polygons.sort((o1, o2) -> Double.compare(o2.getCenter().z, o1.getCenter().z));
        polygons.forEach(this::drawTriangle);

        return frame.toImage();
    }


    public void drawTriangle(@NotNull RenderableTriangle triangle){
        Point project0 = project(triangle.getVertex0());
        if (project0 == null) return;
        Point project1 = project(triangle.getVertex1());
        if (project1 == null) return;
        Point project2 = project(triangle.getVertex2());
        if (project2 == null) return;

        if (settings.drawPlanes) frame.drawTriangle(project0, project1, project2, triangle.getRasterizeColor());
        if (settings.drawWireframe) frame.drawTriangleWireframe(project0, project1, project2, WIREFRAME_COLOR);
    }

    public @Nullable Point project(@NotNull Vector3d pos){
        if (pos.z < 0.1) return null;
        double multiplier = 1d / pos.z * height / camera.getFov();
        int x = (int) Math.round(pos.x * multiplier);
        int y = (int) Math.round(pos.y * multiplier);
        return new Point(x, y);
    }
}
