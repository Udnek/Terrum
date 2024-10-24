package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.PlaneCollider;
import org.jetbrains.annotations.NotNull;

public class PlaneObject extends ImplementedCollidablePhysicObject3d implements CollisionInitiator {
    public final double a;
    public final double b;
    public final double c;
    public final double d;
    public PlaneObject(double a, double b, double c, double d, double stiffness)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        collider = new PlaneCollider(stiffness, this);
    }
    @Override
    public boolean isCollisionIgnoredWith(@NotNull Collidable object) {
        return false;
    }

    public double getY(double x, double z){
        return (-a*x - c*z - d)/b;
    }

    @Override
    public void calculateForces() {}
}
