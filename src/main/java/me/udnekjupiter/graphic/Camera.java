package me.udnekjupiter.graphic;

import me.udnekjupiter.util.*;
import me.udnekjupiter.util.vector.Vector3d;
import me.udnekjupiter.util.vector.VectorUtils;
import org.jetbrains.annotations.NotNull;

public class Camera extends PositionedObject {

    private float yaw;
    private float pitch;
    private double fov = 2f;

    public Camera(@NotNull Vector3d position, float yaw, float pitch) {
        super(position);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Camera(@NotNull Vector3d position) {
        this(position, 0, 0);
    }
    public Camera(){
        this(new Vector3d(), 0, 0);
    }

    public void moveAlongDirection(Vector3d position){
        rotateVector(position);
        move(position);
    }

    public Vector3d getDirection(){
        return rotateVector(new Vector3d(0, 0, -1));
    }
    public void moveAlongDirectionParallelXZ(Vector3d position){
        VectorUtils.rotateYaw(position, (float) Math.toRadians(yaw));
        move(position);
    }

    public float getPitch() {return pitch;}
    public void setPitch(float angle) {pitch = Utils.normalizePitch(angle);}
    public void rotatePitch(float angle) {setPitch(pitch + angle);}

    public float getYaw() {return yaw;}
    public void setYaw(float angle) {yaw = Utils.normalizeYaw(angle);}
    public void rotateYaw(float angle) {setYaw(yaw + angle);}

    public Vector3d rotateVector(Vector3d vector){
        VectorUtils.rotatePitch(vector, Math.toRadians(pitch));
        VectorUtils.rotateYaw(vector, Math.toRadians(yaw));
        return vector;
    }
    public Vector3d rotateBackVector(Vector3d vector){
        VectorUtils.rotateYaw(vector, Math.toRadians(-yaw));
        VectorUtils.rotatePitch(vector, Math.toRadians(-pitch));
        return vector;
    }

    public Triangle rotateBackTriangle(Triangle triangle){
        rotateBackVector(triangle.getUnsafeVertex0());
        rotateBackVector(triangle.getUnsafeVertex1());
        rotateBackVector(triangle.getUnsafeVertex2());
        return triangle;
    }

    public void rotateVectorYaw(Vector3d vector3d){VectorUtils.rotateYaw(vector3d, Math.toRadians(yaw));}
    public void rotateVectorPitch(Vector3d vector3d){VectorUtils.rotatePitch(vector3d, Math.toRadians(pitch));}

    public double getFov() {
        return fov;
    }
    public double getFovAngleRadians(){
        return Math.atan(fov/2)*2;
    }
    public void setFov(double fov){this.fov = fov;}
}
