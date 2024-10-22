
package me.udnekjupiter.physic.object.vertex;

import me.udnekjupiter.Main;
import me.udnekjupiter.app.StandartApplication;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.engine.PhysicEngine3d;
import me.udnekjupiter.util.VectorUtils;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class NetDynamicVertex extends NetVertex {
    protected double springStiffness;
    protected double springRelaxedLength;

    public NetDynamicVertex() {
        EnvironmentSettings settings = Main.getMain().getApplication().getPhysicEngine().getSettings();
        this.springStiffness = settings.springStiffness;
        this.springRelaxedLength = settings.springRelaxedLength;
    }

    @Override
    public void calculateForces(@NotNull Vector3d position) {
        container.appliedForce.y += PhysicEngine3d.GRAVITATIONAL_ACCELERATION * container.mass;
        container.appliedForce.add(getCollisionForce());
    }
}
