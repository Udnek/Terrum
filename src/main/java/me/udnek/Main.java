package me.udnek;


import me.udnek.tests.Tests;

public class Main{
    public static void main(String[] args) {
        ScreenAdapter screenAdapter = new ScreenAdapter();
        screenAdapter.loop();
        //test();
    }




    public static void test(){
        Tests.run();
    }
}