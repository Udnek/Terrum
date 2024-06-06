package me.jupiter;

import me.jupiter.net.NetSettings;
import me.udnek.app.AppSettings;
import me.udnek.scene.polygonholder.PolygonHolder;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(NetSettings.from("small_frame.png"));
        scene.setInitialDeviation(2, 2, 2, 1, 2);
        me.udnek.Main.runApplication(scene);
        //me.udnek.Main.runWithScene(scene, Settings.withRecording(512, 512, "dimas", 12, Settings.PolygonHolderType.DEFAULT));

    }

    public static AppSettings initSettings(){
        return AppSettings.noRecording(4, 2, PolygonHolder.Type.SMART, false);
    }
}
