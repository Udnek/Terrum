package me.udnekjupiter.file;

import me.udnekjupiter.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileManager {
/*    public static String getAssetsDirectory(){
        return System.getProperty("user.dir") + "/src/main/assets/";
    }*/
    public static File readAssetFile(Directory directory, String fileName){
        URL resource = Main.class.getClassLoader().getResource(directory.path + "/" + fileName);
        File file = new File(resource.getFile());
/*        File fullDirectory = new File(getAssetsDirectory() + directory.path);
        if (!fullDirectory.exists()){
            fullDirectory.mkdir();
        }*/

        return file;
    }

    private static BufferedImage readImage(Directory directory, String imageName){
        BufferedImage image;
        try {
            image = ImageIO.read(FileManager.readAssetFile(directory, imageName));
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
        IMAGE_MAP("assets/image/map"),
        IMAGE_ICON("assets/image/icon");
        //VIDEO("out/video");

        public final String path;
        Directory(String dir){
            path = dir;
        }
    }
}
