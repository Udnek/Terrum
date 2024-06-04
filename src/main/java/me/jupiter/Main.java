package me.jupiter;

import me.udnek.app.Settings;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(500,
                1,
                10,
                0.01,
                1,
                "small_frame.png");
        scene.setInitialDeviation(2, 2, 5);
        //me.udnek.Main.runWithScene(scene, Settings.noRecording(2, 16, Settings.PolygonHolderType.DEFAULT));
        me.udnek.Main.runWithScene(scene, Settings.withRecording(512, 512, "dimas", 16, Settings.PolygonHolderType.DEFAULT));
    }
}
