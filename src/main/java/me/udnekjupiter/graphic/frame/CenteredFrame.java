package me.udnekjupiter.graphic.frame;

public class CenteredFrame extends GraphicFrame {

    @Override
    public boolean isInBounds(int x, int y){
        return -width/2 < x && x <= width/2 && -height/2 < y && y <= height/2;
    }

    @Override
    public int toPosition(int x, int y) {
        x += width/2 -1;
        y += height/2 -1;
        int row = height-1-y;
        return row*width + x;
    }
}
