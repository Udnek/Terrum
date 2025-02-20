package me.udnekjupiter;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.StandartApplication;
import me.udnekjupiter.app.util.ApplicationSettings;
import me.udnekjupiter.app.window.Window;
import me.udnekjupiter.app.window.jwt.JwtWindow;
import me.udnekjupiter.graphic.engine.GraphicEngine;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PhysicEngine;
import org.jetbrains.annotations.NotNull;

public abstract class Main{

    private Application application;

    public static final Main.Type MAIN_TO_RUN = Type.JUPITER;
    private static Main main;
    public static void main(String ...args){
        main = switch (MAIN_TO_RUN) {
            case UDNEK -> new MainUdnek();
            case JUPITER -> new MainJupiter();
            case BASKETBALL -> new MainBasketball();
            case BALL -> new BallMain();
            case COLORED_BALLS -> new ColoredBallsMain();
        };

        main.run();
    }

    public static @NotNull Main getMain() {
        return main;
    }

    public abstract @NotNull ApplicationSettings initializeGraphicsSettings();
    public abstract @NotNull EnvironmentSettings initializePhysicsSettings();

    public abstract void run();
    public @NotNull Application getApplication(){
        return application;
    }

    public void initializeApplication(@NotNull GraphicEngine graphicEngine, @NotNull PhysicEngine<?> physicEngine) {
        initializeApplication(graphicEngine, physicEngine, new JwtWindow());
    }

    public void initializeApplication(
            @NotNull GraphicEngine graphicEngine, @NotNull PhysicEngine<?> physicEngine, @NotNull Window window
    ) {
        application = new StandartApplication(initializeGraphicsSettings(), graphicEngine, physicEngine, window);
        application.initialize();
    }

    public void startApplication(){
        getApplication().start();
    }

    public enum Type {
        UDNEK,
        JUPITER,
        BASKETBALL,
        BALL,
        COLORED_BALLS
    }
}