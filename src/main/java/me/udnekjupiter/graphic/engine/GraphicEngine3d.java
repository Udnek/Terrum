package me.udnekjupiter.graphic.engine;

import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.RayTracer;
import me.udnekjupiter.graphic.scene.GraphicScene3d;

import java.awt.image.BufferedImage;

public class GraphicEngine3d implements GraphicEngine{

    private final GraphicScene3d graphicScene;
    private RayTracer rayTracer;

    protected int width = 1;
    protected int height = 1;

    public GraphicEngine3d(GraphicScene3d graphicScene){
        this.graphicScene = graphicScene;
    }

    @Override
    public void initialize() {
        graphicScene.initialize();
        rayTracer = new RayTracer(graphicScene.getCamera(), graphicScene.getObjects(), graphicScene.getLightSource());
    }

    @Override
    public BufferedImage renderFrame(final int width, final int height){
        graphicScene.beforeFrameUpdate(width, height);

        int renderWidth = Math.max(width / ApplicationSettings.GLOBAL.pixelScaling, 1);
        int renderHeight = Math.max(height / ApplicationSettings.GLOBAL.pixelScaling, 1);

        BufferedImage bufferedImage = new BufferedImage(renderWidth, renderHeight, BufferedImage.TYPE_INT_RGB);

        int[] frame = rayTracer.renderFrame(renderWidth, renderHeight);

        bufferedImage.setRGB(0, 0, renderWidth, renderHeight, frame, 0, renderWidth);
        return bufferedImage;
    }
}
