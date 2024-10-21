package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.object.PhysicObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class PhysicScene3d implements PhysicScene {
    protected List<PhysicObject> allObjects = new ArrayList<>();
    protected List<PhysicObject> collisionInitiators = new ArrayList<>();

    @Override
    public void addObject(@NotNull PhysicObject object){
        allObjects.add(object);}
    @Override
    public @NotNull List<PhysicObject> getAllObjects(){
        return allObjects;
    }
    public void addCollisionInitiator(PhysicObject object){
        collisionInitiators.add(object);
    }
    public List<PhysicObject> getCollisionInitiators(){
        return collisionInitiators;
    }
}
