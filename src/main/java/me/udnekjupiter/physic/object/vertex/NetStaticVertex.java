package me.udnekjupiter.physic.object.vertex;

public class NetStaticVertex extends NetVertex {

    public NetStaticVertex() {
        freeze();
    }

    @Override
    public void calculateForces() {}

    @Override
    public void unfreeze(){}

}
