package me.jupiter.image_reader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NetMapReader {
    private BufferedImage image;
    public NetMapReader(){}
    public void readNetMap(String imagePath){
        try {
            image = ImageIO.read(new File(imagePath));
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
}
