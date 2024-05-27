package me.jupiter.object;

public class NetSpring {
    public final float stiffness;
    public final float relaxedLength;
    private float currentLength;

    NetSpring()
    {
        this.stiffness = 1;
        this.relaxedLength = 1;
        this.currentLength = 1;
    }
    NetSpring(float stiffness)
    {
        this.stiffness = stiffness;
        this.relaxedLength = 1;
        this.currentLength = 1;
    }

    public float getCurrentLength() {return currentLength;}
    public void setCurrentLength(float currentLength) {this.currentLength = currentLength;}
}
