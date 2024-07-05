package me.udnekjupiter.physic;

import me.udnekjupiter.Main;

public class EnvironmentSettings {
    //Global physics settings
    public double deltaTime;
    public final String imageFileName;
    public int iterationsPerTick;
    public double decayCoefficient;

    //Net settings
    public double springStiffness;
    public double springRelaxedLength;
    public double vertexMass;

    public static final EnvironmentSettings ENVIRONMENT_SETTINGS = Main.getMain().initializePhysicsSettings();

    public EnvironmentSettings(double springStiffness, double springRelaxedLength, double vertexMass, double deltaTime, double decayCoefficient, String imageFileName, int iterationsPerTick) {
        this.springStiffness = springStiffness;
        this.springRelaxedLength = springRelaxedLength;
        this.vertexMass = vertexMass;
        this.deltaTime = deltaTime;
        this.decayCoefficient = decayCoefficient;
        this.imageFileName = imageFileName;
        this.iterationsPerTick = iterationsPerTick;
    }

    public static EnvironmentSettings defaultPreset(){
        return new EnvironmentSettings(5000,
                0.5,
                5,
                0.001,
                5,
                "small_frame.png",
                4);
    }
}
