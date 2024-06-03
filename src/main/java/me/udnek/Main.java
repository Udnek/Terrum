package me.udnek;


import me.udnek.app.Frame;
import me.udnek.app.Settings;
import me.udnek.scene.Scene;
import me.udnek.scene.instances.LightTestScene;
import me.udnek.test.Tests;

public class Main{
    public static void main(String[] args) {
        Scene scene = new LightTestScene();
        scene.init();
        runWithScene(scene, Settings.noRecording(1));
        //test();
    }


    public static void runWithScene(Scene scene, Settings settings){
        new Frame(scene, settings);
    }

    public static void test(){
        Tests.run();
    }
}