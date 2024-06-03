package me.jupiter;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(250,
                0,
                10,
                0.01,
                0,
                "nano_frame.png");
        scene.setInitialDeviation(1, 1, 2);
        //me.udnek.Main.runWithScene(scene, Settings.noRecording(2, 16));
        //me.udnek.Main.runWithScene(scene, Settings.withRecording(1920, 1080, "dimas"));
    }
}
