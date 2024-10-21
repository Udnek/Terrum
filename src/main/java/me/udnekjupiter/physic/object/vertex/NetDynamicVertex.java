
package me.udnekjupiter.physic.object.vertex;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import static me.udnekjupiter.physic.engine.PhysicEngine.GRAVITATIONAL_ACCELERATION;

//@SuppressWarnings("FieldMayBeFinal")
public class NetDynamicVertex extends NetVertex {
    protected double springStiffness;
    protected double springRelaxedLength;

    public NetDynamicVertex(Vector3d position) {
        super(position);
        this.velocity = new Vector3d(0, 0, 0);
        this.acceleration = new Vector3d(0, 0, 0);
        this.springStiffness = Application.ENVIRONMENT_SETTINGS.springStiffness;
        this.springRelaxedLength = Application.ENVIRONMENT_SETTINGS.springRelaxedLength;
        this.mass = Application.ENVIRONMENT_SETTINGS.vertexMass;
        this.deltaTime = Application.ENVIRONMENT_SETTINGS.deltaTime;
        this.decayCoefficient = Application.ENVIRONMENT_SETTINGS.decayCoefficient;
    }

    @Override
    protected Vector3d getAppliedForce(Vector3d position){
        Vector3d appliedForce = new Vector3d(0, 0, 0);
        for (NetVertex neighbor : neighbors) {
            Vector3d normalizedDirection = VectorUtils.getNormalizedDirection(position, neighbor.getPosition());
            double distanceToNeighbour = VectorUtils.distance(position, neighbor.getPosition());
            double distanceDifferential = distanceToNeighbour - springRelaxedLength;
            double elasticForce = springStiffness * distanceDifferential;
            appliedForce.add(normalizedDirection.mul(elasticForce));
        }
        appliedForce.add(getCollisionForce());
        return appliedForce;
    }

    @Override
    protected Vector3d calculateAcceleration(){
        Vector3d appliedForce = getAppliedForce(position.dup());
        Vector3d decayValue = velocity.dup().mul(decayCoefficient);
        Vector3d resultAcceleration = appliedForce.dup().sub(decayValue);
        resultAcceleration.div(mass);
        resultAcceleration.y += GRAVITATIONAL_ACCELERATION;

        return resultAcceleration;
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


}
