package me.udnek.scene;

import me.udnek.util.PositionedObject;
import me.udnek.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class Camera extends PositionedObject {

    private float yaw;
    private float pitch;
    private double fov = 2f;

    public Camera(Vector3d position, float yaw, float pitch) {
        super(position);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Camera(Vector3d position) {
        this(position, 0, 0);
    }
    public Camera(){
        this(new Vector3d(), 0, 0);
    }

    public void moveAlongDirection(Vector3d position){
        rotateVector(position);
        move(position);
    }
    public void moveAlongDirectionParallelXZ(Vector3d position){
        VectorUtils.rotateYaw(position, (float) Math.toRadians(yaw));
        move(position);
    }

    public float getPitch() {return pitch;}
    public void setPitch(float angle) {
        if (angle > 90) {pitch = 90; return;}
        if (angle < -90) {pitch = -90; return;}
        pitch = angle;
    }
    public void rotatePitch(float angle) {setPitch(pitch + angle);}

    public float getYaw() {return yaw;}
    public void setYaw(float angle) {
        angle = angle % 360f;
        if (angle < 0) angle = 360f + angle;
        yaw = angle;
    }
    public void rotateYaw(float angle) {setYaw(yaw + angle);}

    public void rotateVector(Vector3d vector3d){
        VectorUtils.rotatePitch(vector3d, Math.toRadians(pitch));
        VectorUtils.rotateYaw(vector3d, Math.toRadians(yaw));
    }
    public void rotateVectorYaw(Vector3d vector3d){VectorUtils.rotateYaw(vector3d, Math.toRadians(yaw));}
    public void rotateVectorPitch(Vector3d vector3d){VectorUtils.rotatePitch(vector3d, Math.toRadians(pitch));}

    public double getFov() {
        return fov;
    }
}
