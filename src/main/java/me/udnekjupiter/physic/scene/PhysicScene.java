package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.util.utilityinterface.Initializable;
import me.udnekjupiter.util.utilityinterface.Resettable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface PhysicScene<ObjectType extends PhysicObject<?>> extends Resettable, Initializable {
    void addObject(@NotNull ObjectType object);
    void removeObject(@NotNull ObjectType object);
    @NotNull List<? extends ObjectType> getAllObjects();
    @Override
    default void reset(){getAllObjects().forEach(Resettable::reset);}
}
