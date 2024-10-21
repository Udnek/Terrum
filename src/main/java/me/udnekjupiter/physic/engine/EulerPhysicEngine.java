package me.udnekjupiter.physic.engine;

import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.Collider;
import me.udnekjupiter.physic.collision.CollisionCalculator;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class EulerPhysicEngine extends PhysicEngine3d{
    protected List<Collidable> collidingObjects = new ArrayList<>();
    protected Vector3d velocity;
    protected Vector3d acceleration;
    protected Vector3d positionDifferential = new Vector3d();
    protected Vector3d velocityDifferential = new Vector3d();
    protected Collider collider;
    protected boolean frozen;
    protected double deltaTime;
    protected double decayCoefficient;
    protected double mass;

    public StandardObject3d(Vector3d position) {
        super(position);
        this.velocity = new Vector3d(0,0,0);
        this.acceleration = new Vector3d(0,0,0);
    }

    public Collider getCollider(){
        return collider;
    }
    public void addCollidingObject(Collidable object){
        collidingObjects.add(object);
    }
    public List<Collidable> getCollidingObjects(){return collidingObjects;}
    public void clearCollidingObjects(){
        collidingObjects.clear();
    }
    public boolean collidingObjectIsAlreadyListed(Collidable object){
        return collidingObjects.contains(object);
    }
    public void reset(){
        setPosition(getInitialPosition());
        setVelocity(new Vector3d());
    }

    public Vector3d getVelocity(){
        return velocity.dup();
    }
    public void setVelocity(Vector3d velocity) {
        this.velocity = velocity.dup();
    }

    public void calculatePhaseDifferential(){
        if (this.isFrozen()){
            velocityDifferential.mul(0);
            positionDifferential.mul(0);
            return;
        }
        velocityDifferential = calculateAcceleration().mul(deltaTime);
        positionDifferential = velocity.dup().mul(deltaTime);
    }

    public void updatePosition() {
        setVelocity(getVelocity().add(velocityDifferential));
        setPosition(getPosition().add(positionDifferential));
    }

    @Override
    protected Vector3d calculateAcceleration(){
        Vector3d appliedForce = getAppliedForce(position.dup());
        Vector3d decayValue = velocity.dup().mul(decayCoefficient);
        Vector3d resultAcceleration = appliedForce.dup().sub(decayValue);
        resultAcceleration.div(mass);
        resultAcceleration.y += PhysicEngine3d.GRAVITATIONAL_ACCELERATION;

        return resultAcceleration;
    }
    protected Vector3d getCollisionForce() {
        return CollisionCalculator.getHookeCollisionForce(this);
    }

    public void freeze(){
        frozen = true;
        setVelocity(new Vector3d());
    }
    public void unfreeze(){frozen = false;}
    public boolean isFrozen() {return frozen;}
    @Override
    public void initialize() {
        super.initialize();
    }
}
