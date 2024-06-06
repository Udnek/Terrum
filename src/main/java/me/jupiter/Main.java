package me.jupiter;

import me.jupiter.net.NetSettings;
import me.udnek.app.AppSettings;
import me.udnek.scene.polygonholder.PolygonHolder;

public class Main {
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(NetSettings.from("small_frame.png"));
        scene.setInitialDeviation(1,1, 1, 0, 1);
        me.udnek.Main.runApplication(scene);
    }

    public static AppSettings initSettings(){
//        return AppSettings.noRecording(2, 2, PolygonHolder.Type.DEFAULT, false);
        return AppSettings.withRecording(500, 500, "default", 12, PolygonHolder.Type.DEFAULT, false);
    }
}
