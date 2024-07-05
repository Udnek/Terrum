package me.udnekjupiter.physic.collider;

import me.udnekjupiter.physic.object.RKMObject;

import java.util.List;

public abstract class Collider {
    public abstract List<RKMObject> findIntersections(List<RKMObject> objects);
}