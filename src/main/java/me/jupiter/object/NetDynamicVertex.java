package me.jupiter.object;

import me.jupiter.net.NetSettings;
import me.udnek.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

@SuppressWarnings("FieldMayBeFinal")
public class NetDynamicVertex extends NetVertex{
    protected double springStiffness;
    protected double springRelaxedLength;
    protected double mass;
    protected double decayCoefficient;

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

    public void setVariables(NetSettings settings){
        this.springStiffness = settings.springStiffness;
        this.springRelaxedLength = settings.springRelaxedLength;
        this.mass = settings.vertexMass;
        this.deltaTime = settings.deltaTime;
        this.decayCoefficient = settings.decayCoefficient;
        this.phaseVector = new Vector3d[]{this.getPosition(), this.getVelocity()};
    }

    public Vector3d getNormalizedDirection(Vector3d positionStart, Vector3d positionEnd){
        return positionEnd.sub(positionStart).normalize();
    }

    @Override
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
        resultAcceleration.y += -9.80665;
        resultAcceleration.div(mass);
        return resultAcceleration;
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

    public void updatePosition() {
        setVelocity(getVelocity().add(velocityDifferential));
        setPosition(getPosition().add(positionDifferential));
        phaseVector = new Vector3d[]{this.getPosition(), this.getVelocity()};
    }
}
