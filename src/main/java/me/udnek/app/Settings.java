package me.udnek.app;

public class Settings {

    public final boolean recordVideo;
    public final String videoName;
    public final int videoWidth;
    public final int videoHeight;
    public final int pixelScaling;

    public static Settings DEFAULT = noRecording(2);

    private Settings(boolean recordVideo, int videoWidth, int videoHeight, String videoName, int pixelScaling){
        this.recordVideo = recordVideo;
        this.videoName = videoName;
        this.videoHeight = videoHeight;
        this.videoWidth = videoWidth;
        this.pixelScaling = pixelScaling;
    }

    public static Settings noRecording(int pixelScaling){
        return new Settings(false, 0, 0, "", pixelScaling);
    }
    public static Settings withRecording(int videoWidth, int videoHeight, String videoName){
        return new Settings(true, videoWidth, videoHeight, videoName, 1);
    }

}
