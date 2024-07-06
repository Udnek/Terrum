package me.udnekjupiter.graphic.object.traceable;

import me.udnekjupiter.graphic.object.PhysicSynchronizable;
import me.udnekjupiter.graphic.object.traceable.shape.IcosphereObject;
import me.udnekjupiter.physic.object.MassEssence;

public class MassEssenceObject extends IcosphereObject implements PhysicSynchronizable {

    private final MassEssence massEssence;

    public MassEssenceObject(MassEssence massEssence) {
        super(massEssence.getPosition(), 0.5, 1);
        this.massEssence = massEssence;
    }

    @Override
    public void synchronizeWithPhysic() {
        setPosition(massEssence.getPosition());
    }
}
