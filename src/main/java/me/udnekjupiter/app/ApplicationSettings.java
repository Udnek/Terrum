package me.udnekjupiter.app;

import me.udnekjupiter.graphic.polygonholder.PolygonHolder;

public class ApplicationSettings {

    // COMMON
    public int pixelScaling = 1;
    public int startWindowWidth = 700;
    public int startWindowHeight = 700;
    // VIDEO
    public final boolean recordVideo;
    public final String videoName;
    public final int videoWidth;
    public final int videoHeight;
    // RAYTRACING
    public boolean doLight = false;
    public boolean debugColorizePlanes = false;
    public int cores = 1;
    public PolygonHolder.Type polygonHolderType = PolygonHolder.Type.SMART;
    // RASTERIZATION
    public boolean drawWireframe = false;
    public boolean drawPlanes = true;

    private ApplicationSettings(boolean recordVideo, int videoWidth, int videoHeight, String videoName){
        this.recordVideo = recordVideo;
        this.videoName = videoName;
        this.videoHeight = videoHeight;
        this.videoWidth = videoWidth;
    }

    public static ApplicationSettings noRecording(){
        return new ApplicationSettings(false, 0, 0, "");
    }
    public static ApplicationSettings withRecording(int videoWidth, int videoHeight, String videoName){
        return new ApplicationSettings(true, videoWidth, videoHeight, videoName);
    }
}
