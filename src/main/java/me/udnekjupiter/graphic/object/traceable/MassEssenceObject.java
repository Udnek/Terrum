package me.udnekjupiter.graphic.object.traceable;

import me.udnekjupiter.graphic.object.Draggable;
import me.udnekjupiter.graphic.object.PhysicLinked;
import me.udnekjupiter.graphic.object.traceable.shape.IcosphereObject;
import me.udnekjupiter.graphic.triangle.MassEssenceTriangle;
import me.udnekjupiter.graphic.triangle.TraceableTriangle;
import me.udnekjupiter.physic.object.MassEssence;
import me.udnekjupiter.physic.object.PhysicObject;
import org.realityforge.vecmath.Vector3d;

public class MassEssenceObject extends IcosphereObject implements PhysicLinked, Draggable {

    private final MassEssence massEssence;

    public MassEssenceObject(MassEssence massEssence) {
        super(massEssence.getPosition(), massEssence.getCollider().radius, 1,
                new MassEssenceTriangle(new Vector3d(), new Vector3d(), new Vector3d()));
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

    @Override
    public void select() {
        for (TraceableTriangle polygon : polygons) {
            MassEssenceTriangle triangle = (MassEssenceTriangle) polygon;
            triangle.setColor(MassEssenceTriangle.HIGHLIGHTED_COLOR);
        }
    }

    @Override
    public void unselect() {
        for (TraceableTriangle polygon : polygons) {
            MassEssenceTriangle triangle = (MassEssenceTriangle) polygon;
            triangle.setColor(MassEssenceTriangle.DEFAULT_COLOR);
        }
    }
}
