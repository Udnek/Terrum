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

import static java.lang.Math.abs;

public class SpringSphereNet {

    public static @NotNull List<? extends PhysicObject3d> createFromCuboid(double radius, double step, double stiffness){
        return createFromCuboid(new Vector3d(), radius, step, stiffness);
    }
    public static @NotNull List<? extends PhysicObject3d> createFromCuboid(Vector3d center, double radius, double step, double stiffness){
        List<NetVertex> vertices = new ArrayList<>();
        for (double x = -radius; x <= radius; x+=step) {
            for (double y = -radius; y <= radius; y+=step) {
                for (double z = -radius; z <= radius; z+=step) {
                    Vector3d position = new Vector3d(x, y, z);
                    double distance = VectorUtils.distance(new Vector3d(), position);
                    if (distance > radius) continue;
                    NetVertex vertex = new NetDynamicVertex();
                    position.add(center);
                    vertex.getContainer().position = position;
                    vertex.getContainer().initialPosition = position.dup();
                    vertex.getContainer().mass = 0.1;
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
                springs.add(new SpringObject(vertexA, vertexB, distance, stiffness));
            }
        }

        List<PhysicObject3d> objects = new ArrayList<>(vertices);
        objects.addAll(springs);
        return objects;
    }

    public static @NotNull List<? extends PhysicObject3d> createFromVector(double radius, int steps, double stiffness){
        List<NetVertex> vertices = new ArrayList<>();
        for (int i = -steps; i <= steps; i++) {
            for (int j = -steps; j <= steps; j++) {
                for (int k = -steps; k <= steps; k++) {
                    //if (i == 0 && j == 0 && k == 0) continue;
                    if (!(abs(i) == steps || abs(j) == steps || abs(k) == steps)) continue;

                    Vector3d pos = new Vector3d(i, j, k).normalize().mul(radius);
                    NetDynamicVertex vertex = new NetDynamicVertex();
                    vertex.getContainer().position = pos;
                    vertex.getContainer().initialPosition = pos.dup();
                    vertices.add(vertex);
                }
            }
        }

        List<SpringObject> springs = new ArrayList<>();
        for (int i = 0; i < vertices.size()-1; i++) {
            for (int j = i+1; j < vertices.size(); j++) {
                NetVertex vertexA = vertices.get(i);
                NetVertex vertexB = vertices.get(j);
                double distance = VectorUtils.distance(vertexA.getPosition(), vertexB.getPosition());
                if (distance > radius/steps) continue;
                springs.add(new SpringObject(vertexA, vertexB, distance, stiffness));
            }
        }

        List<PhysicObject3d> objects = new ArrayList<>(vertices);
        objects.addAll(springs);
        return objects;
    }
}
