package me.jupiter.object;

import me.udnek.utils.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import static me.jupiter.PhysicsUtility.valueToVector;

public class NetDynamicVertex extends NetVertex{
    private Vector3d velocity;
    private Vector3d acceleration;
    private Vector3d positionDifferential;
    private double springStiffness;
    private double springRelaxedLength;
    private double mass;
    private double deltaTime;
    private double decayCoefficient;


    public NetDynamicVertex(Vector3d position) {
        super(position);
        this.velocity = new Vector3d(0, 0, 0);
        this.acceleration = new Vector3d(0, 0, 0);
        this.springStiffness = 1;
        this.springRelaxedLength = 1;
        this.mass = 1;
        this.deltaTime = 0.1;
        this.decayCoefficient = 0;
    }

    public Vector3d getVelocity(){
        return this.velocity.dup();
    }
    public void setVariables(double springStiffness,
                             double springRelaxedLength,
                             double mass,
                             double deltaTime,
                             double decayCoefficient){
        this.springStiffness = springStiffness;
        this.springRelaxedLength = springRelaxedLength;
        this.mass = mass;
        this.deltaTime = deltaTime;
        this.decayCoefficient = decayCoefficient;
    }

    public Vector3d getNormalizedDirection(Vector3d positionEnd, Vector3d positionStart){
        return positionEnd.sub(positionStart).normalize();
    }

    private Vector3d[] RKMethodFunction(Vector3d[] inputComponents){    //Runge-Kutta method function
        Vector3d[] resultComponents = new Vector3d[2];
        resultComponents[0] = inputComponents[1];
        resultComponents[1] = RKMethodCalculateAcceleration(inputComponents[0], inputComponents[1]);
        return resultComponents;
    }

    private Vector3d RKMethodCalculateAcceleration(Vector3d position, Vector3d velocity){
        Vector3d appliedForce = new Vector3d(0, 0, 0);
        for (NetVertex neighbour : neighbours) {
            if (neighbour != null) {
                Vector3d normalizedDirection = getNormalizedDirection(neighbour.getPosition(), position);
                double distanceToNeighbour = VectorUtils.distance(position, neighbour.getPosition());
                double sizeDifferential = Math.abs(distanceToNeighbour - springRelaxedLength);
                double elasticForce = springStiffness * sizeDifferential;
                appliedForce.add(normalizedDirection.mul(elasticForce));
            }
        }
        Vector3d decayValue = velocity.dup().mul(decayCoefficient);
        Vector3d resultAcceleration = appliedForce.div(mass);
        resultAcceleration.sub(decayValue);
        return resultAcceleration;
    }

    private Vector3d[] RKMethodCalculateNextPhaseVector(Vector3d[] previousPhaseVector, Vector3d[] coefficient){
        Vector3d resultPositionComponent = previousPhaseVector[0].dup().add(coefficient[0].dup().mul(deltaTime/2));
        Vector3d resultVelocityComponent = previousPhaseVector[1].dup().add(coefficient[1].dup().mul(deltaTime/2));
        return new Vector3d[]{resultPositionComponent, resultVelocityComponent};
    }
    private Vector3d[] RKMethodCalculateFinalPhaseVector(Vector3d[] previousPhaseVector, Vector3d[] coefficient){
        Vector3d resultPositionComponent = previousPhaseVector[0].dup().add(coefficient[0].dup().mul(deltaTime));
        Vector3d resultVelocityComponent = previousPhaseVector[1].dup().add(coefficient[1].dup().mul(deltaTime));
        return new Vector3d[]{resultPositionComponent, resultVelocityComponent};
    }

    private Vector3d[] RKMethodCalculatePhaseDifferentialVector(){
        Vector3d[] phaseVectorBase = new Vector3d[]{this.getPosition(), this.getVelocity()};

        Vector3d[] coefficient1 = RKMethodFunction(phaseVectorBase);
        Vector3d[] phaseVector1 = RKMethodCalculateNextPhaseVector(phaseVectorBase, coefficient1);

        Vector3d[] coefficient2 = RKMethodFunction(phaseVector1);
        Vector3d[] phaseVector2 = RKMethodCalculateNextPhaseVector(phaseVector1, coefficient2);

        Vector3d[] coefficient3 = RKMethodFunction(phaseVector2);
        Vector3d[] phaseVector3 = RKMethodCalculateFinalPhaseVector(phaseVector2, coefficient3);

        Vector3d[] coefficient4 = RKMethodFunction(phaseVector3);

        Vector3d positionDifferentialComponent = new Vector3d(coefficient1[0].dup().add(
                                                              coefficient2[0].dup().mul(2).add(
                                                              coefficient3[0].dup().mul(2).add(
                                                              coefficient4[0].dup()))));
        positionDifferentialComponent.mul(deltaTime/6);

        Vector3d velocityDifferentialComponent = new Vector3d(coefficient1[1].dup().add(
                                                              coefficient2[1].dup().mul(2).add(
                                                              coefficient3[1].dup().mul(2).add(
                                                              coefficient4[1].dup()))));
        velocityDifferentialComponent.mul(deltaTime/6);

        return new Vector3d[]{positionDifferentialComponent, velocityDifferentialComponent};
    }

//    public void calculateAppliedForce() {
//        Vector3d appliedForce = new Vector3d(0, 0, 0);
//        for (NetVertex neighbour : neighbours) {
//            if (neighbour != null) {
//                Vector3d normalizedDirection = getNormalizedDirection(neighbour.getPosition(), this.getPosition());
//                double distanceToNeighbour = VectorUtils.distance(this.getPosition(), neighbour.getPosition());
//                double sizeDifferential = Math.abs(distanceToNeighbour - springRelaxedLength);
//                double elasticForce = springStiffness * sizeDifferential;
//                appliedForce.add(normalizedDirection.mul(elasticForce));
//            }
//        }
//    }

    public void calculatePositionDifferential(){
        Vector3d[] phaseDifferentialVector = RKMethodCalculatePhaseDifferentialVector();
        Vector3d velocity = phaseDifferentialVector[0];
        Vector3d acceleration = phaseDifferentialVector[1];
        this.velocity.add(acceleration.dup());
        positionDifferential = velocity.dup();
    }

    public void updatePosition(){
        position.add(positionDifferential);
    }
}
