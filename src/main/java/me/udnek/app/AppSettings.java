package me.udnek.app;

import me.udnek.Main;
import me.udnek.scene.polygonholder.PolygonHolder;

public class AppSettings {

    public final boolean recordVideo;
    public final String videoName;
    public final int videoWidth;
    public final int videoHeight;
    public int pixelScaling;
    public int cores;
    public boolean doLight;
    public final PolygonHolder.Type polygonHolderType;

    public static final AppSettings globalSettings = Main.initSettings();

    private AppSettings(boolean recordVideo, int videoWidth, int videoHeight, String videoName, int pixelScaling, int cores, PolygonHolder.Type holderType, boolean doLight){
        this.recordVideo = recordVideo;
        this.videoName = videoName;
        this.videoHeight = videoHeight;
        this.videoWidth = videoWidth;
        this.pixelScaling = pixelScaling;
        this.cores = cores;
        this.polygonHolderType = holderType;
        this.doLight = doLight;
    }

    public static AppSettings noRecording(int pixelScaling, int cores, PolygonHolder.Type holderType, boolean doLight){
        return new AppSettings(false, 0, 0, "", pixelScaling, cores, holderType, doLight);
    }
    public static AppSettings withRecording(int videoWidth, int videoHeight, String videoName, int cores, PolygonHolder.Type holderType, boolean doLight){
        return new AppSettings(true, videoWidth, videoHeight, videoName, 1, cores, holderType, doLight);
    }

/*    public static void setGlobalSettings(AppSettings newSettings) {
        AppSettings settings = AppSettings.globalSettings;
        settings.pixelScaling = newSettings.pixelScaling;
        settings.cores = newSettings.cores;
        settings.doLight = newSettings.doLight;
    }*/

}
