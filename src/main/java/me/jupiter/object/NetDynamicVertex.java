package me.jupiter.object;

import me.udnek.utils.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class NetDynamicVertex extends NetVertex{
    private Vector3d velocity;
    private Vector3d acceleration;
    private Vector3d positionDifferential;
    private double springStiffness;
    private double springRelaxedLength;
    private double mass;
    private double deltaTime;


    public NetDynamicVertex(Vector3d position) {
        super(position);
        this.velocity = new Vector3d(0, 0, 0);
        this.acceleration = new Vector3d(0, 0, 0);
        this.springStiffness = 1;
        this.springRelaxedLength = 1;
        this.mass = 1;
        this.deltaTime = 0.1;
    }

    public void setVariables(double springStiffness,
                             double springRelaxedLength,
                             double mass,
                             double deltaTime){
        this.springStiffness = springStiffness;
        this.springRelaxedLength = springRelaxedLength;
        this.mass = mass;
        this.deltaTime = deltaTime;

    }

    public Vector3d getVelocity() {return velocity.dup();}
    public void setVelocity(Vector3d velocity) {this.velocity = velocity;}

    public Vector3d getAcceleration() {return acceleration.dup();}
    public void setAcceleration(Vector3d acceleration) {this.acceleration = acceleration;}

    public Vector3d getPositionDifferential() {return positionDifferential.dup();}
    public void setPositionDifferential(Vector3d positionDifferential) {this.positionDifferential = positionDifferential;}

    public Vector3d getNormalizedDirection(Vector3d positionEnd, Vector3d positionStart){
        return positionEnd.sub(positionStart).normalize();
    }
    public void calculatePositionDifferential() {
        Vector3d appliedForce = new Vector3d(0, 0, 0);
        for (NetVertex neighbour : neighbours) {
            if (neighbour != null) {
                Vector3d normalizedDirection = getNormalizedDirection(neighbour.getPosition(), this.getPosition());
                double distanceToNeighbour = VectorUtils.distance(this.getPosition(), neighbour.getPosition());
                double sizeDifferential = Math.abs(distanceToNeighbour - springRelaxedLength);
                double elasticForce = springStiffness * sizeDifferential;
                appliedForce.add(normalizedDirection.mul(elasticForce));
            }
        }
        acceleration = appliedForce.dup().div(mass);
        velocity.add(acceleration.dup().mul(deltaTime));
        positionDifferential = velocity.dup().mul(deltaTime);
    }

    public void updatePosition(){
        position.add(positionDifferential);
    }
}
