package me.udnekjupiter.physic.container;

import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class RKMContainer extends PhysicVariableContainer {
    public final @NotNull Vector3d[] currentPhaseVector = new Vector3d[]{new Vector3d(), new Vector3d()};
    public @NotNull Vector3d[] basePhaseVector = new Vector3d[]{new Vector3d(), new Vector3d()};
    public @NotNull Vector3d[] coefficient1 = new Vector3d[]{new Vector3d(), new Vector3d()};
    public @NotNull Vector3d[] coefficient2 = new Vector3d[]{new Vector3d(), new Vector3d()};
    public @NotNull Vector3d[] coefficient3 = new Vector3d[]{new Vector3d(), new Vector3d()};
    public @NotNull Vector3d[] coefficient4 = new Vector3d[]{new Vector3d(), new Vector3d()};

    public RKMContainer(@NotNull Vector3d position) {
        super(position);
    }
}
