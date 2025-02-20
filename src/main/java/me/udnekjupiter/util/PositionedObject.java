package me.udnekjupiter.util;

import me.udnekjupiter.util.utilityinterface.Positioned;
import me.udnekjupiter.util.vector.Vector3d;
import org.jetbrains.annotations.NotNull;

public abstract class PositionedObject implements Positioned {
    protected @NotNull Vector3d position;

    public PositionedObject(@NotNull Vector3d position){
        this.position = position;
    }

    public @NotNull Vector3d getPosition() {
        return position.dup();
    }

    public void setPosition(@NotNull Vector3d position) {
        this.position = position;
    }
    public void move(@NotNull Vector3d addPosition){
        setPosition(addPosition.add(position));
    }
    public void move(double x, double y, double z){
        setPosition(position.add(x, y, z));
    }
}
