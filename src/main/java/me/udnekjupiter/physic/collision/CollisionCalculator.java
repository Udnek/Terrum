package me.udnekjupiter.physic.collision;

import me.udnekjupiter.Main;
import me.udnekjupiter.util.VectorUtils;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.Vector3d;

@SuppressWarnings("ExtractMethodRecommender")
public abstract class CollisionCalculator {
    @Deprecated
    public static @NotNull Vector3d getAtomicCollisionForce(@NotNull Collidable thisObject){
        Vector3d collisionForce = new Vector3d();
//        for (Collidable collidingObject : thisObject.getCollidingObjects()) {
//            if (!(collidingObject.getCollider() instanceof SphereCollider otherSphereCollider)) continue;
//
//            Vector3d thisPosition = thisObject.getPosition();
//            Vector3d otherPosition = collidingObject.getPosition();
//            Vector3d normalizedDirection = VectorUtils.getNormalizedDirection(otherPosition, thisPosition);
//            double distance = VectorUtils.distance(thisPosition, otherPosition);
//            double thisCriticalRadius = ((SphereCollider) thisObject.getCollider()).radius - ((SphereCollider) thisObject.getCollider()).maxDepth;
//            double otherCriticalRadius = otherSphereCollider.radius - otherSphereCollider.maxDepth;
//            double criticalDistance = thisCriticalRadius + otherCriticalRadius;
//            double subCriticalDistance = criticalDistance + (maxDepth*0.0625)*2;
//            double depth = Math.max(Math.abs(distance - criticalDistance), Math.abs(subCriticalDistance - criticalDistance));
//            Vector3d collisionForceCache = normalizedDirection.mul(1 / (Math.pow(depth, 3)));
//            collisionForce.add(collisionForceCache);
//
//        }
        return collisionForce;
    }

    public static @NotNull Vector3d getHookeCollisionForce(@NotNull Collider thisCollider){
        /*
        F = Σ(k * |Dm - D|), where:
        [F] (N) is the force, applied to the collision initiator (aka thisCollider)
        [k] (N/m) is the hooke's coefficient, or object's stiffness to some extent
        [Dm] (m) is the maximum distance between interacting objects, below which collision is considered happening
            \_-- Dm = R1 + R2 where R is the radius of the collider
        [D] (m) is the distance between interacting colliders
        */

        Vector3d collisionForce = new Vector3d();
        if (thisCollider instanceof SphereCollider thisSphereCollider){
            for (Collider otherCollider : thisCollider.getCurrentCollisions()) {
                if (otherCollider instanceof SphereCollider otherSphereCollider) {
                    /*
                    Vector3d thisPosition = thisCollider.parent.getPosition();
                    Vector3d otherPosition = otherSphereCollider.parent.getPosition();
                    double distance = VectorUtils.distance(thisPosition, otherPosition);
                    double maxDistance = thisSphereCollider.radius + otherSphereCollider.radius;
                    double depth = maxDistance - distance;
                    Vector3d normalizedDirection = VectorUtils.getNormalizedDirection(otherPosition, thisPosition);
                    Vector3d collisionForceCache = normalizedDirection.mul(Math.abs(depth) * thisSphereCollider.stiffness);
                    collisionForce.add(collisionForceCache);
                    */
                    collisionForce.add(VectorUtils.getNormalizedDirection(otherSphereCollider.parent.getPosition(), thisCollider.parent.getPosition()).mul(Math.abs(thisSphereCollider.radius + otherSphereCollider.radius - VectorUtils.distance(thisCollider.parent.getPosition(), otherSphereCollider.parent.getPosition())) * thisSphereCollider.stiffness));
                } else if (otherCollider instanceof PlaneCollider planeCollider) {
                    double stableDistance = thisSphereCollider.radius;
                    double distance = VectorUtils.distanceFromPointToPlane(planeCollider.a, planeCollider.b, planeCollider.c, planeCollider.d, thisSphereCollider.getCenterPosition().x, thisSphereCollider.getCenterPosition().y, thisSphereCollider.getCenterPosition().z);
                    double depth = stableDistance - distance;
                    Vector3d collisionForceCache = new Vector3d(planeCollider.a, planeCollider.b, planeCollider.c).normalize().mul(Math.abs(depth) * thisSphereCollider.stiffness);
                    collisionForce.add(collisionForceCache);
                }
            }
        }
        return collisionForce;
    }
}
