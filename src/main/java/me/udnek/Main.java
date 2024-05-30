package me.udnek;


import me.udnek.scene.Scene;
import me.udnek.scene.example.DynamicScene;
import me.udnek.tests.Tests;

public class Main{
    public static void main(String[] args) {
        Scene scene = new DynamicScene();
        scene.init();
        runWithScene(scene);
        //test();
    }


    public static void runWithScene(Scene scene){
        new Frame(scene);
    }

    public static void test(){
        Tests.run();
    }
}