package me.jupiter.net;

import me.jupiter.object.NetDynamicVertex;
import me.jupiter.object.NetStaticVertex;
import me.jupiter.object.NetVertex;
import me.jupiter.object.NetVoidVertex;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;

public enum VertexColor {

    DYNAMIC(new Color(0f, 1f, 0f)) {
        @Override
        NetVertex getNewVertex() {
            return new NetDynamicVertex(new Vector3d());
        }
    },
    STATIC(new Color(0f, 0f, 1f)) {
        @Override
        NetVertex getNewVertex() {
            return new NetStaticVertex(new Vector3d());
        }
    },
    VOID(new Color(0f, 0f, 0f)) {
        @Override
        NetVertex getNewVertex() {
            return new NetVoidVertex(new Vector3d());
        }
    };

    public final Color color;
    VertexColor(Color color){
        this.color = color;
    }
    abstract NetVertex getNewVertex();

    public static NetVertex getVertex(Color color){
        for (VertexColor vertex : VertexColor.values()) {
            if (color.getRGB() == vertex.color.getRGB()){
                return vertex.getNewVertex();
            }
        }
        return VertexColor.STATIC.getNewVertex();
    }
}
