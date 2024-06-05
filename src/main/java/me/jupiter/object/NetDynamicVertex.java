package me.jupiter.object;

import me.udnek.Main;
import me.udnek.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

@SuppressWarnings("FieldMayBeFinal")
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

    public Vector3d getNormalizedDirection(Vector3d positionStart, Vector3d positionEnd){
        return positionEnd.sub(positionStart).normalize();
    }

    private Vector3d[] RKMethodFunction(Vector3d[] inputComponents){
        Vector3d[] resultComponents = new Vector3d[2];
        resultComponents[0] = inputComponents[1];
        resultComponents[1] = RKMethodCalculateAcceleration(inputComponents[0], inputComponents[1]);
        return resultComponents;
    }

    private Vector3d RKMethodCalculateAcceleration(Vector3d position, Vector3d velocity){
        Vector3d appliedForce = new Vector3d(0, 0, 0);
        for (NetVertex neighbour : neighbours) {
            Vector3d normalizedDirection = getNormalizedDirection(position, neighbour.getPosition());
            double distanceToNeighbour = VectorUtils.distance(position, neighbour.getPosition());
            double distanceDifferential = distanceToNeighbour - springRelaxedLength;
            double elasticForce = springStiffness * distanceDifferential;
            appliedForce.add(normalizedDirection.mul(elasticForce));
        }

        Vector3d decayValue = velocity.dup().mul(decayCoefficient);
        Vector3d resultAcceleration = appliedForce.sub(decayValue);
        resultAcceleration.div(mass);
        return resultAcceleration;
    }

    private Vector3d[] RKMethodCalculateNextPhaseVector(Vector3d[] basePhaseVector, Vector3d[] coefficient){
        Vector3d resultPositionComponent = basePhaseVector[0].dup().add(coefficient[0].dup().mul(deltaTime/2));
        Vector3d resultVelocityComponent = basePhaseVector[1].dup().add(coefficient[1].dup().mul(deltaTime/2));
        return new Vector3d[]{resultPositionComponent, resultVelocityComponent};
    }
    private Vector3d[] RKMethodCalculateFinalPhaseVector(Vector3d[] basePhaseVector, Vector3d[] coefficient){
        Vector3d resultPositionComponent = basePhaseVector[0].dup().add(coefficient[0].dup().mul(deltaTime));
        Vector3d resultVelocityComponent = basePhaseVector[1].dup().add(coefficient[1].dup().mul(deltaTime));
        return new Vector3d[]{resultPositionComponent, resultVelocityComponent};
    }

    private Vector3d[] RKMethodCalculatePhaseDifferentialVector(){
        Vector3d[] basePhaseVector = new Vector3d[]{this.getPosition(), this.getVelocity()};

        Vector3d[] coefficient1 = RKMethodFunction(basePhaseVector);
        Vector3d[] phaseVector1 = RKMethodCalculateNextPhaseVector(basePhaseVector, coefficient1);

        Vector3d[] coefficient2 = RKMethodFunction(phaseVector1);
        Vector3d[] phaseVector2 = RKMethodCalculateNextPhaseVector(basePhaseVector, coefficient2);

        Vector3d[] coefficient3 = RKMethodFunction(phaseVector2);
        Vector3d[] phaseVector3 = RKMethodCalculateFinalPhaseVector(basePhaseVector, coefficient3);

        Vector3d[] coefficient4 = RKMethodFunction(phaseVector3);

        Vector3d positionDifferentialComponent = new Vector3d(coefficient1[0].dup().add(
                                                              coefficient2[0].dup().mul(2)).add(
                                                              coefficient3[0].dup().mul(2)).add(
                                                              coefficient4[0].dup()));
        positionDifferentialComponent.mul(deltaTime/6);

        Vector3d velocityDifferentialComponent = new Vector3d(coefficient1[1].dup().add(
                                                              coefficient2[1].dup().mul(2)).add(
                                                              coefficient3[1].dup().mul(2)).add(
                                                              coefficient4[1].dup()));
        velocityDifferentialComponent.mul(deltaTime/6);

        return new Vector3d[]{positionDifferentialComponent, velocityDifferentialComponent};
    }

    public Vector3d dumbCalculateAcceleration() {
        Vector3d appliedForce = new Vector3d(0, 0, 0);
        for (NetVertex neighbour : neighbours) {
            if (neighbour != null) {
                Vector3d normalizedDirection = getNormalizedDirection(neighbour.getPosition(), this.getPosition());
                double distanceToNeighbour = VectorUtils.distance(this.getPosition(), neighbour.getPosition());
                double sizeDifferential = Math.abs(distanceToNeighbour - springRelaxedLength);
                double elasticForce = springStiffness * sizeDifferential;
                appliedForce.add(normalizedDirection.mul(elasticForce));
            }
        }
        Vector3d decayValue = velocity.dup().mul(decayCoefficient);
        Vector3d acceleration = appliedForce.dup().div(mass);
        acceleration.sub(decayValue);
        return acceleration;
    }

    public void dumbCalculatePositionDifferential(){
        acceleration = dumbCalculateAcceleration();
        velocity.add(acceleration.dup().mul(deltaTime));
        positionDifferential = velocity.dup().mul(deltaTime);
    }

    public void RKMethodCalculatePositionDifferential(){
        Vector3d[] phaseDifferentialVector = RKMethodCalculatePhaseDifferentialVector();
        Vector3d velocity = phaseDifferentialVector[0];
        Vector3d acceleration = phaseDifferentialVector[1];
        this.velocity.add(acceleration.dup());
        positionDifferential = velocity.dup();
    }

    public double getKineticEnergy(){
        return (Math.pow(velocity.dup().length(), 2)*mass) / 2;
    }

    public void updatePosition(){
        position.add(positionDifferential);
    }
}
