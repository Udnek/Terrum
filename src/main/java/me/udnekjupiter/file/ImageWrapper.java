package me.udnekjupiter.file;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageWrapper {
    private BufferedImage image;
    public ImageWrapper(){}
    public void readImage(String imageName){
        image = FileManager.readMap(imageName);
    }

    public Color getColor(int x, int y){
        return new Color(image.getRGB(x, y));
    }
    public int getWidth(){
        return image.getData().getWidth();
    }
    public int getHeight(){
        return image.getData().getHeight();
    }

    public BufferedImage getImage() {return image;}
}
