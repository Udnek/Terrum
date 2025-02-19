package me.udnekjupiter.file;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class FileManager {

    public static @NotNull BufferedReader readText(@NotNull Directory directory, @NotNull String fileName){
        return new BufferedReader(new InputStreamReader(directory.getStream(fileName)));
    }

    public static @NotNull BufferedImage readImage(@NotNull Directory directory, @NotNull String imageName){
        BufferedImage image;
        try {
            image = ImageIO.read(directory.getURL(imageName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public static @NotNull BufferedImage readMap(@NotNull String imageName){
        return readImage(Directory.IMAGE_MAP, imageName);
    }
    public static @NotNull BufferedImage readIcon(){
        return readImage(Directory.IMAGE_ICON, "icon.png");
    }
    public static @NotNull BufferedImage readTexture(@NotNull String name){
        return readImage(Directory.TEXTURE, name);
    }


    public enum Directory{
        IMAGE_MAP("assets/image/map"),
        IMAGE_ICON("assets/image/icon"),
        KERNEL("assets/kernel"),
        TEXTURE("assets/image/texture");

        public final String path;
        Directory(String dir){path = dir;}

        public URL getURL(String fileName){
            return FileManager.class.getClassLoader().getResource(path + "/" + fileName);
        }
        public InputStream getStream(String fileName){
            return FileManager.class.getClassLoader().getResourceAsStream(path + "/" + fileName);
        }
    }
}
