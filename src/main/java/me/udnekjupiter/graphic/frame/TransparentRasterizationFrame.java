package me.udnekjupiter.graphic.frame;

import java.awt.*;

public class TransparentRasterizationFrame extends RasterizationFrame {

    @Override
    public void setPixel(int x, int y, int z, int color) {
        x += width/2;
        y += height/2;
        int row = height-1-y;
        int pos = row * width + x;

        int alpha = getAlpha(color);
        if (alpha < 255){
            data[pos] = blend(data[pos], color, alpha);
        } else {
            data[pos] = color;
        }


    }
    public int getAlpha(int color){
        return new Color(color, true).getAlpha();
    }
    public int blend(int c0, int c1, int alpha){
        Color base = new Color(c0);
        Color overlap = new Color(c1);
        float overMul = alpha / 255f;
        float baseMul = 1 - overMul;

        return new Color(
                ((int) (base.getRed() * baseMul + overlap.getRed() * overMul)),
                ((int) (base.getGreen() * baseMul + overlap.getGreen() * overMul)),
                ((int) (base.getBlue() * baseMul + overlap.getBlue() * overMul))
        ).getRGB();
    }
    @Override
    public void reset(int width, int height) {
        super.reset(width, height);
    }
}























