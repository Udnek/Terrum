package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.Collider;
import me.udnekjupiter.physic.container.PhysicVariableContainer;
import org.jetbrains.annotations.NotNull;

public class ColliderAnchor extends ImplementedCollidablePhysicObject3d implements CollisionInitiator{

    public ColliderAnchor(){}

    public void setCollider(Collider collider){
        this.collider = collider;
    }

    @Override
    public boolean isCollisionIgnoredWith(@NotNull Collidable object) {return false;}

    @Override
    public <T extends PhysicVariableContainer> @NotNull T getContainer(Class<T> tClass) {
        return super.getContainer(tClass);
    }

    @Override
    public void calculateForces(){}

    @Override
    public void setFrozen(boolean freeze) {
        super.setFrozen(freeze);
    }
}
