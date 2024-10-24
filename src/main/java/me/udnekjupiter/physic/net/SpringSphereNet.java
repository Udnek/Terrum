package me.udnekjupiter.physic.net;

import me.udnekjupiter.physic.object.PhysicObject3d;
import me.udnekjupiter.physic.object.SpringObject;
import me.udnekjupiter.physic.object.vertex.NetDynamicVertex;
import me.udnekjupiter.physic.object.vertex.NetVertex;
import me.udnekjupiter.util.VectorUtils;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class SpringSphereNet {

    public static @NotNull List<? extends PhysicObject3d> createFromCuboid(final double radius, final double step){
        List<NetVertex> vertices = new ArrayList<>();
        for (double x = -radius; x <= radius; x+=step) {
            for (double y = -radius; y <= radius; y+=step) {
                for (double z = -radius; z <= radius; z+=step) {
                    Vector3d position = new Vector3d(x, y, z);
                    double distance = VectorUtils.distance(new Vector3d(), position);
                    if (distance > radius) continue;
                    NetVertex vertex = new NetDynamicVertex();
                    vertex.getContainer().position = position;
                    vertex.getContainer().initialPosition = position.dup();
                    vertices.add(vertex);
                }
            }
        }
        List<SpringObject> springs = new ArrayList<>();
        final double EPSILON = 0.0001;
        final double maxDistance = Math.sqrt(2*step*step);
        for (int i = 0; i < vertices.size()-1; i++) {
            for (int j = i+1; j < vertices.size(); j++) {
                NetVertex vertexA = vertices.get(i);
                NetVertex vertexB = vertices.get(j);
                double distance = VectorUtils.distance(vertexA.getPosition(), vertexB.getPosition());
                if (distance > maxDistance + EPSILON) continue;
                springs.add(new SpringObject(vertexA, vertexB, distance, 5000));
            }
        }

        List<PhysicObject3d> objects = new ArrayList<>(vertices);
        objects.addAll(springs);
        return objects;
    }

}
