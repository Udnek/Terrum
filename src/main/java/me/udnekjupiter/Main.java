package me.udnekjupiter;


import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PhysicEngine;

public abstract class Main{

    public static final Main.Type MAIN_TO_RUN = Type.UDNEK;
    private static Main main;
    public static void main(String ...args){
        if (MAIN_TO_RUN == Main.Type.UDNEK)
            main = new MainUdnek();
        else
            main = new MainJupiter();

        main.run();
    }

    public static Main getMain() {
        return main;
    }

    public abstract ApplicationSettings initializeGraphicsSettings();
    public abstract EnvironmentSettings initializePhysicsSettings();

    public abstract void run();

    public static void runApplication(GraphicEngine graphicEngine, PhysicEngine physicEngine){
        Application application = Application.getInstance();
        application.initialize(graphicEngine, physicEngine);
        application.start();
    }

    public enum Type {
        UDNEK,
        JUPITER
    }
}