package me.jupiter.object;

import me.udnek.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class NetDebugVertex extends NetDynamicVertex{
    private int internalCounter;

    @Override
    protected Vector3d RKMethodCalculateAcceleration(Vector3d position, Vector3d velocity){
        if (internalCounter <= 200) return super.RKMethodCalculateAcceleration(position, velocity);
        if (internalCounter > 202) return super.RKMethodCalculateAcceleration(position, velocity);
        internalCounter += 1;

        Vector3d appliedForce = new Vector3d(0, 0, 0);
        System.out.println("Initiated appliedForce: " + appliedForce.asString() + "\n");

        for (NetVertex neighbour : neighbours) {
            System.out.println("Processing neighbour: ");
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
}
