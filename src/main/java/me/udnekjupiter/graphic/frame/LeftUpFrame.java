package me.udnekjupiter.graphic.frame;

public class LeftUpFrame extends GraphicFrame{
    @Override
    public boolean isInBounds(int x, int y){
        return 0 <= x && x < width && 0 <= y && y < height;
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
