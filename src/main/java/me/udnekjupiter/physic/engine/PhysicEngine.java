package me.udnekjupiter.physic.engine;

import me.udnekjupiter.physic.scene.PhysicScene;
import me.udnekjupiter.util.Initializable;
import me.udnekjupiter.util.Resettable;
import me.udnekjupiter.util.Tickable;
import org.jetbrains.annotations.NotNull;

public interface PhysicEngine<PScene extends PhysicScene<?>> extends Initializable, Tickable, Resettable {
    @NotNull PScene getScene();
}
