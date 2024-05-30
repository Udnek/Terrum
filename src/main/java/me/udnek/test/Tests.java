package me.udnek.test;

import java.util.Arrays;

public class Tests {
    public static void run() {

        int size = 512;
        int[] array = new int[size];

        System.out.println(Arrays.toString(array));

        TestRunnable testRunnable0 = new TestRunnable(array, 0);
        TestRunnable testRunnable1 = new TestRunnable(array, 1);
        TestRunnable testRunnable2 = new TestRunnable(array, 2);
        TestRunnable testRunnable3 = new TestRunnable(array, 3);


        testRunnable0.run();
        testRunnable1.run();
        testRunnable2.run();
        testRunnable3.run();

        System.out.println(Arrays.toString(array));




    }

}
