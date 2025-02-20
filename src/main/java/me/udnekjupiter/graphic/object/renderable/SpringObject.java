package me.udnekjupiter.graphic.object.renderable;

import me.udnekjupiter.graphic.engine.opengl.Texture;
import me.udnekjupiter.graphic.engine.opengl.TextureCorners;
import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.triangle.ColoredTriangle;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Consumer;

public class SpringObject extends GraphicObject3d implements Tickable {

    protected Positioned tip0;
    protected Positioned tip1;
    protected ColoredTriangle plane0;
    protected ColoredTriangle plane1;
    protected me.udnekjupiter.physic.object.SpringObject physicalSpring;

    protected final int color = Color.WHITE.getRGB();

    public SpringObject(@NotNull me.udnekjupiter.physic.object.SpringObject springObject) {
        super(new Vector3d());
        this.physicalSpring = springObject;
        this.tip0 = springObject.getEndpointA();
        this.tip1 = springObject.getEndpointB();
        this.plane0 = new ColoredTriangle(new Vector3d(), new Vector3d(), new Vector3d(), color, Texture.SPRING, TextureCorners.FIRST_HALF_SWAPPED_01);
        this.plane1 = new ColoredTriangle(new Vector3d(), new Vector3d(), new Vector3d(), color, Texture.SPRING, TextureCorners.SECOND_HALF);
    }

    @Override
    public void tick() {
        Vector3d posA1 = tip0.getPosition().sub(0, 0.01, 0);
        Vector3d posB1 = tip1.getPosition().sub(0, 0.01, 0);

        Vector3d direction = posB1.dup().sub(posA1).normalize().mul(0.05f);
        VectorUtils.rotateYaw(direction, Math.PI/2.0);
        direction.y = 0;

        posA1.sub(direction);
        posB1.sub(direction);

        direction.mul(2);

        Vector3d posA2 = posA1.dup().add(direction);
        Vector3d posB2 = posB1.dup().add(direction);

        plane0.setVertices(posA1, posA2, posB1);
        plane1.setVertices(posB1, posB2, posA2);

        //(x-x0)/(x1-x0) = (y-y0)/(y1-y0)
        //(y1-y0)*(x-x0)/(x1-x0) = (y-y0)
        //(y1-y0)*(x-x0)/(x1-x0) + y0 = y
        //(0-1)*(x-r)/(x1-r) + 1 = y
        //-(x-r)/(x1-r) + 1 = y

        double relaxedLength = physicalSpring.getRelaxedLength();
        double maxDistance = 2 * relaxedLength;
        double distance = tip0.getPosition().distance(tip1.getPosition());
        int intense = (int) (Math.clamp(
                        -(distance- relaxedLength)/(maxDistance- relaxedLength)+1,
                        0,
                        1) * 255);
        int newColor = Utils.color(255, intense, intense, 255);
        plane0.color = newColor;
        plane1.color = newColor;

    }


    @Override
    public RenderableTriangle @NotNull [] getRenderTriangles() {
        return new RenderableTriangle[]{plane0.copy(), plane1.copy()};
    }

    @Override
    public RenderableTriangle @NotNull [] getUnsafeRenderTriangles() {
        return new RenderableTriangle[]{plane0, plane1};
    }

    @Override
    public void getUnsafeRenderTriangles(@NotNull Consumer<RenderableTriangle> consumer) {
        consumer.accept(plane0);
        consumer.accept(plane1);
    }
}
