package me.udnek;


import me.jupiter.PhysicalScene;
import me.jupiter.net.NetSettings;
import me.udnek.app.AppSettings;
import me.udnek.app.Application;
import me.udnek.scene.Scene;
import me.udnek.test.Tests;

public class Main{
    public static void main(String[] args) {
        PhysicalScene scene = new PhysicalScene();
        scene.setup(NetSettings.from("small_frame.png"));
        scene.setInitialDeviation(2, 2, 2, 2, 2);
        AppSettings appSettings;
        appSettings = AppSettings.noRecording(2, 6, AppSettings.PolygonHolderType.SMART);
        //appSettings = AppSettings.withRecording(512, 512, "testDir", 6, AppSettings.PolygonHolderType.SMART);
        //Scene scene = new LightTestScene();
        runApplication(scene, appSettings);
        //test();
    }

    public static void runApplication(Scene scene, AppSettings appSettings){
        new Application(scene, appSettings);
    }
    public static void test(){
        Tests.run();
    }
}