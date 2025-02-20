package me.udnekjupiter.app.console;

import me.udnekjupiter.Main;
import me.udnekjupiter.util.utilityinterface.Listenable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console implements Runnable, Listenable<ConsoleListener> {
    private boolean canRun = false;
    private List<ConsoleListener> listeners = new ArrayList<>();
    Thread thread;

    private static Console instance;
    private Console(){}
    public static Console getInstance() {
        if (instance == null) instance = new Console();
        return instance;
    }

    @Override
    public void addListener(@NotNull ConsoleListener listener) {
        listeners.add(listener);
    }

    public void start(){
        thread = new Thread(this);
        thread.setName("Console");
        thread.start();
    }
    @Override
    public void run() {
        assert canRun;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input command (type help for help):");

        while (Main.getMain().getApplication().isRunning()){
            String string = scanner.nextLine();
            Command.execute(listeners, string);
        }

    }
}
