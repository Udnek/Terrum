package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.collider.SphereCollider;
import org.realityforge.vecmath.Vector3d;

public class MassEssence extends RKMObject{
    public MassEssence(Vector3d position) {
        super(position);

        this.deltaTime = settings.deltaTime;
        this.mass = 1;
        this.decayCoefficient = settings.decayCoefficient;
        this.basePhaseVector = new Vector3d[]{position, new Vector3d()};
        collider = new SphereCollider(1.5, this);
    }

    @Override
    protected Vector3d getAppliedForce(Vector3d position){
        Vector3d appliedForce = new Vector3d();
        // TODO: 7/6/2024 MOVE -9.8066 TO STATIC PHYSIC ENGINE
        appliedForce.y += -9.80665 * mass;
        appliedForce.add(getCollisionForce(position));
        return appliedForce;
    }

    @Override
    protected Vector3d RKMethodCalculateAcceleration(Vector3d position, Vector3d velocity){
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
}
