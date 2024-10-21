package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.object.PhysicObject3d;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class PhysicScene3d implements PhysicScene<PhysicObject3d> {
    protected List<PhysicObject3d> objects = new ArrayList<>();

    @Override
    public void addObject(@NotNull PhysicObject3d object) {
        objects.add(object);
    }

    @Override
    public void removeObject(@NotNull PhysicObject3d object) {
        objects.remove(object);
    }

    @Override
    public @NotNull List<? extends PhysicObject3d> getAllObjects() {
        return objects;
    }

}
