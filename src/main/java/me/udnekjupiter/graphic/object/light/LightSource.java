package me.udnekjupiter.graphic.object.light;

import me.udnekjupiter.graphic.object.GraphicObject;
import me.udnekjupiter.util.Triangle;
import org.realityforge.vecmath.Vector3d;

public abstract class LightSource extends GraphicObject {
    public LightSource(Vector3d position) {
        super(position);
    }

    @Override
    public Triangle[] getRenderTriangles() {
        return new Triangle[]{new Triangle(new Vector3d(0,0.1,0), new Vector3d(0.2, 0.1, 0.2), new Vector3d(-0.2, 0.1, 0.2))};
    }
}
