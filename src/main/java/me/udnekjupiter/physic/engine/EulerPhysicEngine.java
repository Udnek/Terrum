package me.udnekjupiter.physic.engine;

import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.object.container.EulerContainer;
import me.udnekjupiter.physic.object.container.PhysicVariableContainer;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class EulerPhysicEngine extends PhysicEngine3d {
    public EulerPhysicEngine(PhysicScene3d scene, EnvironmentSettings settings)
    {
        this.scene = scene;
        this.settings = settings;
    }

    public void tick() {
        calculatePhaseDifferentials();
        updatePositions();
    }

    public void calculatePhaseDifferentials(){
        for (PhysicObject3d object : scene.getAllObjects()) {
            EulerContainer container = (EulerContainer) object.getContainer();
            container.velocityDifferential = calculateAcceleration(object).mul(settings.deltaTime);
            container.positionDifferential = container.velocity.dup().mul(settings.deltaTime);
        }
    }

    protected Vector3d calculateAcceleration(PhysicObject3d object){
        EulerContainer container = (EulerContainer) object.getContainer();

        Vector3d appliedForce = object.getAppliedForce(container.position.dup());
        Vector3d decayValue = container.velocity.dup().mul(settings.decayCoefficient);
        Vector3d resultAcceleration = appliedForce.dup().sub(decayValue);
        resultAcceleration.div(container.mass);
        resultAcceleration.y += PhysicEngine3d.GRAVITATIONAL_ACCELERATION;

        return resultAcceleration;

    }

    protected void updatePositions(){
        for (PhysicObject3d object : scene.getAllObjects()) {
            EulerContainer container = (EulerContainer) object.getContainer();
            container.position.add(container.positionDifferential);
            container.velocity.add(container.velocityDifferential);
        }
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
