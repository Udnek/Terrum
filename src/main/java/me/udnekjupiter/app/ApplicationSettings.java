package me.udnekjupiter.app;

import me.udnekjupiter.Main;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;

public class ApplicationSettings {

    public final boolean recordVideo;
    public final String videoName;
    public final int videoWidth;
    public final int videoHeight;
    public int pixelScaling;
    public int cores;
    public boolean doLight;
    public boolean debugColorizePlanes;
    public final PolygonHolder.Type polygonHolderType;

    public static final ApplicationSettings GLOBAL = Main.getMain().initSettings();

    private ApplicationSettings(boolean recordVideo, int videoWidth, int videoHeight, String videoName, int pixelScaling, int cores, PolygonHolder.Type holderType, boolean doLight, boolean debugColorizePlanes){
        this.recordVideo = recordVideo;
        this.videoName = videoName;
        this.videoHeight = videoHeight;
        this.videoWidth = videoWidth;
        this.pixelScaling = pixelScaling;
        this.cores = cores;
        this.polygonHolderType = holderType;
        this.doLight = doLight;
        this.debugColorizePlanes = debugColorizePlanes;
    }

    public static ApplicationSettings noRecording(int pixelScaling, int cores, PolygonHolder.Type holderType, boolean doLight, boolean debugColorizePlanes){
        return new ApplicationSettings(false, 0, 0, "", pixelScaling, cores, holderType, doLight, debugColorizePlanes);
    }
    public static ApplicationSettings withRecording(int videoWidth, int videoHeight, String videoName, int cores, PolygonHolder.Type holderType, boolean doLight, boolean debugColorizePlanes){
        return new ApplicationSettings(true, videoWidth, videoHeight, videoName, 1, cores, holderType, doLight, debugColorizePlanes);
    }

    public static ApplicationSettings defaultNoRecording(int pixelScaling, int cores, PolygonHolder.Type holderType){
        return noRecording(pixelScaling, cores, holderType, false, false);
    }

    public static ApplicationSettings defaultWithRecording(int videoWidth, int videoHeight, String videoName, int cores, PolygonHolder.Type holderType){
        return withRecording(videoWidth, videoHeight, videoName, cores, holderType, false, false);
    }

}
