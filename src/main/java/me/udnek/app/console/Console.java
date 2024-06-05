package me.udnek.app.console;

import java.util.Scanner;

public class Console implements Runnable{
    private boolean shouldRun = false;
    private ConsoleHandler consoleHandler;

    public Console(ConsoleHandler consoleHandler){
        this.consoleHandler = consoleHandler;
    }
    public void start(){
        shouldRun = true;
        new Thread(this).start();
    }
    @Override
    public void run() {
        assert shouldRun;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input command (type help for help):");

        while (true){
            String string = scanner.nextLine();
            Command.execute(consoleHandler, string);
        }

    }
}
