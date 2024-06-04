package me.jupiter;

import me.udnek.app.Settings;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(500,
                1,
                10,
                0.01,
                5,
                "singular_vertex.png");
        scene.setInitialDeviation(1, 0, 2, 0, 0);
        me.udnek.Main.runWithScene(scene, Settings.noRecording(2, 12, Settings.PolygonHolderType.DEFAULT));
        //me.udnek.Main.runWithScene(scene, Settings.withRecording(512, 512, "dimas", 12, Settings.PolygonHolderType.DEFAULT));
    }
}
