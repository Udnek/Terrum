package me.udnekjupiter.physic.collision;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.physic.object.RKMObject;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import static me.udnekjupiter.physic.engine.PhysicEngine.MAX_DEPTH;

@SuppressWarnings("ExtractMethodRecommender")
public abstract class CollisionCalculator {
    public static Vector3d getAtomicCollisionForce(RKMObject thisObject){
        Vector3d collisionForce = new Vector3d();
        for (RKMObject collidingObject : thisObject.getCollidingObjects()) {
            if (collidingObject.getCollider() instanceof SphereCollider otherSphereCollider) {
                Vector3d thisPosition = thisObject.getCurrentRKMPosition();
                Vector3d otherPosition = collidingObject.getCurrentRKMPosition();
                Vector3d normalizedDirection = VectorUtils.getNormalizedDirection(otherPosition, thisPosition);
                double distance = VectorUtils.distance(thisPosition, otherPosition);
                double maxDepth = Application.ENVIRONMENT_SETTINGS.maxDepth;
                double thisCriticalRadius = ((SphereCollider) thisObject.getCollider()).radius - maxDepth;
                double otherCriticalRadius = otherSphereCollider.radius - maxDepth;
                double criticalDistance = thisCriticalRadius + otherCriticalRadius;
                double subCriticalDistance = criticalDistance + (maxDepth*0.0625)*2;
                double depth = Math.max(Math.abs(distance - criticalDistance), Math.abs(subCriticalDistance - criticalDistance));
                Vector3d collisionForceCache = normalizedDirection.mul(1 / (Math.pow(depth, 3)));
                collisionForce.add(collisionForceCache);
            }
        }
        return collisionForce;
    }

    public static Vector3d getHookeCollisionForce(RKMObject thisObject){
        Vector3d collisionForce = new Vector3d();
        for (RKMObject collidingObject : thisObject.getCollidingObjects()) {
            if (collidingObject.getCollider() instanceof SphereCollider otherSphereCollider) {
                Vector3d thisPosition = thisObject.getCurrentRKMPosition();
                Vector3d otherPosition = collidingObject.getCurrentRKMPosition();
                double distance = VectorUtils.distance(thisPosition, otherPosition);
                SphereCollider thisCollider = (SphereCollider) thisObject.getCollider();
                double maxDistance = thisCollider.radius + otherSphereCollider.radius;
                double thisCriticalRadius = thisCollider.radius - MAX_DEPTH;
                double otherCriticalRadius = otherSphereCollider.radius - MAX_DEPTH;
                double minCriticalRadiiDistance = thisCriticalRadius + otherCriticalRadius;
                double stableDistance = maxDistance - minCriticalRadiiDistance;
                double criticalRadiiDistance = distance - (thisCriticalRadius + otherCriticalRadius);
                double depth = stableDistance - criticalRadiiDistance;
                Vector3d normalizedDirection = VectorUtils.getNormalizedDirection(otherPosition, thisPosition);
                Vector3d collisionForceCache = normalizedDirection.mul(Math.abs(depth) * thisCollider.stiffness);
                collisionForce.add(collisionForceCache);
            }
        }
        return collisionForce;
    }
}
