package me.udnekjupiter.graphic.engine.opengl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.nio.FloatBuffer;

public class Texture {

    public static final int ATLAS_SIZE = 128;

    public static final Texture SPRING = new Texture(0, 31, 114, 0);
    public static final Texture BLANK = new Texture(0, 32, 1, 33);
    public static final Texture VERTEX = new Texture(16, 32, 32, 48);

    public final float x0;
    public final float y0;
    public final float x1;
    public final float y1;

    public void getCornerPosition(@Range(from = 0, to=3) int index, @NotNull FloatBuffer target){
        switch (index){
            case 0 -> target.put(x0).put(y0);
            case 1 -> target.put(x0).put(y1);
            case 2 -> target.put(x1).put(y1);
            case 3 -> target.put(x1).put(y0);
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }

    public Texture(int x0, int y0, int x1, int y1){
        this.x0 = (float) x0 / ATLAS_SIZE;
        this.y0 = (float) y0 / ATLAS_SIZE;
        this.x1 = (float) x1 / ATLAS_SIZE;
        this.y1 = (float) y1 / ATLAS_SIZE;
    }

}
