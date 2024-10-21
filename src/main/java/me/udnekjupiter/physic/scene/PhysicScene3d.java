package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.util.Resettable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PhysicScene3d implements PhysicScene<PhysicObject3d> {
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
    public @NotNull List<? extends PhysicObject3d> getAllObjects() {return objects;}
    @Override
    public void initialize() {}
    @Override
    public void reset(){objects.forEach(Resettable::reset);}

}
