package me.jupiter.file_managment;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageWrapper {
    private BufferedImage image;
    public ImageWrapper(){}
    public void readImage(String imageName){
        try {
            image = ImageIO.read(FileManager.readFile(FileManager.Directory.IMAGE, imageName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
