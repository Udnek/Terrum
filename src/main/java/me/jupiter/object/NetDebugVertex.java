package me.jupiter.object;

import me.udnek.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class NetDebugVertex extends NetDynamicVertex{
    private int internalCounter;

    public NetDebugVertex(Vector3d position)
    {
        super(position);
    }

    private boolean isCurrentIterationObserved(){
        if (internalCounter < 0) return true;
        if (internalCounter > 2) return true;
        return false;
    }

    @Override
    protected Vector3d RKMethodCalculateAcceleration(Vector3d position, Vector3d velocity){
        if (isCurrentIterationObserved()) return super.RKMethodCalculateAcceleration(position, velocity);

        System.out.println("Current tick: " + internalCounter + "\n");

        System.out.println("Self position: " + this.getPosition().asString());
        System.out.println("Self velocity: " + this.velocity.dup().asString());
        System.out.println("Self acceleration: " + this.acceleration.dup().asString() + "\n");

        Vector3d appliedForce = new Vector3d(0, 0, 0);
        System.out.println("Initiated appliedForce: " + appliedForce.asString() + "\n");

        for (NetVertex neighbor : neighbors) {
            System.out.println("Processing neighbor: " + neighbor.getClass());
            System.out.println("Neighbor position: " + neighbor.getPosition().asString());
            Vector3d normalizedDirection = getNormalizedDirection(position, neighbor.getPosition());
            System.out.println("Normalized direction: " + normalizedDirection.asString());
            double distanceToNeighbor = VectorUtils.distance(position, neighbor.getPosition());
            System.out.println("Distance to neighbor: " + distanceToNeighbor);
            double distanceDifferential = distanceToNeighbor - springRelaxedLength;
            System.out.println("Distance differential:  " + distanceToNeighbor + " - " + springRelaxedLength + " = " + distanceDifferential);
            double elasticForce = springStiffness * distanceDifferential;
            System.out.println("Elastic force:  " + springStiffness + " * " + distanceDifferential + " = " + elasticForce);
            appliedForce.add(normalizedDirection.mul(elasticForce));
            System.out.println("Current applied force: " + appliedForce.asString() + "\n");
        }

        System.out.println("Final applied force: " + appliedForce.asString());
        Vector3d decayValue = velocity.dup().mul(decayCoefficient);
        System.out.println("Decay value: " + decayValue.asString());
        Vector3d resultAcceleration = appliedForce.sub(decayValue);
        System.out.println("Raw acceleration: " + resultAcceleration.asString());
        resultAcceleration.div(mass);
        System.out.println("Acceleration: " + resultAcceleration.asString() + "\n\n\n\n\n\n\n");
        return resultAcceleration;
    }
    @Override
    public void RKMethodCalculatePositionDifferential(){
        internalCounter += 1;
        if (isCurrentIterationObserved()) {super.RKMethodCalculatePositionDifferential();return;}

        Vector3d[] phaseDifferentialVector = RKMethodCalculatePhaseDifferentialVector();
        Vector3d velocity = phaseDifferentialVector[0];
        Vector3d acceleration = phaseDifferentialVector[1];

        System.out.println("Result velocity: " + velocity.asString());
        System.out.println("Result acceleration: " + acceleration.asString() + "\n");
        System.out.println("----------------------------------------------------------------------\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        velocityDifferential = acceleration.dup();
        positionDifferential = velocity.dup();
    }
//    @Override
//    protected Vector3d[] RKMethodCalculatePhaseDifferentialVector(){
//        Vector3d[] basePhaseVector = new Vector3d[]{this.getPosition(), this.getVelocity()};
//
//        Vector3d[] coefficient1 = RKMethodFunction(basePhaseVector);
//        Vector3d[] phaseVector1 = RKMethodCalculateNextPhaseVector(basePhaseVector, coefficient1);
//
//        Vector3d[] coefficient2 = RKMethodFunction(phaseVector1);
//        Vector3d[] phaseVector2 = RKMethodCalculateNextPhaseVector(basePhaseVector, coefficient2);
//
//        Vector3d[] coefficient3 = RKMethodFunction(phaseVector2);
//        Vector3d[] phaseVector3 = RKMethodCalculateFinalPhaseVector(basePhaseVector, coefficient3);
//
//        Vector3d[] coefficient4 = RKMethodFunction(phaseVector3);
//
//        Vector3d positionDifferentialComponent = new Vector3d(coefficient1[0].dup().add(
//                coefficient2[0].dup().mul(2)).add(
//                coefficient3[0].dup().mul(2)).add(
//                coefficient4[0].dup()));
//        positionDifferentialComponent.mul(deltaTime/6);
//
//        Vector3d velocityDifferentialComponent = new Vector3d(coefficient1[1].dup().add(
//                coefficient2[1].dup().mul(2)).add(
//                coefficient3[1].dup().mul(2)).add(
//                coefficient4[1].dup()));
//        velocityDifferentialComponent.mul(deltaTime/6);
//
//        return new Vector3d[]{positionDifferentialComponent, velocityDifferentialComponent};
//    }
}



