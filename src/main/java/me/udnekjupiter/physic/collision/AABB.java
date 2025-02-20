package me.udnekjupiter.physic.collision;

import me.udnekjupiter.physic.collision.shape.SphereCollider;
import me.udnekjupiter.util.Vector3d;
import org.jetbrains.annotations.NotNull;

public final class AABB {
    private Vector3d centerPosition;
    private Vector3d minCorner;
    private Vector3d maxCorner;
    private Vector3d size;

    public AABB(Vector3d center, Vector3d size){
        this.centerPosition = center;
        this.size = size;
        updateCorners();
    }

    @NotNull
    public Vector3d getMinCorner(){
        return minCorner;
    }
    @NotNull
    public Vector3d getMaxCorner(){
        return maxCorner;
    }
    @NotNull
    public Vector3d getSize(){
        return size;
    }

    @NotNull
    public Vector3d getPosition(){
        return centerPosition;
    }
    public void setPosition(@NotNull Vector3d newPosition){
        this.centerPosition = newPosition;
        updateCorners();
    }
    public void setSize(Vector3d newSize){
        this.size = newSize;
        updateCorners();
    }

    public void updateCorners(){
        this.minCorner = new Vector3d(
                centerPosition.x - (size.x/2),
                centerPosition.y - (size.y/2),
                centerPosition.z - (size.z/2)
        );
        this.maxCorner = new Vector3d(
                centerPosition.x + (size.x/2),
                centerPosition.y + (size.y/2),
                centerPosition.z + (size.z/2)
        );
    }

    public boolean collidesWith(AABB otherAABB){
        return ((maxCorner.x >= otherAABB.minCorner.x) &
                (maxCorner.y >= otherAABB.minCorner.y) &
                (maxCorner.z >= otherAABB.minCorner.z) &
                (minCorner.x <= otherAABB.maxCorner.x) &
                (minCorner.y <= otherAABB.maxCorner.y) &
                (minCorner.z <= otherAABB.maxCorner.z));

    }

    public boolean collidesWith(SphereCollider sphereCollider) {
        Vector3d normalizedDirection = sphereCollider.getCenterPosition().getNormalizedDirection(centerPosition);
        Vector3d borderPointRelativePosition = normalizedDirection.mul(sphereCollider.radius);
        return containsPoint(sphereCollider.getCenterPosition().add(borderPointRelativePosition));
    }
    public boolean containsPoint(Vector3d point){
        return ((minCorner.x <= point.x & minCorner.y <= point.y & minCorner.z <= point.z)
                & (maxCorner.x >= point.x & maxCorner.y >= point.y & maxCorner.z >= point.z));
    }

}
