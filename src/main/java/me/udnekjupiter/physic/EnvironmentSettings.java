package me.udnekjupiter.physic;

import me.udnekjupiter.app.Application;

public class EnvironmentSettings {
    //Global physics settings
    public double deltaTime;
    public int iterationsPerTick;
    public double decayCoefficient;
    public double maxDepth;

    //Net settings
    public double springStiffness;
    public double springRelaxedLength;
    public double vertexMass;

    public EnvironmentSettings(double springStiffness, double springRelaxedLength, double vertexMass, double deltaTime, double decayCoefficient, double maxDepth, int iterationsPerTick) {
        this.springStiffness = springStiffness;
        this.springRelaxedLength = springRelaxedLength;
        this.vertexMass = vertexMass;
        this.deltaTime = deltaTime;
        this.decayCoefficient = decayCoefficient;
        this.maxDepth = maxDepth;
        this.iterationsPerTick = iterationsPerTick;
    }


    public static EnvironmentSettings defaultPreset(){
        double deltaTime = 0.0001;
        return new EnvironmentSettings(10000,
                1,
                5,
                deltaTime,
                0,
                0.1,
                (int) (1/deltaTime/Application.PHYSIC_TICKS_PER_SECOND)
        );
    }
}
