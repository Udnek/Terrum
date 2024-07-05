package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.collider.SphereCollider;
import org.realityforge.vecmath.Vector3d;

public class MassEssence extends RKMObject{
    public MassEssence(Vector3d position) {
        // TEMPORARY CODE
        super(position);

        this.deltaTime = settings.deltaTime;
        this.mass = 10;
        this.decayCoefficient = settings.decayCoefficient;
        this.basePhaseVector = new Vector3d[]{position, new Vector3d(0,0,0)};
        collider = new SphereCollider(0.2, this);
    }

    @Override
    protected Vector3d getAppliedForce(Vector3d position){
        return new Vector3d(0, -9.80665, 0).mul(mass);
    }

//    @Override
//    protected Vector3d getCollisionForce() {
//        for (RKMObject collidingObject : collidingObjects) {
//
//        }
//    }

    @Override
    protected Vector3d RKMethodCalculateAcceleration(Vector3d position, Vector3d velocity){
        Vector3d decayValue = velocity.dup().mul(decayCoefficient);
        Vector3d resultAcceleration = new Vector3d().sub(decayValue);
        resultAcceleration.div(mass);
        return resultAcceleration;
    }
}
