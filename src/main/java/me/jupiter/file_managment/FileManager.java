package me.jupiter.file_managment;

import java.io.File;

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

    public enum Directory{
        IMAGE("image"),
        VIDEO("video");

        public final String path;
        Directory(String dir){
            path = dir;
        }
    }
}
