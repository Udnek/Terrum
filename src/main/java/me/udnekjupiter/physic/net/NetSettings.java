package me.udnekjupiter.physic.net;

public class NetSettings {
    public final double springStiffness;
    public final double springRelaxedLength;
    public final double vertexMass;
    public final double deltaTime;
    public final double decayCoefficient;
    public final String imageFileName;
    public int iterationsPerTick;

    public NetSettings(double springStiffness, double springRelaxedLength, double vertexMass, double deltaTime, double decayCoefficient, String imageFileName, int iterationsPerTick) {
        this.springStiffness = springStiffness;
        this.springRelaxedLength = springRelaxedLength;
        this.vertexMass = vertexMass;
        this.deltaTime = deltaTime;
        this.decayCoefficient = decayCoefficient;
        this.imageFileName = imageFileName;
        this.iterationsPerTick = iterationsPerTick;
    }

    public static NetSettings getDefault(){
        return NetSettings.defaultPreset("small_frame.png");
    }

    public static NetSettings defaultPreset(String imageFileName){
        return new NetSettings(1000, 0.8, 2,
                0.001, 10, imageFileName, 4);
    }
    public static NetSettings highStiffnessPreset(String imageFileName){
        return new NetSettings(5000, 1, 5,
                0.001, 10, imageFileName, 4);
    }
}
