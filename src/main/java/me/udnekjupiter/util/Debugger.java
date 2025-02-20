package me.udnekjupiter.util;

import me.udnekjupiter.physic.object.ColliderAnchor;
import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.physic.object.PhysicObject3d;

import java.util.List;

public class Debugger {
    protected List<PhysicObject3d> supervisedObjects;
    private int internalCounter = 0;

    public Debugger(List<PhysicObject3d> objectsToDebug)
    {
        supervisedObjects = objectsToDebug;
    }

    public void addSupervisedObject(PhysicObject3d object){
        supervisedObjects.add(object);
    }
    public void removeSupervisedObject(PhysicObject3d object){
        supervisedObjects.remove(object);
    }

    public void tick(){
        internalCounter += 1;
        for (PhysicObject3d object : supervisedObjects) {
            if (object instanceof ColliderAnchor anchor){
                System.out.println("Anchor [" + anchor.getPosition().asString() + "] is in touch with " + anchor.getCollider().getCurrentCollisions().size() + " objects");
            }
        }
    }
}
