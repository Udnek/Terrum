package me.udnek;


import me.udnek.app.AppSettings;
import me.udnek.app.Application;
import me.udnek.scene.Scene;
import me.udnek.scene.instances.LightTestScene;
import me.udnek.test.Tests;

public class Main{
    public static void main(String[] args) {
/*        PhysicalScene scene = new PhysicalScene();
        scene.setup(250,
                0,
                10,
                0.01,
                0.5,
                "small_frame.png");
        scene.setInitialDeviation(2, 2, 2, 2, 2);*/
        AppSettings appSettings;
        //settings = Settings.noRecording(2, 6, Settings.PolygonHolderType.SMART);
        appSettings = AppSettings.withRecording(512, 512, "testDir", 6, AppSettings.PolygonHolderType.SMART);
        Scene scene = new LightTestScene();
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