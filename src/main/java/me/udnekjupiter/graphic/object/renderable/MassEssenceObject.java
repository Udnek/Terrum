package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.object.Draggable;
import me.udnekjupiter.graphic.object.PhysicLinked;
import me.udnekjupiter.graphic.object.renderable.shape.IcosphereObject;
import me.udnekjupiter.graphic.triangle.MassEssenceTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.object.sphere.SphereObject;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.vector.Vector3d;

public class MassEssenceObject extends IcosphereObject implements PhysicLinked, Draggable {

    private final SphereObject sphereObject;

    public MassEssenceObject(SphereObject sphereObject){
        super(sphereObject.getPosition(), sphereObject.getCollider().radius, 1,
                new MassEssenceTriangle(new Vector3d(), new Vector3d(), new Vector3d()));
        this.sphereObject = sphereObject;
    }
    @Override
    public void synchronizeWithPhysic() {
        setPosition(sphereObject.getPosition());
    }
    @Override
    public void setPosition(@NotNull Vector3d position) {
        super.setPosition(position);
        sphereObject.setPosition(position);
    }
    @Override
    public @NotNull PhysicObject3d getPhysicRepresentation() {
        return sphereObject;
    }
    @Override
    public void select() {
        for (RenderableTriangle polygon : polygons) {
            MassEssenceTriangle triangle = (MassEssenceTriangle) polygon;
            triangle.setColor(MassEssenceTriangle.HIGHLIGHTED_COLOR);
        }
    }
    @Override
    public void unselect() {
        for (RenderableTriangle polygon : polygons) {
            MassEssenceTriangle triangle = (MassEssenceTriangle) polygon;
            triangle.setColor(MassEssenceTriangle.DEFAULT_COLOR);
        }
    }
}
