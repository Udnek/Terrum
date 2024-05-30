package me.jupiter;

public class Main {
    public static void main(String[] args) {
        NetScene scene = new NetScene();
        scene.setup(500,
                1,
                5,
                0.01,
                1,
                "small_frame.png");
        scene.setInitialDeviation(2, 2, 1);
        me.udnek.Main.runWithScene(scene);
    }
}
