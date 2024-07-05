package me.udnekjupiter.graphic;

import me.udnekjupiter.util.PositionedObject;
import me.udnekjupiter.util.Utils;
import me.udnekjupiter.util.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class Camera extends PositionedObject {

    private float yaw;
    private float pitch;
    private double fov = 1f;

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

    public Vector3d getDirection(){
        Vector3d direction = new Vector3d(0, 0, 1);
        rotateVector(direction);
        return direction;
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

    public void rotateVector(Vector3d vector3d){
        VectorUtils.rotatePitch(vector3d, Math.toRadians(pitch));
        VectorUtils.rotateYaw(vector3d, Math.toRadians(yaw));

    }
    public void rotateBackVector(Vector3d vector3d){
        VectorUtils.rotateYaw(vector3d, Math.toRadians(Utils.normalizeYaw(-yaw)));
        VectorUtils.rotatePitch(vector3d, Math.toRadians(Utils.normalizePitch(-pitch)));

    }

    public void rotateVectorYaw(Vector3d vector3d){VectorUtils.rotateYaw(vector3d, Math.toRadians(yaw));}
    public void rotateVectorPitch(Vector3d vector3d){VectorUtils.rotatePitch(vector3d, Math.toRadians(pitch));}

    public double getFov() {
        return fov;
    }
}
