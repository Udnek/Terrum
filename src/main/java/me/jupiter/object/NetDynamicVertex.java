package me.jupiter.object;

import me.udnek.utils.VectorUtils;
import org.realityforge.vecmath.Vector3d;

public class NetDynamicVertex extends NetVertex{
    private Vector3d velocity;
    private Vector3d acceleration;
    private Vector3d positionDifferential;
    private Vector3d appliedForce;
    private double springStiffness;
    private double springRelaxedLength;
    private double mass;


    public NetDynamicVertex(Vector3d position) {
        super(position);
        this.velocity = new Vector3d(0, 0, 0);
        this.springStiffness = 1;
        this.springRelaxedLength = 0.5;
        this.mass = 0.5;
    }

    public Vector3d getVelocity() {return velocity.dup();}
    public void setVelocity(Vector3d velocity) {this.velocity = velocity;}

    public Vector3d getAcceleration() {return acceleration;}
    public void setAcceleration(Vector3d acceleration) {this.acceleration = acceleration;}

    public Vector3d getPositionDifferential() {return positionDifferential.dup();}
    public void setPositionDifferential(Vector3d positionDifferential) {this.positionDifferential = positionDifferential;}

    public Vector3d getNormalizedDirection(Vector3d positionEnd, Vector3d positionStart){
        return positionEnd.sub(positionStart).normalize();
    }
    public void calculatePositionDifferential(double deltaTime) {
        appliedForce = new Vector3d(0, 0, 0);
        for (NetVertex neighbour : neighbours) {
            if (neighbour != null) {
                Vector3d normalizedDirection = getNormalizedDirection(neighbour.getPosition(), this.getPosition());
                //System.out.println(normalizedDirection.asString());
                double distanceToNeighbour = VectorUtils.distance(this.getPosition(), neighbour.getPosition());
                //System.out.println(distanceToNeighbour);
                double sizeDifferential = Math.abs(distanceToNeighbour - springRelaxedLength);
                //System.out.println(sizeDifferential);
                double elasticForce = springStiffness * sizeDifferential;
                appliedForce.add(normalizedDirection.mul(elasticForce));
            }
        }
        acceleration = appliedForce.div(mass);
        velocity.add(acceleration);
        positionDifferential = velocity.mul(deltaTime);
    }

    public void updatePosition(){
        position.add(positionDifferential);
    }
}
