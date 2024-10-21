package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.SphereCollider;
import me.udnekjupiter.physic.engine.PhysicEngine3d;
import org.realityforge.vecmath.Vector3d;

public class SphereObject extends StandardObject3d {
    public SphereObject(Vector3d position, double colliderRadius, double stiffness, double mass) {
        super(position);
        this.deltaTime = settings.deltaTime;
        this.mass = mass;
        this.decayCoefficient = settings.decayCoefficient;
        collider = new SphereCollider(colliderRadius, stiffness, this);
    }

    public SphereObject(Vector3d position, double colliderRadius, double mass){
        this(position, colliderRadius, 10_000, mass);
    }

    @Override
    protected Vector3d getAppliedForce(Vector3d position){
        Vector3d appliedForce = new Vector3d();
        appliedForce.y += PhysicEngine3d.GRAVITATIONAL_ACCELERATION * mass;
        appliedForce.add(getCollisionForce());
        return appliedForce;
    }

    @Override
    protected Vector3d calculateAcceleration(){
        Vector3d resultAcceleration = getAppliedForce(position);
        Vector3d decayValue = velocity.dup().mul(decayCoefficient);
        resultAcceleration.sub(decayValue);
        resultAcceleration.div(mass);
        return resultAcceleration;
    }

    @Override
    public SphereCollider getCollider() {
        return (SphereCollider) collider;
    }

    @Override
    public boolean isCollisionIgnored(Collidable object) {return false;}
}
