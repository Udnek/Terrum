package me.udnekjupiter.graphic.triangle;

import me.udnekjupiter.graphic.engine.opengl.Texture;
import me.udnekjupiter.graphic.engine.opengl.TextureCorners;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.NotNull;
import me.udnekjupiter.util.vector.Vector3d;

import java.awt.*;

public class ColoredTriangle extends RenderableTriangle {
    public int color;
    public @NotNull Texture texture;
    public @NotNull TextureCorners textureCorners;

    public ColoredTriangle(@NotNull Vector3d vertex0, @NotNull Vector3d vertex1, @NotNull Vector3d vertex2, int color, @NotNull Texture texture, @NotNull TextureCorners textureCorners) {
        super(vertex0, vertex1, vertex2);
        this.color = color;
        this.texture = texture;
        this.textureCorners = textureCorners;
    }

    public ColoredTriangle(@NotNull Vector3d vertex0, @NotNull Vector3d vertex1, @NotNull Vector3d vertex2, int color) {
        this(vertex0, vertex1, vertex2, color, Texture.BLANK, TextureCorners.FIRST_HALF);
    }

    public ColoredTriangle(@NotNull ColoredTriangle triangle) {
        super(triangle);
        this.color = triangle.color;
        this.texture = triangle.texture;
        this.textureCorners = triangle.textureCorners;
    }

    @Override
    public @NotNull TextureCorners getTextureCorners() {return textureCorners;}
    @Override
    public @NotNull Texture getTexture() {return texture;}

    @Override
    public int getTraceColor(@NotNull Vector3d hitPosition) {return color;}
    @Override
    public int getRasterizeColor() {return color;}

    @Override
    public @NotNull ColoredTriangle copy() {
        return new ColoredTriangle(this);
    }
}
