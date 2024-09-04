package me.udnekjupiter.graphic.engine.rasterization;

import java.util.Arrays;

@Deprecated
public class FlatRasterizationFrame extends RasterizationFrame {
    protected int[] depthBuffer;
    public void setPixel(int x, int y, int z, int color){
        x += width/2;
        y += height/2;
        int row = height-1-y;
        int pos = row * width + x;
        if (depthBuffer[pos] < z) return;
        depthBuffer[pos] = z;
        data[pos] = color;
    }
    @Override
    public void reset(int width, int height) {
        super.reset(width, height);
        depthBuffer = new int[width * height];
        Arrays.fill(depthBuffer, 100);
    }
}
