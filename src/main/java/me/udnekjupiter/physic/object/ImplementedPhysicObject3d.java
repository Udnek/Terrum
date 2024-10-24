package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.container.PhysicVariableContainer;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public abstract class ImplementedPhysicObject3d implements PhysicObject3d {

    protected PhysicVariableContainer container;
    protected boolean frozen;

    public ImplementedPhysicObject3d(){
        this(new Vector3d());
    }

    public ImplementedPhysicObject3d(@NotNull Vector3d position){
        container = new PhysicVariableContainer(position);
    }

    @Override
    public @NotNull PhysicVariableContainer getContainer() {return container;}
    @Override
    public void setContainer(@NotNull PhysicVariableContainer container) {this.container = container;}

    @Override
    public void freeze() {
        frozen = true;
        container.velocity.mul(0);
    }
    @Override
    public void unfreeze() {frozen = false;}
    @Override
    public boolean isFrozen() {return frozen;}

    @Override
    public @NotNull Vector3d getPosition() {
        return container.getPosition().dup();
    }
    @Override
    public @NotNull Vector3d getVelocity() {
        return container.getVelocity().dup();
    }

    @Override
    public void setPosition(@NotNull Vector3d position) {
        container.position = position;
    }

    @Override
    public void move(@NotNull Vector3d position) {
        setPosition(getPosition().add(position));
    }
    @Override
    public void move(double x, double y, double z) {
        setPosition(getPosition().add(x,y,z));
    }

    @Override
    public void reset() {
        setPosition(container.initialPosition.dup());
        container.velocity.mul(0);
    }

}
