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
        scene.setup(NetSettings.highStiffnessPreset("small_frame.png"));
        //scene.setInitialDeviation(1,1, 5, 2, 5);
        me.udnek.Main.runApplication(scene);
    }

    public static AppSettings initSettings(){
        if (thisMainWasRun){
            return AppSettings.noRecording(2, 12, PolygonHolder.Type.DEFAULT, false, false);
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