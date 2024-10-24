package me.udnekjupiter.graphic.frame;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector4d;

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
    public abstract boolean isInBounds(int x, int y);
    public boolean isInBounds(@NotNull Point point){return isInBounds(point.x, point.y);}
    public abstract @NotNull Vector4d getBounds();
    public abstract int toPosition(int x, int y);
    public void setPixel(int x, int y, int color){
        data[toPosition(x, y)] = color;
    }
    public void safeSetPixel(int x, int y, int color){
        if (isInBounds(x, y)) setPixel(x, y, color);
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
        }
        else {
            steps = (int) Math.abs(dy);
        }

        if (steps == 0) steps = 1;

        dx /= steps;
        dy /= steps;
/*
        if (x0 < bounds.x){
            y0 += (int) ((bounds.x - x0) * dy);
            x0 = (int) bounds.x;}
        else if (x0 > bounds.z){
            y0 += (int) ((x0 - bounds.x) * dy);
            x0 = (int) bounds.x;}

        if (y0 < bounds.y){

        else if (y0 > bounds.w){*/


        // UNSAFE DRAWING
        if (isInBounds(x0, y0) && isInBounds(x1, y1)){
            for (int i = 0; i <= steps; i++) {
                setPixel((int) (x0 + dx*i), (int) (y0 + dy*i), color);
            }
        }
        // SAFE DRAWING
        else {
            for (int i = 0; i <= steps; i++) {
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
        //if (!(isInBounds(p0) && isInBounds(p1) && isInBounds(p2))) return;

/*        final int boundExtend = 100;

        Vector4d bounds = getBounds();
        int minBound = (int) (Math.min(bounds.x, bounds.y) -boundExtend);
        int maxBound = (int) (Math.max(bounds.z, bounds.w) +boundExtend);

        Point lowest = Utils.clamp(new Point(p0), minBound, maxBound);
        Point middle = Utils.clamp(new Point(p1), minBound, maxBound);
        Point highest = Utils.clamp(new Point(p2), minBound, maxBound);*/

        Point lowest = new Point(p0);
        Point middle = new Point(p1);
        Point highest = new Point(p2);

        if (lowest.y > middle.y) swapPoints(lowest, middle);
        if (middle.y > highest.y){
            swapPoints(middle, highest);
            if (lowest.y > middle.y) swapPoints(lowest, middle);
        }

        double dxLeft = (double) -(highest.x - lowest.x) / Math.max(highest.y - lowest.y, 1);
        double dxRightH = (double) -(highest.x - middle.x) / Math.max(highest.y - middle.y, 1);
        double dxRightL = (double) -(middle.x - lowest.x) / Math.max(middle.y - lowest.y, 1);


        int ys = highest.y - middle.y;
        int y = highest.y;
        for (int step = 0; step <= ys; step++) {
            drawLine((int) (highest.x + dxLeft * step), y, (int) (highest.x + dxRightH * step), y, color);
            y--;
        }


        ys = middle.y - lowest.y;
        y = lowest.y;
        for (int step = 0; step < ys; step++) {
            drawLine((int) (lowest.x -dxLeft*step), y, (int) (lowest.x -dxRightL*step), y, color);
            y++;
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

















