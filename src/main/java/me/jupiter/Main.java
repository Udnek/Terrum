package me.jupiter;

import me.jupiter.net.NetSettings;
import me.udnek.app.AppSettings;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(NetSettings.from("brick.png"));
        scene.setInitialDeviation(1, 0, 1, 0, 0);
        me.udnek.Main.runApplication(scene, AppSettings.noRecording(2, 12, AppSettings.PolygonHolderType.DEFAULT));
        //me.udnek.Main.runWithScene(scene, Settings.withRecording(512, 512, "dimas", 12, Settings.PolygonHolderType.DEFAULT));

    }
}
