package me.udnek;


import me.udnek.app.Frame;
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
                "big_frame.png");
        scene.setInitialDeviation(2, 2, 2, 2, 2);*/
        Settings settings;
        settings = Settings.noRecording(2, 12, Settings.PolygonHolderType.DEFAULT);
        //settings = Settings.withRecording(512, 512, "test2", 12, Settings.PolygonHolderType.SMART);
        Scene scene = new LightTestScene();
        runWithScene(scene, settings);
        //test();
    }

    public static void runWithScene(Scene scene, Settings settings){ new Frame(scene, settings);}
    public static void test(){
        Tests.run();
    }
}