package me.udnekjupiter.graphic.engine;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.window.WindowManager;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.frame.CenteredFrame;
import me.udnekjupiter.graphic.frame.GraphicFrame;
import me.udnekjupiter.graphic.object.renderable.RenderableObject;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.awt.image.BufferedImage;

;

// TODO: 7/12/2024 DEPTH BUFFER
public class RasterizationEngine extends GraphicScene3dEngine {

    public static final int WIREFRAME_COLOR = Color.CYAN.getRGB();
    public static final boolean DRAW_WIREFRAME = true;
    public static final boolean DRAW_WIREFRAME_ONLY = true;

    private final GraphicFrame frame;
    private int width;
    private int height;
    private Camera camera;

    public RasterizationEngine(GraphicScene3d graphicScene) {
        super(graphicScene);
        frame = new CenteredFrame();
    }

    @Override
    public void initialize() {
        scene.initialize();
    }

    @Override
    public BufferedImage renderFrame(final int rawWidth, final int rawHeight) {
        scene.beforeFrameUpdate(WindowManager.getInstance().getWidth(), WindowManager.getInstance().getHeight());

        this.width = Math.max(rawWidth / Application.APPLICATION_SETTINGS.pixelScaling, 1);
        this.height = Math.max(rawHeight / Application.APPLICATION_SETTINGS.pixelScaling, 1);

        frame.reset(width, height);

        Camera camera = scene.getCamera();
        this.camera = camera;

        Vector3d cameraPosition = camera.getPosition();
        for (RenderableObject object : scene.getTraceableObjects()) {
            Vector3d objectPosition = object.getPosition();
            for (RenderableTriangle triangle : object.getRenderTriangles()) {
                triangle.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);
                drawTriangle(triangle);
            }
        }
        return frame.toImage();
    }


    public void drawTriangle(RenderableTriangle triangle){
        Point project0 = project(camera.rotateBackVector(triangle.getVertex0()));
        if (project0 == null) return;
        Point project1 = project(camera.rotateBackVector(triangle.getVertex1()));
        if (project1 == null) return;
        Point project2 = project(camera.rotateBackVector(triangle.getVertex2()));
        if (project2 == null) return;
        if (DRAW_WIREFRAME){
            frame.drawTriangleWireframe(project0, project1, project2, WIREFRAME_COLOR);
        }
        if (!DRAW_WIREFRAME_ONLY){
            frame.drawTriangle(project0, project1, project2, triangle.getRasterizeColor());
        }
    }

    public Point project(Vector3d pos){
        if (pos.z < 0.1) return null;
        double multiplier = 1 / pos.z * height / camera.getFov();
        int x = (int) (pos.x * multiplier);
        int y = (int) (pos.y * multiplier);

        return new Point(x, y);
    }
}
