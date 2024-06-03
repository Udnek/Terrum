package me.jupiter;

import me.udnek.app.Settings;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(1000,
                1,
                10,
                0.01,
                0.5,
                "medium_frame.png");
        scene.setInitialDeviation(5, 5, 5);
        //me.udnek.Main.runWithScene(scene, Settings.noRecording(2, 12, Settings.PolygonHolderType.DEFAULT));
        //me.udnek.Main.runWithScene(scene, Settings.withRecording(1920, 1080, "dimas", 12, Settings.PolygonHolderType.DEFAULT));
    }
}
