package me.udnekjupiter.graphic.engine.rasterization;

import me.udnekjupiter.graphic.frame.CenteredFrame;

public class RasterizationFrame extends CenteredFrame {

    protected int[] depthBuffer;
    @Override
    public void reset(int width, int height) {
        super.reset(width, height);
        depthBuffer = new int[width*height];
    }

}
