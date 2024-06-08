package me.udnek;


import me.jupiter.PhysicalScene;
import me.jupiter.net.NetSettings;
import me.udnek.app.AppSettings;
import me.udnek.app.Application;
import me.udnek.scene.Scene;
import me.udnek.scene.polygonholder.PolygonHolder;
import me.udnek.test.Tests;

public class Main{

    private static boolean thisMainWasRun;
    public static void main(String[] args) {
        thisMainWasRun = true;
        PhysicalScene scene = new PhysicalScene();
        scene.setup(NetSettings.from("frame.png"));
        //scene.setInitialDeviation(2, 2, 2, 2, 2);
        //appSettings = AppSettings.withRecording(512, 512, "testDir", 6, AppSettings.PolygonHolderType.SMART);
        //Scene scene = new LightTestScene();
        runApplication(scene);
        //test();
    }

    public static AppSettings initSettings(){
        if (thisMainWasRun){
            return AppSettings.noRecording(2, 6, PolygonHolder.Type.SMART, false, true);
        }
        return me.jupiter.Main.initSettings();

    }

    public static void runApplication(Scene scene){
        new Application(scene);
    }
    public static void test(){
        Tests.run();
    }
}