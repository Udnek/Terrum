package me.udnekjupiter.physic;

public class EnvironmentSettings {
    //Global physics settings
    public double deltaTime;
    public int iterationsPerTick;
    public double decayCoefficient;

    //Net settings
    public double springStiffness;
    public double springRelaxedLength;
    public double vertexMass;

    public EnvironmentSettings(double springStiffness, double springRelaxedLength, double vertexMass, double deltaTime, double decayCoefficient, int iterationsPerTick) {
        this.springStiffness = springStiffness;
        this.springRelaxedLength = springRelaxedLength;
        this.vertexMass = vertexMass;
        this.deltaTime = deltaTime;
        this.decayCoefficient = decayCoefficient;
        this.iterationsPerTick = iterationsPerTick;
    }


    public static EnvironmentSettings defaultPreset(){
        return new EnvironmentSettings(5000,
                1,
                5,
                0.0001,
                5,
                100);
    }
}
