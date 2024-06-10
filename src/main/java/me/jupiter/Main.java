package me.jupiter;

import me.jupiter.net.NetSettings;
import me.udnek.app.AppSettings;
import me.udnek.scene.polygonholder.PolygonHolder;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
            scene.setup(NetSettings.highStiffnessPreset("small_frame.png"));
        scene.setInitialDeviation(2,2, 2, 2, 2);
        me.udnek.Main.runApplication(scene);
    }

    public static AppSettings initSettings(){
        return AppSettings.noRecording(2, 12, PolygonHolder.Type.SMART, false, false);
//        return AppSettings.withRecording(500, 500, "default", 12, PolygonHolder.Type.SMART, false, false);
    }
}
