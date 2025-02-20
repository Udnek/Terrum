package me.udnekjupiter.physic.engine;

import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.ControllerListener;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import org.jetbrains.annotations.NotNull;

public abstract class PhysicEngine3d implements PhysicEngine<PhysicObject3d>, ConsoleListener, ControllerListener {

    public static final double SPHERE_DRAG_COEFFICIENT = 0.47;
    public static final double FORCE_HARD_CAP = Math.pow(10, 10);
    public static final double MAX_VELOCITY = 500;
    public static final double FORCE_SOFT_CAP = 500000;
    public static final double MAX_DEPTH = 0.1;

    protected PhysicScene3d scene;
    protected EnvironmentSettings settings;
    protected int beforePauseIPT;

    @Override
    public void initialize() {
        Console.getInstance().addListener(this);
        Controller.getInstance().addListener(this);
        this.beforePauseIPT = settings.iterationsPerTick;
        scene.initialize();
    }
    @Override
    public void tick(){    }

    public void reset(){
        if (settings.iterationsPerTick == 0) {
            scene.reset();
        } else {
            pauseSwitch();
            scene.reset();
            pauseSwitch();
        }
    }

    public void pauseSwitch(){
        if (settings.iterationsPerTick == 0){
            settings.iterationsPerTick = beforePauseIPT;
        } else {
            beforePauseIPT = settings.iterationsPerTick;
            settings.iterationsPerTick = 0;
        }
    }

    @Override
    public void handleCommand(@NotNull Command command, Object[] args) {
        if (command != Command.SET_ITERATIONS_PER_TICK) return;
        settings.iterationsPerTick = (int) args[0];
    }

    @Override
    public void keyEvent(@NotNull InputKey inputKey, boolean pressed) {
        if (!pressed) return;

        if (inputKey == InputKey.PAUSE) {
            pauseSwitch();
        } else if (inputKey == InputKey.RESET) {
            reset();
        }
    }

    @Override
    @NotNull
    public PhysicScene3d getScene() {return scene;}

    @Override
    @NotNull
    public EnvironmentSettings getSettings() {return settings;}
}
