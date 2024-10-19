package me.udnekjupiter.physic.core;

import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.util.Initializable;
import me.udnekjupiter.util.Resettable;
import me.udnekjupiter.util.Tickable;

public interface PhysicCore extends Initializable, Tickable, Resettable {
    void addObject(PhysicObject object);


    enum Type{
        EULER,
        RKM
    }
}
