package me.udnekjupiter.physic.collision;

import me.udnekjupiter.Main;
import me.udnekjupiter.physic.object.SphereObject;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

@SuppressWarnings("ExtractMethodRecommender")
public abstract class CollisionCalculator {
    public static Vector3d getAtomicCollisionForce(Collidable thisObject){
        Vector3d collisionForce = new Vector3d();
        for (Collidable collidingObject : thisObject.getCollidingObjects()) {
            if (!(collidingObject.getCollider() instanceof SphereCollider otherSphereCollider)) continue;

            Vector3d thisPosition = thisObject.getPosition();
            Vector3d otherPosition = collidingObject.getPosition();
            Vector3d normalizedDirection = VectorUtils.getNormalizedDirection(otherPosition, thisPosition);
            double distance = VectorUtils.distance(thisPosition, otherPosition);
            double maxDepth = Main.getMain().getApplication().getPhysicEngine().getSettings().maxDepth;
            double thisCriticalRadius = ((SphereCollider) thisObject.getCollider()).radius - maxDepth;
            double otherCriticalRadius = otherSphereCollider.radius - maxDepth;
            double criticalDistance = thisCriticalRadius + otherCriticalRadius;
            double subCriticalDistance = criticalDistance + (maxDepth*0.0625)*2;
            double depth = Math.max(Math.abs(distance - criticalDistance), Math.abs(subCriticalDistance - criticalDistance));
            Vector3d collisionForceCache = normalizedDirection.mul(1 / (Math.pow(depth, 3)));
            collisionForce.add(collisionForceCache);

        }
        return collisionForce;
    }

    public static Vector3d getHookeCollisionForce(Collider thisCollider){
        Vector3d collisionForce = new Vector3d();
        double maxDepth = Main.getMain().getApplication().getPhysicEngine().getSettings().maxDepth;
        if (thisCollider instanceof SphereCollider sphereCollider){
            for (Collider otherCollider : thisCollider.getCurrentCollisions()) {
                if (!(otherCollider instanceof SphereCollider otherSphereCollider)) continue;
                Vector3d thisPosition = thisCollider.parent.getActualPosition();
                Vector3d otherPosition = otherSphereCollider.parent.getActualPosition();
                double distance = VectorUtils.distance(thisPosition, otherPosition);
                double maxDistance = sphereCollider.radius + otherSphereCollider.radius;
                double thisCriticalRadius = sphereCollider.radius - maxDepth;
                double otherCriticalRadius = otherSphereCollider.radius - maxDepth;
                double minCriticalRadiiDistance = thisCriticalRadius + otherCriticalRadius;
                double stableDistance = maxDistance - minCriticalRadiiDistance;
                double criticalRadiiDistance = distance - (thisCriticalRadius + otherCriticalRadius);
                double depth = stableDistance - criticalRadiiDistance;
                Vector3d normalizedDirection = VectorUtils.getNormalizedDirection(otherPosition, thisPosition);
                Vector3d collisionForceCache = normalizedDirection.mul(Math.abs(depth) * sphereCollider.stiffness); // TODO: 7/9/2024 STIFFNESS????
                collisionForce.add(collisionForceCache);
            }
        }
        return collisionForce;
    }
}
