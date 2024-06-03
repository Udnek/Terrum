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
                "small_frame.png");
        scene.setInitialDeviation(2, 2, 4);
        //Settings settings = Settings.noRecording(2, 12);
        Settings settings = Settings.withRecording(512, 512, "test1", 12);
        runWithScene(scene, settings);*/
        Scene scene = new LightTestScene();
        scene.init();
        runWithScene(scene, Settings.noRecording(4, 12));
        //test();
    }


    public static void runWithScene(Scene scene, Settings settings){
        new Frame(scene, settings);
    }

    public static void test(){
        Tests.run();
    }
}