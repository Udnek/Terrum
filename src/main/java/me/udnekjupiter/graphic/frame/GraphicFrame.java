package me.udnekjupiter.graphic.frame;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GraphicFrame {

    protected int[] data;
    protected int width;
    protected int height;

    public GraphicFrame(){}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public void reset(int width, int height){
        this.width = width;
        this.height = height;
        data = new int[width*height];
    }
    public int[] getData(){return data;}
    public abstract boolean isInBounds(int x, int y);
    public abstract void setPixel(int x, int y, int color);
    public void safeSetPixel(int x, int y, int color){
        if (!isInBounds(x, y)) return;
        setPixel(x, y, color);
    }
    ///////////////////////////////////////////////////////////////////////////
    // IMAGE
    ///////////////////////////////////////////////////////////////////////////

    public BufferedImage toImage(){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, width, height, data, 0, width);
        return bufferedImage;
    }
    public void drawOnImage(BufferedImage image) {
        image.setRGB(0, 0, image.getWidth(), image.getHeight(), data, 0, width);
    }

    ///////////////////////////////////////////////////////////////////////////
    // TOOLS
    ///////////////////////////////////////////////////////////////////////////

    public void drawLine(Point p0, Point p1, int color){
        drawLine(p0.x, p0.y, p1.x, p1.y, color);
    }
    public void drawLine(int x0, int y0, int x1, int y1, int color){
        if (!isInBounds(x0, y0) && !isInBounds(x1, y1)) return;

        float dx = x1 - x0;
        float dy = y1 - y0;
        int steps;
        if (Math.abs(dx) > Math.abs(dy)) {
            steps = (int) Math.abs(dx);
        } else{
            steps = (int) Math.abs(dy);
        }
        dx /= steps;
        dy /= steps;

        // UNSAFE DRAWING
        if (isInBounds(x0, y0) && isInBounds(x1, y1)){
            for (int i = 0; i < steps; i++) {
                setPixel((int) (x0 + dx*i), (int) (y0 + dy*i), color);
            }
        }
        // SAFE DRAWING
        else {
            for (int i = 0; i < steps; i++) {
                safeSetPixel((int) (x0 + dx*i), (int) (y0 + dy*i), color);
            }
        }


    }
    public void drawTriangleWireframe(Point p0, Point p1, Point p2, int color){
        drawLine(p0, p1, color);
        drawLine(p1, p2, color);
        drawLine(p2, p0, color);
    }

    public void drawTriangle(Point p0, Point p1, Point p2, int color){

        Point lowest = new Point(p0);
        Point middle = new Point(p1);
        Point highest = new Point(p2);


        if (lowest.y > middle.y) swapPoints(lowest, middle);
        if (middle.y > highest.y){
            swapPoints(middle, highest);
            if (lowest.y > middle.y) swapPoints(lowest, middle);
        }

        double dxLeft = (double) -(highest.x - lowest.x) / (highest.y - lowest.y);
        double dxRightH = (double) -(highest.x - middle.x) / (highest.y - middle.y);
        double dxRightL = (double) -(middle.x - lowest.x) / (middle.y - lowest.y);

        int ys = highest.y - middle.y;
        for (int i = 0; i < ys; i++) {
            int y = highest.y - i;
            drawLine((int) (highest.x + dxLeft * i), y, (int) (highest.x + dxRightH * i), y, color);
        }

        highest.x += (int) (dxLeft * ys);

        for (int i = 0; i < middle.y - lowest.y; i++) {
            int y = middle.y - i;
            drawLine((int) (highest.x + dxLeft * i), y, (int) (middle.x + dxRightL * i), y, color);
        }
    }

    protected void swapPoints(Point p0, Point p1) {
        int x0 = p0.x;
        int y1 = p0.y;
        p0.x = p1.x;
        p0.y = p1.y;

        p1.x = x0;
        p1.y = y1;
    }
}

















