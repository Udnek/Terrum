package me.udnek.app.console;

import java.util.Arrays;

public enum Command{
    HELP("help", "outputs all commands"),
    TEST("test", "tests???", "1", "2", "3");

    public final String name;
    public final String description;
    private final String[] args;
    Command(String name, String description, String ...args){
        this.name = name;
        this.description = description;
        this.args = args;
    }

    private static Command getCommand(String fullCommand){
        String[] split = fullCommand.split(" ");
        if (split.length == 0) return null;
        String name = split[0];
        for (Command command : Command.values()) {
            if (command.name.equals(name)){
                return command;
            }
        }
        return null;
    }
    private static String[] getArgs(String fullCommand){
        String[] split = fullCommand.split(" ");
        if (split.length <= 1) return new String[0];
        return Arrays.copyOfRange(split, 1, split.length);
    }
    public static void execute(ConsoleHandler consoleHandler, String fullCommand){
        Command command = getCommand(fullCommand);
        if (command == null) {
            System.out.println("Unknown command (type help for help)");
            return;
        }
        if (command == HELP){
            showHelp();
        }
        String[] args = getArgs(fullCommand);
        consoleHandler.handleCommand(command, args);
    }

    private static void showHelp(){
        System.out.println("All commands:");
        for (Command command : Command.values()) {
            System.out.print(command.name);
            for (String arg : command.args) {
                System.out.print(" ["+arg+"]");
            }
            System.out.print(" ("+command.description+")");
            System.out.println();
        }
    }
}
