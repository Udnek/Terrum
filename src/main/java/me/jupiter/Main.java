package me.jupiter;

import me.jupiter.net.NetSettings;
import me.udnek.app.Settings;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(NetSettings.defaultSettings());
        scene.setInitialDeviation(2, 2, 2, 3, 2);
        me.udnek.Main.runApplication(scene, Settings.noRecording(2, 12, Settings.PolygonHolderType.SMART));
        //me.udnek.Main.runWithScene(scene, Settings.withRecording(512, 512, "dimas", 12, Settings.PolygonHolderType.DEFAULT));

    }
}
