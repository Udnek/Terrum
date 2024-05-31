package me.jupiter.image_reader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageReader {
    private BufferedImage image;
    public ImageReader(){}
    public void readImage(String imageName){
        try {
            image = ImageIO.read(new File(getImageDirectory() + imageName));
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

    public static String getImageDirectory(){
        return System.getProperty("user.dir") + "/src/main/assets/image/";
    }
}
