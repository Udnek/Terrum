package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.collider.SphereCollider;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class MassEssence extends RKMObject{
    public MassEssence(Vector3d position) {
        super(position);

        this.deltaTime = settings.deltaTime;
        this.mass = 10;
        this.decayCoefficient = settings.decayCoefficient;
        this.basePhaseVector = new Vector3d[]{position, new Vector3d()};
        collider = new SphereCollider(0.5, this);
    }

    @Override
    protected Vector3d getAppliedForce(Vector3d position){
        Vector3d appliedForce = new Vector3d();
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
}
