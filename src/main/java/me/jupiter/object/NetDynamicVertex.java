package me.jupiter.object;

import me.jupiter.net.NetSettings;
import me.udnek.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

@SuppressWarnings("FieldMayBeFinal")
public class NetDynamicVertex extends NetVertex{
    protected Vector3d velocity;
    protected Vector3d acceleration;
    protected Vector3d positionDifferential;
    protected Vector3d velocityDifferential;
    protected double springStiffness;
    protected double springRelaxedLength;
    protected double mass;
    protected double deltaTime;
    protected double decayCoefficient;

    protected Vector3d[] phaseVector;
    protected Vector3d[] coefficient1;
    protected Vector3d[] coefficient2;
    protected Vector3d[] coefficient3;
    protected Vector3d[] coefficient4;

    protected NetDynamicVertex(){}
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

    @Override
    public Vector3d getVelocity(){
        return this.velocity.dup();
    }

    public void setVariables(NetSettings settings){
        this.springStiffness = settings.springStiffness;
        this.springRelaxedLength = settings.springRelaxedLength;
        this.mass = settings.vertexMass;
        this.deltaTime = settings.deltaTime;
        this.decayCoefficient = settings.decayCoefficient;
        this.currentRKMPhaseVector = new Vector3d[]{this.getPosition(), this.getVelocity()};
        this.phaseVector = new Vector3d[]{this.getPosition(), this.getVelocity()};
    }

    public Vector3d getNormalizedDirection(Vector3d positionStart, Vector3d positionEnd){
        return positionEnd.sub(positionStart).normalize();
    }

    protected Vector3d[] RKMethodFunction(Vector3d[] inputComponents){
        Vector3d[] resultComponents = new Vector3d[2];
        resultComponents[0] = inputComponents[1];
        resultComponents[1] = RKMethodCalculateAcceleration(inputComponents[0], inputComponents[1]);
        return resultComponents;
    }

    protected Vector3d RKMethodCalculateAcceleration(Vector3d position, Vector3d velocity){
        Vector3d appliedForce = new Vector3d(0, 0, 0);
        for (NetVertex neighbor : neighbors) {
            Vector3d normalizedDirection = getNormalizedDirection(position, neighbor.getCurrentRKMPosition());
            double distanceToNeighbour = VectorUtils.distance(position, neighbor.getCurrentRKMPosition());
            double distanceDifferential = distanceToNeighbour - springRelaxedLength;
            double elasticForce = springStiffness * distanceDifferential;
            appliedForce.add(normalizedDirection.mul(elasticForce));
        }

        Vector3d decayValue = velocity.dup().mul(decayCoefficient);
        Vector3d resultAcceleration = appliedForce.dup().sub(decayValue);
        resultAcceleration.div(mass);
        return resultAcceleration;
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
        coefficient1 = RKMethodFunction(currentRKMPhaseVector);
    }
    public void updateRKMPhaseVector1(){
        currentRKMPhaseVector = RKMethodCalculateNextPhaseVector(phaseVector, coefficient1);
    }
    public void calculateCoefficient2(){
        coefficient2 = RKMethodFunction(currentRKMPhaseVector);
    }
    public void updateRKMPhaseVector2(){
        currentRKMPhaseVector = RKMethodCalculateNextPhaseVector(phaseVector, coefficient2);
    }
    public void calculateCoefficient3(){
        coefficient3 = RKMethodFunction(currentRKMPhaseVector);
    }
    public void updateRKMPhaseVector3(){
        currentRKMPhaseVector = RKMethodCalculateFinalPhaseVector(phaseVector, coefficient3);
    }
    public void calculateCoefficient4(){
        coefficient4 = RKMethodFunction(currentRKMPhaseVector);
    }

    protected Vector3d[] RKMethodCalculatePhaseDifferentialVector(){
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

    public Vector3d EMethodCalculateAcceleration() {
        Vector3d appliedForce = new Vector3d(0, 0, 0);
        for (NetVertex neighbor : neighbors) {
            Vector3d normalizedDirection = getNormalizedDirection(this.getPosition(), neighbor.getPosition());
            double distanceToNeighbour = VectorUtils.distance(this.getPosition(), neighbor.getPosition());
            double distanceDifferential = distanceToNeighbour - springRelaxedLength;
            double elasticForce = springStiffness * distanceDifferential;
            appliedForce.add(normalizedDirection.mul(elasticForce));
        }

        Vector3d decayValue = velocity.dup().mul(decayCoefficient);
        Vector3d acceleration = appliedForce.dup().sub(decayValue);
        acceleration.div(mass);
        return acceleration;
    }
    public void EMethodCalculatePositionDifferential(){
        acceleration = EMethodCalculateAcceleration();
        velocityDifferential = acceleration.dup().mul(deltaTime);
        positionDifferential = velocity.dup().mul(deltaTime);
    }

    public void RKMethodCalculatePositionDifferential(){
        Vector3d[] phaseDifferentialVector = RKMethodCalculatePhaseDifferentialVector();
        Vector3d velocity = phaseDifferentialVector[0];
        Vector3d acceleration = phaseDifferentialVector[1];
        velocityDifferential = acceleration.dup();
        positionDifferential = velocity.dup();
    }

    public double getKineticEnergy(){
        return (Math.pow(velocity.dup().length(), 2)*mass) / 2;
    }
    public double getPotentialEnergy() {
        double potentialEnergy = 0;
        for (NetVertex neighbour : neighbors) {
            double distanceToNeighbour = VectorUtils.distance(position, neighbour.getPosition());
            double distanceDifferential = distanceToNeighbour - springRelaxedLength;
            if (neighbour instanceof NetStaticVertex) {
                potentialEnergy += (Math.pow(distanceDifferential, 2) * springStiffness) / 2;
            } else {
                potentialEnergy += (Math.pow(distanceDifferential, 2) * springStiffness) / 4;
            }
        }
        return potentialEnergy;
    }

    public void updatePosition(){
        velocity.add(velocityDifferential);
        position.add(positionDifferential);
        phaseVector = new Vector3d[]{this.getPosition(), this.getVelocity()};
    }
}
