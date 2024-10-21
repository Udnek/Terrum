package me.udnekjupiter.graphic.engine.rasterization;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.window.WindowManager;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.frame.TransparentRasterizationFrame;
import me.udnekjupiter.graphic.object.renderable.RenderableObject;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

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

    public RasterizationEngine(GraphicScene3d graphicScene) {
        super(graphicScene);
        frame = new TransparentRasterizationFrame();
    }

    @Override
    public @NotNull BufferedImage renderFrame(final int rawWidth, final int rawHeight) {
        scene.beforeFrameUpdate(WindowManager.getInstance().getWidth(), WindowManager.getInstance().getHeight());

        width = Math.max(rawWidth / Application.APPLICATION_SETTINGS.pixelScaling, 1);
        height = Math.max(rawHeight / Application.APPLICATION_SETTINGS.pixelScaling, 1);

        frame.reset(width, height);

        //frame.setPixel(0, 3, WIREFRAME_COLOR);
        //frame.drawLine(0, 3, 0, 3, Color.RED.getRGB());
        //frame.drawLine(-4, -3, 0, 3, Color.WHITE.getRGB());
        //frame.drawTriangle(new Point(-4, -3), new Point(4, -3), new Point(0, 3), WIREFRAME_COLOR);
        //frame.drawLine(-4, -3, 0, 3, Color.WHITE.getRGB());


        camera = scene.getCamera();

        List<RenderableTriangle> polygons = new ArrayList<>();

        Vector3d cameraPosition = camera.getPosition();
        for (RenderableObject object : scene.getTraceableObjects()) {
            Vector3d objectPosition = object.getPosition();
            for (RenderableTriangle triangle : object.getRenderTriangles()) {
                triangle.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);
                camera.rotateBackTriangle(triangle);
                polygons.add(triangle);
            }
        }

        polygons.sort((o1, o2) -> Double.compare(o2.getCenter().z, o1.getCenter().z));

        polygons.forEach(this::drawTriangle);

        return frame.toImage();
    }


    public void drawTriangle(RenderableTriangle triangle){
        Point project0 = project(triangle.getVertex0());
        if (project0 == null) return;
        Point project1 = project(triangle.getVertex1());
        if (project1 == null) return;
        Point project2 = project(triangle.getVertex2());
        if (project2 == null) return;

        if (Application.APPLICATION_SETTINGS.drawPlanes) frame.drawTriangle(project0, project1, project2, triangle.getRasterizeColor());
        if (Application.APPLICATION_SETTINGS.drawWireframe) frame.drawTriangleWireframe(project0, project1, project2, WIREFRAME_COLOR);
    }

    public Point project(Vector3d pos){
        if (pos.z < 0.1) return null;
        double multiplier = 1 / pos.z * height / camera.getFov();
        int x = (int) Math.round(pos.x * multiplier);
        int y = (int) Math.round(pos.y * multiplier);


        return new Point(x, y);
    }
}
