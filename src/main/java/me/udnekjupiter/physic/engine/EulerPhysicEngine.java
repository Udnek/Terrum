package me.udnekjupiter.physic.engine;

import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.Collider;
import me.udnekjupiter.physic.collision.CollisionCalculator;
import me.udnekjupiter.physic.object.PhysicObject;
import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.object.container.EulerContainer;
import me.udnekjupiter.physic.object.container.PhysicVariableContainer;
import me.udnekjupiter.physic.object.container.VariableContainer;
import me.udnekjupiter.physic.scene.PhysicScene3d;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class EulerPhysicEngine extends PhysicEngine3d<PhysicScene3d> {
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
    }
}
