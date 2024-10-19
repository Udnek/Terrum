package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.util.Initializable;
import me.udnekjupiter.util.Resettable;
import me.udnekjupiter.util.Tickable;

import java.util.ArrayList;
import java.util.List;

public abstract class PhysicScene implements Initializable, Tickable, Resettable {
    protected List<PhysicObject> allObjects = new ArrayList<>();
    protected List<PhysicObject> collisionInitiators = new ArrayList<>();

    public void addObject(PhysicObject object){
        allObjects.add(object);}
    public List<PhysicObject> getAllObjects(){
        return allObjects;
    }
    public void addCollisionInitiator(PhysicObject object){
        collisionInitiators.add(object);
    }
    public List<PhysicObject> getCollisionInitiators(){
        return collisionInitiators;
    }
}
