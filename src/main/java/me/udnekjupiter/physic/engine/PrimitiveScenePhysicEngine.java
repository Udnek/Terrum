package me.udnekjupiter.physic.engine;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.ControllerListener;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.scene.PhysicScene;

public class PrimitiveScenePhysicEngine implements PhysicEngine, ConsoleListener, ControllerListener {
    private final PhysicScene physicScene;
    public static double gravitationalAcceleration = -9.80665;
    private int beforePauseIPT = Application.ENVIRONMENT_SETTINGS.iterationsPerTick;

    public PrimitiveScenePhysicEngine(PhysicScene physicScene){
        this.physicScene = physicScene;
    }
    @Override
    public void tick() {
        physicScene.tick();
    }

    @Override
    public void initialize() {
        physicScene.initialize();
        Console.getInstance().addListener(this);
        Controller.getInstance().addListener(this);
    }

    @Override
    public void handleCommand(Command command, Object[] args) {
        if (command != Command.SET_ITERATIONS_PER_TICK) return;
        Application.ENVIRONMENT_SETTINGS.iterationsPerTick = (int) args[0];
    }

    @Override
    public void keyEvent(InputKey inputKey, boolean pressed) {
        if (inputKey != InputKey.PAUSE) return;
        if (!pressed) return;

        EnvironmentSettings settings = Application.ENVIRONMENT_SETTINGS;
        if (settings.iterationsPerTick == 0){
            settings.iterationsPerTick = beforePauseIPT;
        } else {
            beforePauseIPT = settings.iterationsPerTick;
            settings.iterationsPerTick = 0;
        }
    }
}
