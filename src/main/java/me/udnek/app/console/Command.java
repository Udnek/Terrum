package me.udnek.app.console;

import java.util.Arrays;

public enum Command{
    HELP("help", "outputs all commands"),
    SET_DO_LIGHT("doLight", "sets doLight", "", ArgumentType.BOOLEAN),
    SET_CORES("cores", "sets render threads", "", ArgumentType.INTEGER),
    SET_ITERATIONS_PER_TICK("ipt", "sets iterations per tick in physical simulation", "", ArgumentType.INTEGER);

    public final String name;
    public final String description;
    private final String[] args;
    private final ArgumentType<?>[] argTypes;

    Command(String name, String description){
        this.name = name;
        this.description = description;
        this.args = new String[]{};
        this.argTypes = new ArgumentType<?>[]{};
    }
    Command(String name, String description, String[] args, ArgumentType<?>[] types){
        this.name = name;
        this.description = description;
        this.args = args;
        this.argTypes = types;
    }
    Command(String name, String description, String arg, ArgumentType<?> type){
        this.name = name;
        this.description = description;
        this.args = new String[]{arg};
        this.argTypes = new ArgumentType<?>[]{type};
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
        ArgumentType<?>[] argTypes = command.argTypes;
        if (args.length != argTypes.length){
            System.out.println("Incorrect amount of argument");
            return;
        }
        Object[] actualArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            ArgumentType<?> argumentType = argTypes[i];
            Object value = argumentType.convert(arg);
            if (value == null){
                System.out.println("Incorrect argument: " + arg);
                return;
            }
            actualArgs[i] = value;
        }
        consoleHandler.handleCommand(command, actualArgs);
    }

    private static void showHelp(){
        System.out.println("All commands:");
        for (Command command : Command.values()) {
            System.out.print(command.name);
            String[] strings = command.args;
            for (int i = 0; i < strings.length; i++) {
                String arg = strings[i];
                ArgumentType<?> type = command.argTypes[i];
                if (arg.isEmpty()){
                    System.out.print(" ["+ type.getName() + "]");
                }else {
                    System.out.print(" [" + arg + " (" + type.getName() + ")]");
                }

            }
            System.out.print(" ("+command.description+")");
            System.out.println();
        }
    }
}
