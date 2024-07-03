package me.udnekjupiter.app;

import me.udnekjupiter.file.FileManager;
import org.jcodec.api.awt.AWTSequenceEncoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoRecorder {

    private AWTSequenceEncoder videoEncoder;
    private ApplicationSettings settings;

    public void start(ApplicationSettings settings){
        this.settings = settings;

        if (!settings.recordVideo) return;

        File file = FileManager.readFile(FileManager.Directory.VIDEO, settings.videoName+".mp4");
        try {
            videoEncoder = AWTSequenceEncoder.createSequenceEncoder(file, 25);
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public void addFrame(BufferedImage frame){
        if (!settings.recordVideo) return;
        try {
            videoEncoder.encodeImage(frame);
        } catch (Exception ignored) {}
    }


    public void save(){
        if (!settings.recordVideo) return;
        try {
            videoEncoder.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
