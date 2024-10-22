package me.udnekjupiter.physic.engine;

import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.physic.scene.PhysicScene;
import me.udnekjupiter.util.Initializable;
import me.udnekjupiter.util.Resettable;
import me.udnekjupiter.util.Tickable;
import org.jetbrains.annotations.NotNull;

public interface PhysicEngine<PObject extends PhysicObject<?>> extends Initializable, Tickable, Resettable {
    @NotNull PhysicScene<PObject> getScene();
    @NotNull EnvironmentSettings getSettings();
    void addObject(@NotNull PObject object);
    default void addObjects(@NotNull Iterable<@NotNull PObject> objects){
        for (PObject object : objects) {addObject(object);}
    }
}
