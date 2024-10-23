/*
package me.udnekjupiter.physic.engine;

import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.container.EulerContainer;
import me.udnekjupiter.physic.container.RKMContainer;
import me.udnekjupiter.physic.object.CollidablePhysicObject3d;
import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import org.realityforge.vecmath.Vector3d;

import java.util.List;
//TODO Optimize engine by minimizing new Vector3d()'s creation
//TODO Reconsider usage of currentPhaseVector, because it seems to not make any practical sense while dramatically increasing algorithm's difficulty
public class RKMPhysicEngine extends PhysicEngine3d{

    public RKMPhysicEngine(PhysicScene3d scene, EnvironmentSettings settings)
    {
        this.scene = scene;
        this.settings = settings;
    }

    @Override
    public void tick() {
        List<? extends PhysicObject3d> objects = scene.getAllObjects();
        List<? extends CollidablePhysicObject3d> collidableObjects = scene.getAllCollidableObjects();
        List<? extends CollidablePhysicObject3d> collisionInitiators = scene.getAllCollisionInitiators();
    }
    protected Vector3d[] RKMethodFunction(Vector3d[] inputComponents){
        Vector3d[] resultComponents = new Vector3d[2];
        resultComponents[0] = inputComponents[1];
        resultComponents[1] = calculateAcceleration(inputComponents[0], inputComponents[1]);
        return resultComponents;
    }
    protected Vector3d calculateAcceleration(Vector3d position, Vector3d velocity){
        RKMContainer container = (RKMContainer) object.getContainer();
        Vector3d decayValue = velocity.dup().mul(settings.decayCoefficient);
        Vector3d resultAcceleration = container.appliedForce.dup().sub(decayValue);
        resultAcceleration.div(container.mass);
        return resultAcceleration;
    }

    protected Vector3d[] RKMethodCalculateNextPhaseVector(Vector3d[] basePhaseVector, Vector3d[] coefficient){
        Vector3d resultPositionComponent = basePhaseVector[0].dup().add(coefficient[0].dup().mul(settings.deltaTime/2.0));
        Vector3d resultVelocityComponent = basePhaseVector[1].dup().add(coefficient[1].dup().mul(settings.deltaTime/2.0));
        return new Vector3d[]{resultPositionComponent, resultVelocityComponent};
    }
    protected Vector3d[] RKMethodCalculateFinalPhaseVector(Vector3d[] basePhaseVector, Vector3d[] coefficient) {
        Vector3d resultPositionComponent = basePhaseVector[0].dup().add(coefficient[0].dup().mul(settings.deltaTime));
        Vector3d resultVelocityComponent = basePhaseVector[1].dup().add(coefficient[1].dup().mul(settings.deltaTime));
        return new Vector3d[]{resultPositionComponent, resultVelocityComponent};
    }

    public void calculateNextCoefficient(List<? extends PhysicObject3d> objects){
        for (PhysicObject3d object : objects) {
            RKMContainer container = (RKMContainer) object.getContainer();
            switch (container.coefficientCounter) {
                case 1 -> container.coefficient1 = RKMethodFunction(container.currentPhaseVector);
                case 2 -> container.coefficient2 = RKMethodFunction(container.currentPhaseVector);
                case 3 -> container.coefficient3 = RKMethodFunction(container.currentPhaseVector);
                case 4 -> {
                    container.coefficient4 = RKMethodFunction(container.currentPhaseVector);
                    container.coefficientCounter = 1;
                }
                default -> System.out.println("RKMCounter error in calculateNextCoefficient");
            }
        }
    }
    // TODO [mess around] WITH SWITCH
    public void calculateNextPhaseVector(List<? extends PhysicObject3d> objects){
        for (PhysicObject3d object : objects) {
            RKMContainer container = (RKMContainer) object.getContainer();
            switch (container.coefficientCounter) {
                case 1 -> {

                    container.currentPhaseVector = RKMethodCalculateNextPhaseVector(container.basePhaseVector, container.coefficient1);
                    container.coefficientCounter = 2;
                }
                case 2 -> {
                    container.currentPhaseVector = RKMethodCalculateNextPhaseVector(container.basePhaseVector, container.coefficient2);
                    container.coefficientCounter = 3;
                }
                case 3 -> {
                    container.currentPhaseVector = RKMethodCalculateFinalPhaseVector(container.basePhaseVector, container.coefficient3);
                    container.coefficientCounter = 4;
                }
                case 4 -> {}
                default -> System.out.println("RKMCounter error in calculateNextPhaseVector");
            }
        }
    }

    protected void RKMCalculatePhaseDifferentials(List<? extends PhysicObject3d> objects){
        for (PhysicObject3d object : objects) {
            if (object.isFrozen()) continue;
            RKMContainer container = (RKMContainer) object.getContainer();
            container.positionDifferential = new Vector3d(container.coefficient1[0].dup().add(
                    container.coefficient2[0].dup().mul(2)).add(
                    container.coefficient3[0].dup().mul(2)).add(
                    container.coefficient4[0].dup())).mul(settings.deltaTime/6.0);

            container.velocityDifferential = new Vector3d(container.coefficient1[1].dup().add(
                    container.coefficient2[1].dup().mul(2)).add(
                    container.coefficient3[1].dup().mul(2)).add(
                    container.coefficient4[1].dup())).mul(settings.deltaTime/6.0);
        }
    }

    protected void updatePositions(List<? extends PhysicObject3d> objects){
        for (PhysicObject3d object : objects) {
            if (object.isFrozen()) continue;
            RKMContainer container = (RKMContainer) object.getContainer();
            container.position.add(container.positionDifferential);
            container.velocity.add(container.velocityDifferential);
        }
    }
}
*/
