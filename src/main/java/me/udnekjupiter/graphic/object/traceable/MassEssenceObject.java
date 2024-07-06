package me.udnekjupiter.graphic.object.traceable;

import me.udnekjupiter.graphic.object.PhysicLinked;
import me.udnekjupiter.graphic.object.traceable.shape.IcosphereObject;
import me.udnekjupiter.physic.object.MassEssence;
import me.udnekjupiter.physic.object.PhysicObject;
import org.realityforge.vecmath.Vector3d;

public class MassEssenceObject extends IcosphereObject implements PhysicLinked {

    private final MassEssence massEssence;

    public MassEssenceObject(MassEssence massEssence) {
        super(massEssence.getPosition(), 0.5, 1);
        this.massEssence = massEssence;
    }

    @Override
    public void synchronizeWithPhysic() {
        setPosition(massEssence.getPosition());
    }

    @Override
    public void setPosition(Vector3d position) {
        super.setPosition(position);
        massEssence.setPosition(position);
    }

    @Override
    public PhysicObject getPhysicRepresentation() {
        return massEssence;
    }
}
