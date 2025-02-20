
package me.udnekjupiter.physic.net;

import me.udnekjupiter.physic.object.vertex.NetDebugVertex;
import me.udnekjupiter.physic.object.vertex.NetDynamicVertex;
import me.udnekjupiter.physic.object.vertex.NetStaticVertex;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public enum VertexColor {

    DYNAMIC(new Color(0f, 1f, 0f), NetDynamicVertex.class) {
        @Override
        NetVertex getNewVertex() {
            return new NetDynamicVertex();
        }
    },
    STATIC(new Color(0f, 0f, 1f), NetStaticVertex.class) {
        @Override
        NetVertex getNewVertex() {
            return new NetStaticVertex();
        }
    },
    DEBUG(new Color(1f, 0f, 0f), NetDebugVertex.class){
        @Override
        NetVertex getNewVertex() {
            return new NetDebugVertex();
        }
    },
    UNKNOWN(new Color(1f, 0f, 1f), null){
        @Override
        NetVertex getNewVertex() {return null;}
    };

    public final Color color;
    public final Class<? extends NetVertex> clazz;
    VertexColor(@NotNull Color color, Class<? extends NetVertex> clazz){
        this.color = color;
        this.clazz = clazz;
    }
    abstract @Nullable NetVertex getNewVertex();

    public static @Nullable NetVertex getVertex(@NotNull Color color){
        for (VertexColor vertex : VertexColor.values()) {
            if (color.getRGB() == vertex.color.getRGB()){
                return vertex.getNewVertex();
            }
        }
        return VertexColor.UNKNOWN.getNewVertex();
    }

    public static @NotNull Color getColorFromVertex(@NotNull NetVertex netVertex){
        for (VertexColor vertex : VertexColor.values()) {
            Class<? extends NetVertex> clazz = netVertex.getClass();
            if (vertex.clazz == clazz){
                return vertex.color;
            }
        }
        return UNKNOWN.color;
    }
}
