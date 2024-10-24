package me.udnekjupiter.graphic.frame;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector4d;

public class LeftUpFrame extends GraphicFrame{

    private final Vector4d bounds = new Vector4d();

    @Override
    public boolean isInBounds(int x, int y){
        return 0 <= x && x < width && 0 <= y && y < height;
    }

    @Override
    public void reset(int width, int height) {
        super.reset(width, height);
        bounds.z = width-1;
        bounds.w = height-1;
    }

    @Override
    public @NotNull Vector4d getBounds() {
        return bounds;
    }


    @Override
    public int toPosition(int x, int y) {
        int row = height-1-y;
        return row*width + x;
    }


    @Override
    public void setPixel(int x, int y, int color){
        data[toPosition(x, y)] = color;
    }
}
