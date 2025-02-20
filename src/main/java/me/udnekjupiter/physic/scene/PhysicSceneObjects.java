package me.udnekjupiter.physic.scene;

import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.physic.object.PhysicObject3d;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PhysicSceneObjects {
    private final List<PhysicObject3d> objects;

    PhysicSceneObjects()
    {
        objects = new ArrayList<>();
    }

    public int getAmount(){
        return objects.size();
    }

    @NotNull
    public PhysicObject3d getObject(int index){
        return objects.get(index);
    }

    public void add(@NotNull PhysicObject3d object){
        objects.add(object);
    }

    public void remove(@NotNull PhysicObject3d object){
        objects.remove(object);
    }

    public List<PhysicObject3d> getList(){
        return objects;
    }
}
