package me.jupiter;

import me.udnek.app.AppSettings;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(1000,
                0.8,
                10,
                0.001,
                5,
                "small_frame.png");
        scene.setInitialDeviation(2, 2, 2, 3, 2);
        me.udnek.Main.runApplication(scene, AppSettings.noRecording(2, 12, AppSettings.PolygonHolderType.SMART));
        //me.udnek.Main.runWithScene(scene, Settings.withRecording(512, 512, "dimas", 12, Settings.PolygonHolderType.DEFAULT));

    }
}
