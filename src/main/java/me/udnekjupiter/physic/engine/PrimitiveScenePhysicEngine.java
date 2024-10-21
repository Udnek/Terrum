package me.udnekjupiter.physic.engine;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.ControllerListener;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import me.udnekjupiter.util.Resettable;
import org.jetbrains.annotations.NotNull;

public class PrimitiveScenePhysicEngine implements PhysicEngine, ConsoleListener, ControllerListener, Resettable {

    double GRAVITATIONAL_ACCELERATION = -9.80665;
    double MAX_VELOCITY = 500;
    double MAX_FORCE = 50000;
    double MAX_DEPTH = 0.1;     //Жесткое ограничение, отражающее максимальную глубину на которую один хитбокс может погрузиться в другой


    private final PhysicScene3d physicScene;
    private int beforePauseIPT = Application.ENVIRONMENT_SETTINGS.iterationsPerTick;
//    private int internalCounter = 0;

    public PrimitiveScenePhysicEngine(PhysicScene3d physicScene){
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

    public void reset(){
        physicScene.reset();
    }

    @Override
    public void handleCommand(@NotNull Command command, Object[] args) {
        if (command != Command.SET_ITERATIONS_PER_TICK) return;
        Application.ENVIRONMENT_SETTINGS.iterationsPerTick = (int) args[0];
    }

    @Override
    public void keyEvent(InputKey inputKey, boolean pressed) {
        if (!pressed) return;

        EnvironmentSettings settings = Application.ENVIRONMENT_SETTINGS;

        if (inputKey == InputKey.PAUSE) {
            if (settings.iterationsPerTick == 0){
                settings.iterationsPerTick = beforePauseIPT;
            } else {
                beforePauseIPT = settings.iterationsPerTick;
                settings.iterationsPerTick = 0;
            }
        } else if (inputKey == InputKey.RESET){
            reset();
        } else {
            return;
        }
    }
}
