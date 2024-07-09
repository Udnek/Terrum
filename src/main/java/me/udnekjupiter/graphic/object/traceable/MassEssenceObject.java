package me.udnekjupiter.graphic.object.traceable;

import me.udnekjupiter.graphic.object.Draggable;
import me.udnekjupiter.graphic.object.PhysicLinked;
import me.udnekjupiter.graphic.object.traceable.shape.IcosphereObject;
import me.udnekjupiter.graphic.triangle.MassEssenceTriangle;
import me.udnekjupiter.graphic.triangle.TraceableTriangle;
import me.udnekjupiter.physic.object.SphereObject;
import me.udnekjupiter.physic.object.PhysicObject;
import org.realityforge.vecmath.Vector3d;

public class MassEssenceObject extends IcosphereObject implements PhysicLinked, Draggable {

    private final SphereObject sphereObject;

    public MassEssenceObject(SphereObject sphereObject) {
        super(sphereObject.getPosition(), sphereObject.getCollider().radius, 1,
                new MassEssenceTriangle(new Vector3d(), new Vector3d(), new Vector3d()));
        this.sphereObject = sphereObject;
    }

    @Override
    public void synchronizeWithPhysic() {
        setPosition(sphereObject.getPosition());
    }

    @Override
    public void setPosition(Vector3d position) {
        super.setPosition(position);
        sphereObject.setPosition(position);
    }

    @Override
    public PhysicObject getPhysicRepresentation() {
        return sphereObject;
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
