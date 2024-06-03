package me.udnek.scene;

import me.udnek.utils.PositionedObject;
import me.udnek.utils.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class Camera extends PositionedObject {

    private float pitch = 0f;
    private float yaw = 0f;

    public Camera(Vector3d position) {
        super(position);
    }
    public Camera(){
        super(new Vector3d());
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


    public void rotate(float pitch, float yaw){
        rotatePitch(pitch);
        rotateYaw(yaw);
    }

    public void rotateVector(Vector3d vector3d){
        VectorUtils.rotatePitch(vector3d, (float) Math.toRadians(pitch));
        VectorUtils.rotateYaw(vector3d, (float) Math.toRadians(yaw));
    }
}
