package me.udnekjupiter.physic.engine;

import me.udnekjupiter.app.StandartApplication;
import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.ControllerListener;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import org.jetbrains.annotations.NotNull;

public abstract class PhysicEngine3d<Scene extends PhysicScene3d> implements PhysicEngine<Scene>, ConsoleListener, ControllerListener {

    public static final double GRAVITATIONAL_ACCELERATION = -9.80665;
    public static final double MAX_VELOCITY = 500;
    public static final double MAX_FORCE = 50000;
    public static final double MAX_DEPTH = 0.1;     //Жесткое ограничение, отражающее максимальную глубину на которую один хитбокс может погрузиться в другой


    protected Scene scene;
    protected int beforePauseIPT = StandartApplication.ENVIRONMENT_SETTINGS.iterationsPerTick;

    @Override
    public void initialize() {
        Console.getInstance().addListener(this);
        Controller.getInstance().addListener(this);
    }

    public void reset(){scene.reset();}

    @Override
    public void handleCommand(@NotNull Command command, Object[] args) {
        if (command != Command.SET_ITERATIONS_PER_TICK) return;
        StandartApplication.ENVIRONMENT_SETTINGS.iterationsPerTick = (int) args[0];
    }

    @Override
    public void keyEvent(InputKey inputKey, boolean pressed) {
        if (!pressed) return;

        EnvironmentSettings settings = StandartApplication.ENVIRONMENT_SETTINGS;

        if (inputKey == InputKey.PAUSE) {
            if (settings.iterationsPerTick == 0){
                settings.iterationsPerTick = beforePauseIPT;
            } else {
                beforePauseIPT = settings.iterationsPerTick;
                settings.iterationsPerTick = 0;
            }
        } else if (inputKey == InputKey.RESET) reset();
    }

    @Override
    @NotNull
    public Scene getScene() {return scene;}
}
