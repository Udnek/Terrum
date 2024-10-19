package me.udnekjupiter.physic;

import me.udnekjupiter.physic.core.PhysicCore;

public class EnvironmentSettings {
    //Global physics settings
    public double deltaTime;
    public int iterationsPerTick;
    public double decayCoefficient;
    public double maxDepth;
    public PhysicCore.Type physicCoreType = PhysicCore.Type.EULER;

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
        return new EnvironmentSettings(10000,
                1,
                5,
                0.0001,
                5,
                0.1,
                200);
    }
}
