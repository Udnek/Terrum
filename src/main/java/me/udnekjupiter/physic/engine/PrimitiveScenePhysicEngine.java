package me.udnekjupiter.physic.engine;

import me.udnekjupiter.physic.scene.PhysicScene;

public class PrimitiveScenePhysicEngine implements PhysicEngine {
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
    }
}
