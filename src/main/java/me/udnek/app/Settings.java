package me.udnek.app;

public class Settings {

    public final boolean recordVideo;
    public final String videoName;
    public final int videoWidth;
    public final int videoHeight;
    public final int pixelScaling;
    public final int cores;

    public static Settings DEFAULT_12_CORES = noRecording(2, 12);
    public static Settings DEFAULT_16_CORES = noRecording(2, 16);

    private Settings(boolean recordVideo, int videoWidth, int videoHeight, String videoName, int pixelScaling, int cores){
        this.recordVideo = recordVideo;
        this.videoName = videoName;
        this.videoHeight = videoHeight;
        this.videoWidth = videoWidth;
        this.pixelScaling = pixelScaling;
        this.cores = cores;
    }

    public static Settings noRecording(int pixelScaling, int cores){
        return new Settings(false, 0, 0, "", pixelScaling, cores);
    }
    public static Settings withRecording(int videoWidth, int videoHeight, String videoName, int cores){
        return new Settings(true, videoWidth, videoHeight, videoName, 1, cores);
    }

}
