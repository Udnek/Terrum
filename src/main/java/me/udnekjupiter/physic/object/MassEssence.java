package me.udnekjupiter.physic.object;

import org.realityforge.vecmath.Vector3d;

public class MassEssence extends RKMObject{
    public MassEssence(Vector3d position) {
        // TEMPORARY CODE
        super(position);
        this.deltaTime = 0.001;
        this.mass = 1;
        this.decayCoefficient = 0;
        this.basePhaseVector = new Vector3d[]{position, new Vector3d(0,0,0)};
    }

    @Override
    protected Vector3d getAppliedForce(Vector3d position){
        return new Vector3d(0, -9.80665, 0).mul(mass);
    }

    @Override
    protected Vector3d RKMethodCalculateAcceleration(Vector3d position, Vector3d velocity){
        Vector3d decayValue = velocity.dup().mul(decayCoefficient);
        Vector3d resultAcceleration = new Vector3d().sub(decayValue);
        resultAcceleration.div(mass);
        return resultAcceleration;
    }

    @Override
    protected Vector3d[] RKMethodFunction(Vector3d[] inputComponents){
        Vector3d[] resultComponents = new Vector3d[2];
        resultComponents[0] = inputComponents[1];
        resultComponents[1] = RKMethodCalculateAcceleration(inputComponents[0], inputComponents[1]);
        return resultComponents;
    }

    @Override
    public void updatePosition(){
        System.out.println("MassEssence position: " + this.getPosition().asString());
        System.out.println("VD and PD: " + velocityDifferential.asString() + " -- " + positionDifferential.asString());
        setVelocity(getVelocity().add(velocityDifferential));
        setPosition(getPosition().add(positionDifferential));
        basePhaseVector = new Vector3d[]{this.getPosition(), this.getVelocity()};
    }

    @Override
    protected Vector3d[] RKMCalculatePhaseDifferentialVector(){
        Vector3d positionDifferentialComponent = new Vector3d(coefficient1[0].dup().add(
                coefficient2[0].dup().mul(2)).add(
                coefficient3[0].dup().mul(2)).add(
                coefficient4[0].dup()));
        positionDifferentialComponent.mul(deltaTime/6.0);

        Vector3d velocityDifferentialComponent = new Vector3d(coefficient1[1].dup().add(
                coefficient2[1].dup().mul(2)).add(
                coefficient3[1].dup().mul(2)).add(
                coefficient4[1].dup()));
        velocityDifferentialComponent.mul(deltaTime/6.0);

        System.out.println("VDComponent: " + velocityDifferentialComponent.asString());
        return new Vector3d[]{positionDifferentialComponent, velocityDifferentialComponent};
    }
}
