package me.udnekjupiter.app;

public class DebugData {

    public int framesAmount = 0;
    public double fpsSum = 0;
    public double renderTime;
    public long frameStartTime = System.nanoTime();

    public double averageFpsForLastTimes;
    public double timeSinceAverageFpsUpdate = 0;
    public double fpsSumLastTime = 0;
    public double framesSinceAverageFpsUpdate = 0;
    public DebugData(){}

    public void framePerformed(){
        renderTime = (System.nanoTime() - frameStartTime)/Math.pow(10, 9);

        timeSinceAverageFpsUpdate += renderTime;
        fpsSumLastTime += 1/renderTime;
        framesSinceAverageFpsUpdate++;

        if (timeSinceAverageFpsUpdate >= 0.5){
            averageFpsForLastTimes = fpsSumLastTime/framesSinceAverageFpsUpdate;
            timeSinceAverageFpsUpdate = 0;
            fpsSumLastTime = 0;
            framesSinceAverageFpsUpdate = 0;
        }

        fpsSum += 1/renderTime;
        framesAmount++;

        frameStartTime = System.nanoTime();
    }


}
