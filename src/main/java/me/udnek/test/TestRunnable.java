package me.udnek.test;

public class TestRunnable implements Runnable{
    private int[] array;
    private int n;
    public TestRunnable(int[] array, int n){
        this.array =  array;
        this.n = n;
    }
    @Override
    public void run() {
        int from = (array.length/4)*n;
        int to = (array.length/4)*n + (array.length/4);
        for (int i = from; i < to; i++) {
            array[i] = n;
        }
    }
}
