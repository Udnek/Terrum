package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.EnvironmentSettings;
import me.udnekjupiter.physic.collision.Collidable;
import me.udnekjupiter.physic.collision.Collider;
import me.udnekjupiter.physic.collision.CollisionCalculator;
import me.udnekjupiter.physic.object.container.PhysicCalculationContainer;
import me.udnekjupiter.util.Freezable;
import me.udnekjupiter.util.PositionedObject;
import me.udnekjupiter.util.Resettable;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class PhysicObject extends PositionedObject implements Resettable, Collidable, Freezable {
    protected PhysicCalculationContainer container;
    protected EnvironmentSettings settings;
    protected List<Collidable> collidingObjects;
    protected final Vector3d initialPosition;
    protected Vector3d velocity;
    protected Vector3d acceleration;
    protected Vector3d positionDifferential;
    protected Vector3d velocityDifferential;
    protected Collider collider;
    protected boolean frozen;
    protected double deltaTime;
    protected double decayCoefficient;
    protected double mass;

    public PhysicObject(Vector3d position) {
        super(position);
        this.initialPosition = position.dup();
    }

    public Vector3d getInitialPosition() {
        return initialPosition.dup();
    }

    public Collider getCollider(){
        return collider;
    }
    public void addCollidingObject(RKMObject object){
        collidingObjects.add(object);
    }
    public List<Collidable> getCollidingObjects(){return collidingObjects;}
    public void clearCollidingObjects(){
        collidingObjects = new ArrayList<>();
    }
    public boolean collidingObjectIsAlreadyListed(RKMObject object){
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

    @Override
    public void freeze(){
        frozen = true;
        setVelocity(new Vector3d());
    }
    @Override
    public void unfreeze(){frozen = false;}
    @Override
    public boolean isFrozen() {return frozen;}

    protected abstract Vector3d calculateAcceleration(Vector3d position, Vector3d velocity);
    protected abstract Vector3d getAppliedForce(Vector3d position);
    protected Vector3d getCollisionForce() {
        return CollisionCalculator.getHookeCollisionForce(this);
    }

}
