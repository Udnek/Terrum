package me.jupiter.object;

public class FallingObject {
    public final int weight; //kilograms
    private float posY;
    private float velocityY;

    FallingObject()
    {
        this.weight = 1;
    }
    FallingObject(int weight)
    {
        this.weight = weight;
    }

    public float getPosY() {return posY;}
    public void setPosY(float posY) {this.posY = posY;}

    public float getVelocityY() {return velocityY;}
    public void setVelocityY(float velocityY) {this.velocityY = velocityY;}
}
