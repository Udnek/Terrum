package me.udnekjupiter.graphic.engine.opengl;

import org.jcodec.common.Preconditions;

public class TextureCorners {

    public static final TextureCorners FIRST_HALF = new TextureCorners(0, 1, 2);
    public static final TextureCorners FIRST_HALF_SWAPPED_01 = new TextureCorners(1, 0, 2);
    public static final TextureCorners SECOND_HALF = new TextureCorners(2, 3, 0);
    public static final TextureCorners SECOND_HALF_SWAPPED_01 = new TextureCorners(3, 2, 0);

    public final int id0;
    public final int id1;
    public final int id2;

    public TextureCorners(int id0, int id1, int id2){
        Preconditions.checkArgument(0 <= id0 && id0 < 4, "id0 must be in [0; 3]");
        Preconditions.checkArgument(0 <= id1 && id1 < 4, "id1 must be in [0; 3]");
        Preconditions.checkArgument(0 <= id2 && id2 < 4, "id2 must be in [0; 3]");
        this.id0 = id0;
        this.id1 = id1;
        this.id2 = id2;
    }
}
