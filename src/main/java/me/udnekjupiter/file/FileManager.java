package me.udnekjupiter.file;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class FileManager {
/*    public static String getAssetsDirectory(){
        return System.getProperty("user.dir") + "/src/main/assets/";
    }*/
/*    public static File readAssetFile(Directory directory, String fileName){
        URL url = FileManager.class.getClassLoader().getResource(directory.path + "/" + fileName);
        File file;

        //file = Paths.get(url.toURI()).toFile();
        file = new File(url.toExternalForm());
        if (file.exists()) return file;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println(file.exists());
        return file ;
    }*/
    public static BufferedReader readKernel(Directory directory, String fileName){
        BufferedReader reader = new BufferedReader(new InputStreamReader(directory.getStream(fileName)));
        return reader;
    }


    private static BufferedImage readImage(Directory directory, String imageName){
        BufferedImage image;
        try {
            image = ImageIO.read(directory.getURL(imageName));
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
        IMAGE_ICON("assets/image/icon"),
        KERNEL("assets/kernel");
        //VIDEO("out/video");

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
