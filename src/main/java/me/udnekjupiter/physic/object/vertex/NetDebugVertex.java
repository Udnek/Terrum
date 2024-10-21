package me.udnekjupiter.physic.object.vertex;

public class NetDebugVertex extends NetDynamicVertex {
    private int internalCounter;

    private boolean isCurrentIterationObserved() {
        if (internalCounter < 0) return true;
        if (internalCounter > 2) return true;
        return false;
    }
}



