package me.jupiter;

public class Main {
    public static void main(String[] args) {
        NetScene scene = new NetScene();
        scene.setup(250, 0.5, 5, 0.01, "banana.png");
        scene.setInitialDeviation(4, 4, -5);
        me.udnek.Main.runWithScene(scene);
    }
}
