package me.jupiter;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(250,
                0.5,
                5,
                0.01,
                0.5,
                "small_frame.png");
        scene.setInitialDeviation(2, 2, 5);
        me.udnek.Main.runWithScene(scene);
    }
}
