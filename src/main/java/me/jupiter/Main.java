package me.jupiter;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(250,
                0,
                10,
                0.01,
                0.5,
                "small_frame.png");
        scene.setInitialDeviation(2, 2, 4);
        //me.udnek.Main.runWithScene(scene, Settings.noRecording(2));
        //me.udnek.Main.runWithScene(scene, Settings.withRecording(1920, 1080, "dimas"));
    }
}
