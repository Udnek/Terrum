package me.jupiter.net;

public class NetSettings {
    public final double springStiffness;
    public final double springRelaxedLength;
    public final double vertexMass;
    public final double deltaTime;
    public final double decayCoefficient;
    public final String imageFileName;
    public final int iterationsPerTick;


    private NetSettings(double springStiffness, double springRelaxedLength, double vertexMass, double deltaTime, double decayCoefficient, String imageFileName, int iterationsPerTick) {
        this.springStiffness = springStiffness;
        this.springRelaxedLength = springRelaxedLength;
        this.vertexMass = vertexMass;
        this.deltaTime = deltaTime;
        this.decayCoefficient = decayCoefficient;
        this.imageFileName = imageFileName;
        this.iterationsPerTick = iterationsPerTick;
    }

    public static NetSettings defaultSettings(){
        return new NetSettings(1000, 0.8, 10,
                0.001, 0, "brick.png", 4);
    }
}
