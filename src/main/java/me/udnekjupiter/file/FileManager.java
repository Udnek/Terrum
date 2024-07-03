package me.udnekjupiter.file;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileManager {
    public static String getAssetsDirectory(){
        return System.getProperty("user.dir") + "/src/main/assets/";
    }
    public static File readFile(Directory directory, String fileName){
        File fullDirectory = new File(getAssetsDirectory() + directory.path);
        if (!fullDirectory.exists()){
            fullDirectory.mkdir();
        }
        File file = new File(getAssetsDirectory() + directory.path + "/" + fileName);
        return file;
    }

    private static BufferedImage readImage(Directory directory, String imageName){
        BufferedImage image;
        try {
            image = ImageIO.read(FileManager.readFile(directory, imageName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public static BufferedImage readMap(String imageName){
        return readImage(Directory.IMAGE_MAP, imageName);
    }
    public static BufferedImage readIcon(){
        return readImage(Directory.IMAGE_ICON, "icon.png");
    }



    public enum Directory{
        IMAGE_MAP("image/map"),
        IMAGE_ICON("image/icon"),
        VIDEO("video");

        public final String path;
        Directory(String dir){
            path = dir;
        }
    }
}
