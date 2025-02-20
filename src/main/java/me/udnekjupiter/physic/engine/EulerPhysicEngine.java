package me.udnekjupiter.physic.engine;

import me.udnekjupiter.Main;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.container.EulerContainer;
import me.udnekjupiter.physic.container.PhysicVariableContainer;
import me.udnekjupiter.physic.object.CollidablePhysicObject3d;
import me.udnekjupiter.physic.object.ColliderAnchor;
import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import me.udnekjupiter.util.Debugger;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.vector.Vector3d;

import java.util.List;

public class EulerPhysicEngine extends PhysicEngine3d {
    public EulerPhysicEngine(PhysicScene3d scene, EnvironmentSettings settings) {
        this.scene = scene;
        this.settings = settings;
    }

    @Override
    public void tick() {
        super.tick();
        List<? extends PhysicObject3d> objects = scene.getAllObjects();
        List<? extends CollidablePhysicObject3d> collidableObjects = scene.getAllCollidableObjects();
        List<? extends CollidablePhysicObject3d> collisionInitiators = scene.getAllCollisionInitiators();
        for (int i = 0; i < settings.iterationsPerTick; i++) {
            updateColliders(collidableObjects, collisionInitiators);
            recalculateForces(objects);
            calculateVelocityDifferentials(objects);
            updatePositions(objects);
        }
        Main.getMain().getDebugger().tick();
    }

    protected void recalculateForces(@NotNull List<? extends PhysicObject3d> objects){
        for (PhysicObject3d object : objects) object.getContainer().appliedForce.mul(0);
        for (PhysicObject3d object : objects) object.calculateForces();
    }

    public void calculateVelocityDifferentials(List<? extends PhysicObject3d> objects){
        for (PhysicObject3d object : objects) {
            EulerContainer container = (EulerContainer) object.getContainer();
            container.velocityDifferential = calculateAcceleration(object).mul(settings.deltaTime);
        }
    }

    protected void updatePositions(List<? extends PhysicObject3d> objects){
        for (PhysicObject3d object : objects) {
            if (object.isFrozen()) continue;
            EulerContainer container = (EulerContainer) object.getContainer();
            container.velocity.add(container.velocityDifferential);
            container.position.add(container.velocity.dup().mul(settings.deltaTime));
        }
    }


    protected @NotNull Vector3d calculateAcceleration(@NotNull PhysicObject3d object){
        EulerContainer container = (EulerContainer) object.getContainer();
//        Vector3d decayValue = container.velocity.dup().mul(settings.decayCoefficient);
//        Vector3d resultAcceleration = container.appliedForce.dup().div(container.mass);
//        resultAcceleration.sub(decayValue);
        return container.appliedForce.dup().div(container.mass);
    }

    @Override
    public void initialize() {
        super.initialize();
        for (PhysicObject3d object : scene.getAllObjects()) {
            object.setContainer(new EulerContainer(object.getContainer()));
        }
    }

    public void updateColliders(List<? extends CollidablePhysicObject3d> objects,
                                List<? extends CollidablePhysicObject3d> collisionInitiators){

        for (CollidablePhysicObject3d object : objects) {
            object.clearCollidingObjects();
        }
        for (CollidablePhysicObject3d targetObject : collisionInitiators) {
            for (CollidablePhysicObject3d anotherObject : objects) {
                if (targetObject == anotherObject) continue;
                if (targetObject.isCollisionIgnoredWith(anotherObject)) continue;
                if (targetObject.getCollider().collidingObjectIsAlreadyListed(anotherObject)) continue;
                if (!targetObject.getCollider().collidesWith(anotherObject.getCollider())) continue;
                targetObject.getCollider().addCollision(anotherObject.getCollider());
                anotherObject.getCollider().addCollision(targetObject.getCollider());
            }
        }
    }

    @Override
    public void addObject(@NotNull PhysicObject3d object) {
        scene.addObject(object);
        PhysicVariableContainer container = object.getContainer();
        EulerContainer newContainer = new EulerContainer(container);
        object.setContainer(newContainer);
    }

    @Override
    public void removeObject(@NotNull PhysicObject3d object) {
        scene.removeObject(object);
    }
}
