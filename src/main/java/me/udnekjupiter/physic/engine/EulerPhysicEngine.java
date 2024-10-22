package me.udnekjupiter.physic.engine;

import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.object.container.EulerContainer;
import me.udnekjupiter.physic.object.container.PhysicVariableContainer;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.List;

public class EulerPhysicEngine extends PhysicEngine3d {
    public EulerPhysicEngine(PhysicScene3d scene, EnvironmentSettings settings)
    {
        this.scene = scene;
        this.settings = settings;
    }

    public void tick() {
        List<? extends PhysicObject3d> objects = scene.getAllObjects();
        for (int i = 0; i < settings.iterationsPerTick; i++) {
            recalculateForces(objects);
            calculatePhaseDifferentials(objects);
            updatePositions(objects);
        }
    }

    public void calculatePhaseDifferentials(List<? extends PhysicObject3d> objects){
        for (PhysicObject3d object : objects) {
            EulerContainer container = (EulerContainer) object.getContainer();
            container.velocityDifferential = calculateAcceleration(object).mul(settings.deltaTime);
            container.positionDifferential = container.velocity.dup().mul(settings.deltaTime);
        }
    }

    protected void updatePositions(List<? extends PhysicObject3d> objects){
        for (PhysicObject3d object : objects) {
            EulerContainer container = (EulerContainer) object.getContainer();
            container.position.add(container.positionDifferential);
            container.velocity.add(container.velocityDifferential);
        }
    }

    protected void recalculateForces(List<? extends PhysicObject3d> objects){
        for (PhysicObject3d object : objects) {
            object.getContainer().appliedForce.mul(0);
        }
        for (PhysicObject3d object : objects) {
            object.calculateForces(object.getContainer().position);
        }
    }

    protected Vector3d calculateAcceleration(PhysicObject3d object){
        EulerContainer container = (EulerContainer) object.getContainer();
        //Vector3d decayValue = container.velocity.dup().mul(settings.decayCoefficient);
        Vector3d resultAcceleration = container.appliedForce.dup();//.sub(decayValue);
        resultAcceleration.div(container.mass);

        System.out.println(resultAcceleration.asString());
        return resultAcceleration;

    }

    @Override
    public void initialize() {
        super.initialize();
        for (PhysicObject3d object : scene.getAllObjects()) {
            object.setContainer(new EulerContainer(object.getContainer()));
        }
    }

    @Override
    public @NotNull EnvironmentSettings getSettings() {
        return settings;
    }

    @Override
    public void addObject(@NotNull PhysicObject3d object) {
        scene.addObject(object);
        PhysicVariableContainer container = object.getContainer();
        EulerContainer newContainer = new EulerContainer(container);
        object.setContainer(newContainer);
    }
}
