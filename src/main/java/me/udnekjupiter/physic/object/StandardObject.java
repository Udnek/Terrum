package me.udnekjupiter.physic.object;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.Collider;
import me.udnekjupiter.physic.collision.CollisionCalculator;
import me.udnekjupiter.util.Freezable;
import me.udnekjupiter.util.Resettable;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public abstract class StandardObject extends PhysicObject implements Freezable, Collidable, Resettable {
    protected EnvironmentSettings settings;
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

    public StandardObject(Vector3d position) {
        super(position);
        settings = Application.ENVIRONMENT_SETTINGS;
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

    protected abstract Vector3d calculateAcceleration();
    protected abstract Vector3d getAppliedForce(Vector3d position);
    protected Vector3d getCollisionForce() {
        return CollisionCalculator.getHookeCollisionForce(this);
    }

    @Override
    public void freeze(){
        frozen = true;
        setVelocity(new Vector3d());
    }
    @Override
    public void unfreeze(){frozen = false;}
    @Override
    public boolean isFrozen() {return frozen;}
}
