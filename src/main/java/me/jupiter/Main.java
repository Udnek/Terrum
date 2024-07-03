package me.jupiter;

import me.jupiter.object.net.NetSettings;
import me.udnek.app.AppSettings;
import me.udnek.scene.polygonholder.PolygonHolder;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
            scene.setup(NetSettings.highStiffnessPreset("small_frame.png"));
        scene.setInitialDeviation(2,2, 2, 0, 2);
        me.udnek.Main.runApplication(scene);
    }

    public static AppSettings initSettings(){
        return AppSettings.noRecording(4, 12, PolygonHolder.Type.DEFAULT, false, false);
//        return AppSettings.withRecording(500, 500, "default", 12, PolygonHolder.Type.SMART, false, false);
    }
}
