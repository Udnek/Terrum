package me.udnek.scene;

import me.udnek.utils.PositionedObject;
import me.udnek.utils.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class Camera extends PositionedObject {

    private Vector3d direction;
    private float pitch = 0f;
    private float yaw = 0f;

    public Camera(Vector3d position) {
        super(position);
    }
    public Camera(){
        super(new Vector3d());
    }

    public Vector3d getDirection() {
        return new Vector3d(direction);
    }
    public void setDirection(Vector3d direction) {
        this.direction = direction;
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
        angle = angle % 360;
        if (angle < 0) angle = 360 - angle;
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
