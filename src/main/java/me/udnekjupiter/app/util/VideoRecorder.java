package me.udnekjupiter.app.util;

import me.udnekjupiter.app.StandartApplication;
import org.jcodec.api.awt.AWTSequenceEncoder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoRecorder {

    public static final int VIDEO_FPS = StandartApplication.PHYSIC_TICKS_PER_SECOND/2;
    private AWTSequenceEncoder videoEncoder;
    private ApplicationSettings settings;

    public void start(ApplicationSettings settings){
        this.settings = settings;

        if (!settings.recordVideo) return;


        File fullDirectory = new File(System.getProperty("user.dir") + "/out/");
        if (!fullDirectory.exists()) fullDirectory.mkdir();
        File file = new File(System.getProperty("user.dir") + "/out/video/" + settings.videoName + ".mp4");


        try {
            videoEncoder = AWTSequenceEncoder.createSequenceEncoder(file, VIDEO_FPS);
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
