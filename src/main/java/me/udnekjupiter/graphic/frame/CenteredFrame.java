package me.udnekjupiter.graphic.frame;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector4d;

public class CenteredFrame extends GraphicFrame {

    private final Vector4d bounds = new Vector4d();

    @Override
    public boolean isInBounds(int x, int y){
        return -width/2 < x && x <= width/2 && -height/2 < y && y <= height/2;
    }

    @Override
    public void reset(int width, int height) {
        super.reset(width, height);
        bounds.x = -width/2d+1;
        bounds.y = -height/2d+1;
        bounds.z = width/2d;
        bounds.w = height/2d;
    }

    @Override
    public @NotNull Vector4d getBounds() {
        return bounds;
    }

    @Override
    public int toPosition(int x, int y) {
        x += width/2 -1;
        y += height/2 -1;
        int row = height-1-y;
        return row*width + x;
    }
}
