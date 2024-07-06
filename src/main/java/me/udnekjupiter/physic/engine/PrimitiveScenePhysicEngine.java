package me.udnekjupiter.physic.engine;

import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.scene.PhysicScene;

public class PrimitiveScenePhysicEngine implements PhysicEngine, ConsoleListener {
    private final PhysicScene physicScene;

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
    }

    @Override
    public void handleCommand(Command command, Object[] args) {
        if (command != Command.SET_ITERATIONS_PER_TICK) return;
        EnvironmentSettings.ENVIRONMENT_SETTINGS.iterationsPerTick = (int) args[0];
    }
}
