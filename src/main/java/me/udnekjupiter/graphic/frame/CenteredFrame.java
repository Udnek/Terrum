package me.udnekjupiter.graphic.frame;

public class CenteredFrame extends GraphicFrame {

    @Override
    public boolean isInBounds(int x, int y){
        return -width/2 <= x && x < width/2 && -height/2 <= y && y < height/2;
    }
    @Override
    public void setPixel(int x, int y, int color){
        x += width/2;
        y += height/2;
        int row = height-1-y;
        int column = x;
        data[row*width + column] = color;
    }
}
