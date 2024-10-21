package me.udnekjupiter.app;

import me.udnekjupiter.util.Utils;

public class ApplicationData {

    public long applicationStartTime = System.nanoTime();

    ///////////////////////////////////////////////////////////////////////////
    // FRAMES
    ///////////////////////////////////////////////////////////////////////////
    public int framesAmount = 0;
    public double fpsSum = 0;
    public long frameRenderTime;
    public long frameStartTime;

    public double averageFpsForLastTimes;
    public double timeSinceAverageFpsUpdate = 0;
    public double fpsSumLastTime = 0;
    public double framesSinceAverageFpsUpdate = 0;

    ///////////////////////////////////////////////////////////////////////////
    // TICKS
    ///////////////////////////////////////////////////////////////////////////

    public int physicTicks = 0;
    public long physicStart = 0;
    public long physicFinish = 0;
    public long estimatedNextTickTime = System.nanoTime();
    public long physicTickTime;
    public ApplicationData(){}

    public void frameStarted(){
        frameStartTime = System.nanoTime();
    }
    public void framePerformed(){
        frameRenderTime = System.nanoTime() - frameStartTime;
        double currentFps =  1 / ((double) frameRenderTime / Utils.NANOS_IN_SECOND);

        timeSinceAverageFpsUpdate += frameRenderTime;
        fpsSumLastTime += currentFps;
        framesSinceAverageFpsUpdate++;

        if (timeSinceAverageFpsUpdate >= 0.5 * Utils.NANOS_IN_SECOND){
            averageFpsForLastTimes = fpsSumLastTime/framesSinceAverageFpsUpdate;
            timeSinceAverageFpsUpdate = 0;
            fpsSumLastTime = 0;
            framesSinceAverageFpsUpdate = 0;
        }

        fpsSum += currentFps;
        framesAmount++;

        frameStartTime = System.nanoTime();
    }

    public void physicTickStarted(){
        physicTicks++;
        physicStart = System.nanoTime();
        estimatedNextTickTime += ((long) Math.pow(10, 9))/ StandartApplication.PHYSIC_TICKS_PER_SECOND;
        //estimatedNextTickTime = physicStart + ((long) Math.pow(10, 9))/Application.PHYSIC_TICKS_PER_SECOND;
    }
    public void physicTickPerformed(){
        physicFinish = System.nanoTime();
        physicTickTime = physicFinish - physicStart;
    }
}
