package me.udnekjupiter.graphic.frame;

public class LeftUpFrame extends GraphicFrame{
    @Override
    public boolean isInBounds(int x, int y){
        return 0 <= x && x < width && 0 <= y && y < height;
    }
    @Override
    public void setPixel(int x, int y, int color){
        int row = height-1-y;
        int column = x;
        data[row*width + column] = color;
    }
}
