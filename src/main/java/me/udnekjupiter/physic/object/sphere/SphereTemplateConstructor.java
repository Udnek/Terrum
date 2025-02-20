package me.udnekjupiter.physic.object.sphere;

import me.udnekjupiter.physic.engine.ConstantValues;
import me.udnekjupiter.util.Utils;
import me.udnekjupiter.util.vector.Vector3d;

public enum SphereTemplateConstructor {
    WOODEN_SPHERE(){
        @Override
        SphereObject getObject(Vector3d position, double radius) {
            SphereObject sphere = new SphereObject(radius, 50_000);
            sphere.getContainer().mass = Utils.getSphereWeight(ConstantValues.WOOD_DENSITY, radius);
            sphere.getContainer().getPosition().set(position);
            return sphere;
        }
    };

    abstract Object getObject(Vector3d position, double radius);
}
