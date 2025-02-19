package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.object.CollidablePhysicObject3d;
import me.udnekjupiter.physic.object.CollisionInitiator;
import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.object.PlaneObject;
import me.udnekjupiter.util.Resettable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PhysicScene3d implements PhysicScene<PhysicObject3d> {
    protected PhysicSceneObjects objects = new PhysicSceneObjects();

    @Override
    public void addObject(@NotNull PhysicObject3d object) {
        objects.add(object);
    }
    @Override
    public void removeObject(@NotNull PhysicObject3d object) {
        objects.remove(object);
    }

    @Override
    public @NotNull List<? extends PhysicObject3d> getAllObjects() {return objects.getList();}

    public @NotNull List<? extends CollidablePhysicObject3d> getAllCollidableObjects() {
        List<CollidablePhysicObject3d> collidableObjects = new ArrayList<>();
        for (PhysicObject3d object : objects.getList()) {
            if (!(object instanceof CollidablePhysicObject3d)) continue;
            collidableObjects.add((CollidablePhysicObject3d) object);
        }
        return collidableObjects;
    }

    public @NotNull List<? extends CollidablePhysicObject3d> getAllCollisionInitiators(){
        List<CollidablePhysicObject3d> collisionInitiators = new ArrayList<>();
        for (PhysicObject3d object : objects.getList()) {
            if (!(object instanceof CollidablePhysicObject3d)) continue;
            if (!(object instanceof CollisionInitiator)) continue;
            collisionInitiators.add((CollidablePhysicObject3d) object);
        }
        return collisionInitiators;
    }
    @Override
    public void initialize() {}
    @Override
    public void reset(){objects.getList().forEach(Resettable::reset);}

}
