package me.jupiter;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(500,
                0.5,
                5,
                0.01,
                0,
                "small_square.png");
        scene.setInitialDeviation(2, 2, 0);
        me.udnek.Main.runWithScene(scene);
    }
}
