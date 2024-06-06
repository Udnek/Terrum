package me.jupiter.net;

import me.jupiter.object.NetDynamicVertex;
import me.jupiter.object.NetStaticVertex;
import me.jupiter.object.NetVertex;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public enum VertexColor {

    DYNAMIC(new Color(0f, 1f, 0f), NetDynamicVertex.class) {
        @Override
        NetVertex getNewVertex() {
            return new NetDynamicVertex(new Vector3d());
        }
    },
    STATIC(new Color(0f, 0f, 1f), NetStaticVertex.class) {
        @Override
        NetVertex getNewVertex() {
            return new NetStaticVertex(new Vector3d());
        }
    },
    UNKNOWN(new Color(1f, 0f, 1f), null){
        @Override
        NetVertex getNewVertex() {return null;}
    };

    public final Color color;
    public final Class<? extends NetVertex> clazz;
    VertexColor(Color color, Class<? extends NetVertex> clazz){
        this.color = color;
        this.clazz = clazz;
    }
    abstract NetVertex getNewVertex();

    public static NetVertex getVertex(Color color){
        for (VertexColor vertex : VertexColor.values()) {
            if (color.getRGB() == vertex.color.getRGB()){
                return vertex.getNewVertex();
            }
        }
        return VertexColor.UNKNOWN.getNewVertex();
    }

    public static Color getColorFromVertex(NetVertex netVertex){
        for (VertexColor vertex : VertexColor.values()) {
            Class<? extends NetVertex> clazz = netVertex.getClass();
            if (vertex.clazz == clazz){
                return vertex.color;
            }
        }
        return UNKNOWN.color;
    }
}
