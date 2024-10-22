package me.udnekjupiter.physic.engine;

import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.physic.scene.PhysicScene;
import me.udnekjupiter.util.Initializable;
import me.udnekjupiter.util.Resettable;
import me.udnekjupiter.util.Tickable;
import org.jetbrains.annotations.NotNull;

public interface PhysicEngine<ObjectType extends PhysicObject<?>> extends Initializable, Tickable, Resettable {
    <Scene extends PhysicScene<ObjectType>> @NotNull Scene getScene();
    @NotNull EnvironmentSettings getSettings();
    void addObject(@NotNull ObjectType object);
}
