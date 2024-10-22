package me.udnekjupiter.physic.object;

import me.udnekjupiter.physic.object.container.VariableContainer;
import me.udnekjupiter.util.Positioned;
import me.udnekjupiter.util.Resettable;
import org.jetbrains.annotations.NotNull;

public interface PhysicObject<ContainerType extends VariableContainer> extends Resettable, Positioned {
    @NotNull ContainerType getContainer();
    default @NotNull <T extends ContainerType> T getContainer(Class<T> tClass){return (T) getContainer();}
    void setContainer(@NotNull ContainerType container);
}