
package me.udnekjupiter.physic.object.vertex;

import me.udnekjupiter.app.StandartApplication;
import me.udnekjupiter.util.VectorUtils;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class NetDynamicVertex extends NetVertex {
    protected double springStiffness;
    protected double springRelaxedLength;
    protected double decayCoefficient;

    public NetDynamicVertex() {
        this.springStiffness = StandartApplication.ENVIRONMENT_SETTINGS.springStiffness;
        this.springRelaxedLength = StandartApplication.ENVIRONMENT_SETTINGS.springRelaxedLength;
        this.decayCoefficient = StandartApplication.ENVIRONMENT_SETTINGS.decayCoefficient;
    }

    @Override
    public @NotNull Vector3d getAppliedForce(@NotNull Vector3d position){
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
}
