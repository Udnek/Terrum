package me.udnek.app;

public class Settings {

    public final boolean recordVideo;
    public final String videoName;
    public final int videoWidth;
    public final int videoHeight;
    public final int pixelScaling;
    public final int cores;
    public final PolygonHolderType polygonHolderType;

    public static Settings DEFAULT_12_CORES = noRecording(2, 12, PolygonHolderType.SMART);
    public static Settings DEFAULT_16_CORES = noRecording(2, 16, PolygonHolderType.SMART);
    public static Settings THE_SLOWEST = noRecording(1, 1, PolygonHolderType.DEFAULT);

    private Settings(boolean recordVideo, int videoWidth, int videoHeight, String videoName, int pixelScaling, int cores, PolygonHolderType holderType){
        this.recordVideo = recordVideo;
        this.videoName = videoName;
        this.videoHeight = videoHeight;
        this.videoWidth = videoWidth;
        this.pixelScaling = pixelScaling;
        this.cores = cores;
        this.polygonHolderType = holderType;
    }

    public static Settings noRecording(int pixelScaling, int cores, PolygonHolderType holderType){
        return new Settings(false, 0, 0, "", pixelScaling, cores, holderType);
    }
    public static Settings withRecording(int videoWidth, int videoHeight, String videoName, int cores, PolygonHolderType holderType){
        return new Settings(true, videoWidth, videoHeight, videoName, 1, cores, holderType);
    }

    public enum PolygonHolderType{
        DEFAULT,
        SMART
    }

}
