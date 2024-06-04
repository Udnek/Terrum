package me.udnek;


import me.udnek.app.Application;
import me.udnek.app.Settings;
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
        Settings settings;
        settings = Settings.noRecording(2, 6, Settings.PolygonHolderType.SMART);
        //settings = Settings.withRecording(512, 512, "test2", 12, Settings.PolygonHolderType.SMART);
        Scene scene = new LightTestScene();
        runApplication(scene, settings);
        //test();
    }

    public static void runApplication(Scene scene, Settings settings){
        new Application(scene, settings);
    }
    public static void test(){
        Tests.run();
    }
}