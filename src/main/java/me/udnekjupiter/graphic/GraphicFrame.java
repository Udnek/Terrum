package me.udnekjupiter.graphic;

import java.awt.image.BufferedImage;

public class GraphicFrame {

    private int[] data;
    private int width;
    private int height;

    public GraphicFrame(){}

    public int getWidth() {return width;}

    public int getHeight() {return height;}

    public void reset(int width, int height){
        this.width = width;
        this.height = height;
        data = new int[width*height];
    }
    public boolean isInBounds(int x, int y){
        return 0 <= x && x < width && 0 <= y && y < height;
    }
    public void setPixel(int x, int y, int color){
        int row = height-1-y;
        int column = x;
        data[row*width + column] = color;
    }

    public void safeSetPixel(int x, int y, int color){
        if (!isInBounds(x, y)) return;
        setPixel(x, y, color);
    }
/*    public void setPixel(int x, int y, Color color){
        setPixel(x, y, color.getRGB());
    }*/

    public BufferedImage toImage(){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, width, height, data, 0, width);
        return bufferedImage;
    }
    public void drawOnImage(BufferedImage image){
        image.setRGB(0, 0, image.getWidth(), image.getHeight(), data, 0, image.getWidth());
    }

    public int[] getData(){return data;}
}
