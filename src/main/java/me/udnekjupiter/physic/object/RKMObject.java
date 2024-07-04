package me.udnekjupiter.physic.object;

import org.realityforge.vecmath.Vector3d;

public class RKMObject extends PhysicObject {
    protected Vector3d[] currentRKMPhaseVector;
    protected Vector3d velocity;
    protected Vector3d acceleration;
    protected Vector3d positionDifferential;
    protected Vector3d velocityDifferential;
    protected double deltaTime;
    protected double decayCoefficient;
    protected double mass;

    protected Vector3d[] phaseVector;
    protected Vector3d[] coefficient1;
    protected Vector3d[] coefficient2;
    protected Vector3d[] coefficient3;
    protected Vector3d[] coefficient4;

    public RKMObject(Vector3d position) {
        super(position);
        this.velocity = new Vector3d(0,0,0);
        this.acceleration = new Vector3d(0,0,0);
        this.setCurrentRKMPhaseVector(new Vector3d[]{position, new Vector3d(0, 0, 0)});
    }

    public Vector3d getVelocity(){
        return velocity.dup();
    }
    public void setVelocity(Vector3d velocity) {
        this.velocity = velocity.dup();
    }
    public void setCurrentRKMPhaseVector(Vector3d[] currentRKMPhaseVector) {
        this.currentRKMPhaseVector = currentRKMPhaseVector;
    }
    public Vector3d[] getCurrentRKMPhaseVector() {
        return new Vector3d[]{currentRKMPhaseVector[0].dup(), currentRKMPhaseVector[1].dup()};
    }
    public Vector3d getCurrentRKMPosition(){return getCurrentRKMPhaseVector()[0].dup();}

    protected Vector3d[] RKMethodFunction(Vector3d[] inputComponents){
        Vector3d[] resultComponents = new Vector3d[2];
        resultComponents[0] = inputComponents[1];
        resultComponents[1] = RKMethodCalculateAcceleration(inputComponents[0], inputComponents[1]);
        return resultComponents;
    }

    protected Vector3d[] RKMethodCalculateNextPhaseVector(Vector3d[] basePhaseVector, Vector3d[] coefficient){
        Vector3d resultPositionComponent = basePhaseVector[0].dup().add(coefficient[0].dup().mul(deltaTime/2.0));
        Vector3d resultVelocityComponent = basePhaseVector[1].dup().add(coefficient[1].dup().mul(deltaTime/2.0));
        return new Vector3d[]{resultPositionComponent, resultVelocityComponent};
    }
    protected Vector3d[] RKMethodCalculateFinalPhaseVector(Vector3d[] basePhaseVector, Vector3d[] coefficient) {
        Vector3d resultPositionComponent = basePhaseVector[0].dup().add(coefficient[0].dup().mul(deltaTime));
        Vector3d resultVelocityComponent = basePhaseVector[1].dup().add(coefficient[1].dup().mul(deltaTime));
        return new Vector3d[]{resultPositionComponent, resultVelocityComponent};
    }

    public void calculateCoefficient1(){
        coefficient1 = RKMethodFunction(getCurrentRKMPhaseVector());
    }
    public void updateRKMPhaseVector1(){
        setCurrentRKMPhaseVector(RKMethodCalculateNextPhaseVector(phaseVector, coefficient1));
    }
    public void calculateCoefficient2(){
        coefficient2 = RKMethodFunction(getCurrentRKMPhaseVector());
    }
    public void updateRKMPhaseVector2(){
        setCurrentRKMPhaseVector(RKMethodCalculateNextPhaseVector(phaseVector, coefficient2));
    }
    public void calculateCoefficient3(){
        coefficient3 = RKMethodFunction(getCurrentRKMPhaseVector());
    }
    public void updateRKMPhaseVector3(){
        setCurrentRKMPhaseVector(RKMethodCalculateFinalPhaseVector(phaseVector, coefficient3));
    }
    public void calculateCoefficient4(){
        coefficient4 = RKMethodFunction(getCurrentRKMPhaseVector());
    }

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

        return new Vector3d[]{positionDifferentialComponent, velocityDifferentialComponent};
    }

    public void RKMCalculatePositionDifferential(){
        Vector3d[] phaseDifferentialVector = RKMCalculatePhaseDifferentialVector();
        Vector3d velocity = phaseDifferentialVector[0];
        Vector3d acceleration = phaseDifferentialVector[1];
        velocityDifferential = acceleration.dup();
        positionDifferential = velocity.dup();
    }

    public void updatePosition() {
        setVelocity(getVelocity().add(velocityDifferential));
        setPosition(getPosition().add(positionDifferential));
        phaseVector = new Vector3d[]{this.getPosition(), this.getVelocity()};
    }

    protected Vector3d RKMethodCalculateAcceleration(Vector3d position, Vector3d velocity) {
        return null; //Should be overridden by extending classes
    }

    protected Vector3d getAppliedForce(Vector3d position){
        return null; //Should be overridden by extending classes
    }
}
