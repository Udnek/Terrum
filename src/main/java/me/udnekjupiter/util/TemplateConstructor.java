package me.udnekjupiter.util;

import me.udnekjupiter.physic.object.SphereObject;

public enum TemplateConstructor {
    WOODEN_SPHERE(){
        @Override
        SphereObject getObject() {
            SphereObject sphere = new SphereObject(0.5, 50_000);
            return sphere;
        }
    };

    abstract Object getObject();
}
