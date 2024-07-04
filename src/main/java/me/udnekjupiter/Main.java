package me.udnekjupiter;


import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.physic.engine.PhysicEngine;

public abstract class Main{

    public static final MainType MAIN_TO_RUN = MainType.UDNEK;
    private static Main main;
    public static void main(String ...args){
        if (MAIN_TO_RUN == MainType.UDNEK){
            MainUdnek.main(args);
            main = new MainUdnek();
        }
        else {
            MainJupiter.main(args);
            main = new MainJupiter();
        }
    }

    public static Main getMain() {
        return main;
    }

    public abstract ApplicationSettings initSettings();

    public static void runApplication(GraphicEngine graphicEngine, PhysicEngine physicEngine){
        Application application = Application.getInstance();
        application.initialize(graphicEngine, physicEngine);
        application.start();
    }
}