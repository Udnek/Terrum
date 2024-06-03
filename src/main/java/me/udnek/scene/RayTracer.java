package me.udnek.scene;

import me.udnek.objects.SceneObject;
import me.udnek.objects.light.LightSource;
import me.udnek.utils.Triangle;
import me.udnek.utils.VectorUtils;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RayTracer {

    private Vector3d cameraPosition;
    private double cameraYaw, cameraPitch;
    private int width, height;
    private double fovMultiplier;

    private int[] frame;

    private List<? extends SceneObject> objectsToRender;
    private List<Triangle> cachedPlanes;

    private LightSource lightSource;
    private Vector3d lastLightPosition;
    private List<Triangle> lightCachedPlanes;

    private final boolean doLight;

    public RayTracer(List<? extends SceneObject> objectsToRender, LightSource lightSource, boolean doLight){
        this.objectsToRender = objectsToRender;
        this.lightSource = lightSource;
        this.lastLightPosition = null;
        this.doLight = doLight;
    }

    ///////////////////////////////////////////////////////////////////////////
    // CACHING
    ///////////////////////////////////////////////////////////////////////////

    public void recacheObjects(Vector3d position){
        cameraPosition = position;

        // camera cache
        cachedPlanes = new ArrayList<>();
        for (SceneObject object : objectsToRender) {
            cacheObject(cachedPlanes, object, cameraPosition);
        }

        // light cache
        if (!doLight) return;

        Vector3d lightSourcePosition = lightSource.getPosition();
        if (lastLightPosition != null && lightSourcePosition.isEqualTo(lastLightPosition)) return; //skipping light recache
        lastLightPosition = lightSourcePosition;
        lightCachedPlanes = new ArrayList<>();
        for (SceneObject object : objectsToRender) {
            cacheObject(lightCachedPlanes, object, lightSourcePosition);
        }
    }

    private void cacheObject(List<Triangle> cache, SceneObject object, Vector3d position){
        Vector3d objectPosition = object.getPosition();
        for (Triangle plane: object.getRenderTriangles()) {
            plane.addToAllVertexes(objectPosition).subFromAllVertexes(position);
            cache.add(plane);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // TRACING
    ///////////////////////////////////////////////////////////////////////////

    public int rayTrace(Vector3d direction){
        Vector3d nearestHitPosition = null;
        Triangle nearestPlane = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (Triangle plane : cachedPlanes) {
            Vector3d hitPosition = VectorUtils.triangleRayIntersection(direction, plane);

            if (hitPosition != null) {
                if (hitPosition.lengthSquared() < nearestDistance) {
                    nearestHitPosition = hitPosition;
                    nearestPlane = plane;
                    nearestDistance = hitPosition.lengthSquared();
                }
            }
        }

        if (nearestPlane == null) return 0;
        return colorizeRayTrace(nearestHitPosition, nearestPlane);
    }

    public void rotateDirectionAsCamera(Vector3d direction){
        VectorUtils.rotatePitch(direction, cameraPitch);
        VectorUtils.rotateYaw(direction, cameraYaw);
    }

    public boolean allThreadsDone(RayTracerThread[] threads){
        for (RayTracerThread thread : threads) {
            if (!thread.done) return false;
        }
        return true;
    }

    public int[] renderFrame(int width, int height, double cameraYaw, double cameraPitch, double fovMultiplier, int cores){
        frame = new int[width*height];

        this.width = width;
        this.height = height;
        this.cameraYaw = Math.toRadians(cameraYaw);
        this.cameraPitch = Math.toRadians(cameraPitch);
        this.fovMultiplier = fovMultiplier;

        RayTracerThread[] threads = new RayTracerThread[cores];
        int threadXStep = width / cores;
        for (int i = 0; i < cores; i++) {
            threads[i] = new RayTracerThread(threadXStep*i, threadXStep*(i+1), 0, height);
        }
        for (RayTracerThread thread : threads) {
            new Thread(thread).start();
        }
        while (!allThreadsDone(threads)) {
            try {
                Thread.sleep(1, 0);
            } catch (InterruptedException e) { throw new RuntimeException(e);}
        }
        return frame;

/*        switch (cores){
            case 12: {
                int threadXStep = width / 12;

                RayTracerThread[] threads = new RayTracerThread[12];
                for (int i = 0; i < 12; i++) {
                    threads[i] = new RayTracerThread(threadXStep*i, threadXStep*(i+1), 0, height);
                }
                for (RayTracerThread thread : threads) {
                    new Thread(thread).start();
                }

                while (!allThreadsDone(threads)) {
                    try {
                        Thread.sleep(1, 0);
                    } catch (InterruptedException e) { throw new RuntimeException(e);}
                }
                break;
            }



            case 6: {
                int threadXStep = width / 6;

                RayTracerThread thread0 = new RayTracerThread(threadXStep * 0, threadXStep * 1, 0, height);
                RayTracerThread thread1 = new RayTracerThread(threadXStep * 1, threadXStep * 2, 0, height);
                RayTracerThread thread2 = new RayTracerThread(threadXStep * 2, threadXStep * 3, 0, height);
                RayTracerThread thread3 = new RayTracerThread(threadXStep * 3, threadXStep * 4, 0, height);
                RayTracerThread thread4 = new RayTracerThread(threadXStep * 4, threadXStep * 5, 0, height);
                RayTracerThread thread5 = new RayTracerThread(threadXStep * 5, threadXStep * 6, 0, height);
                new Thread(thread0).start();
                new Thread(thread1).start();
                new Thread(thread2).start();
                new Thread(thread3).start();
                new Thread(thread4).start();
                new Thread(thread5).start();

                while (!(thread0.done && thread1.done && thread2.done && thread3.done && thread4.done && thread5.done)) {
                    try {
                        Thread.sleep(1, 0);
                    } catch (InterruptedException e) { throw new RuntimeException(e);}
                }
                break;
            }

            case 4: {
                RayTracerThread thread0 = new RayTracerThread(0, width / 2, height / 2, height);
                RayTracerThread thread1 = new RayTracerThread(width / 2, width, height / 2, height);
                RayTracerThread thread2 = new RayTracerThread(0, width / 2, 0, height / 2);
                RayTracerThread thread3 = new RayTracerThread(width / 2, width, 0, height / 2);
                new Thread(thread0).start();
                new Thread(thread1).start();
                new Thread(thread2).start();
                new Thread(thread3).start();

                while (!(thread0.done && thread1.done && thread2.done && thread3.done)) {
                    try {
                        Thread.sleep(1, 0);
                    } catch (InterruptedException e) {throw new RuntimeException(e);}
                }
                break;
            }

            case 1: {
                RayTracerThread thread0 = new RayTracerThread(0, width, 0, height);
                new Thread(thread0).start();
                while (!thread0.done) {
                    try {
                        Thread.sleep(1, 0);
                    } catch (InterruptedException e) {throw new RuntimeException(e);}
                }
                break;
            }
        }*/


        //return frame;
    }

    ///////////////////////////////////////////////////////////////////////////
    // COLORIZING
    ///////////////////////////////////////////////////////////////////////////

    private double positionLighted(Vector3d position, Triangle plane){

        // to absolute position;
        position.add(cameraPosition);
        // to light relative position
        position.sub(lastLightPosition);
        // from light to point direction
        Vector3d direction = position;

        final float EPSILON = 0.0001f;

        for (Triangle triangle : lightCachedPlanes) {
            Vector3d hitPosition = VectorUtils.triangleRayIntersection(direction, triangle);
            if (hitPosition != null){
                if (direction.lengthSquared() - EPSILON > hitPosition.lengthSquared()){
                    return 0;
                }
            }
        }
        double perpendicularity = 1 - new Vector3d().cross(plane.getNormal().normalize(), direction.normalize()).length();
        return perpendicularity;
    }

    private int colorizeRayTrace(Vector3d hitPosition, Triangle plane){

        double d0 = VectorUtils.distance(hitPosition, plane.getVertex0());
        double d1 = VectorUtils.distance(hitPosition, plane.getVertex1());
        double d2 = VectorUtils.distance(hitPosition, plane.getVertex2());
        Vector3d distances = new Vector3d(d0, d1, d2);
        double minDistance = VectorUtils.getMin(distances);
        Vector3d color;
        if (minDistance <= 0.01){
            color = new Vector3d(1f, 1f, 1f);
        } else {
            color = new Vector3d(1/d0, 1/d1 ,1/d2);
            color.div(VectorUtils.getMax(color));
            VectorUtils.cutTo(color, 1f);
        }

        if (doLight){
            float light = (float) positionLighted(hitPosition, plane);
            light += 0.1f;
            if (light < 0.15) light = 0.15f;
            else if (light > 1) light = 1;

            color.mul(light);
        }


        return new Color((float) color.x, (float) color.y, (float) color.z).getRGB();

    }
    public class RayTracerThread implements Runnable{
        private final int xFrom, xTo, yFrom, yTo;
        private boolean done = false;
        public RayTracerThread(int xFrom, int xTo, int yFrom, int yTo){
            this.xFrom = xFrom;
            this.xTo = xTo;
            this.yFrom = yFrom;
            this.yTo = yTo;
        }

        @Override
        public void run() {
            double xOffset = -width/2.0;
            double yOffset = -height/2.0;

            for (int x = xFrom; x < xTo; x++) {
                for (int y = yFrom; y < yTo; y++) {

                    Vector3d direction = new Vector3d(
                            (x+xOffset),
                            (y+yOffset),
                            fovMultiplier
                    );

                    rotateDirectionAsCamera(direction);

                    int color = rayTrace(direction);
                    frame[(height-y-1)*width + x] = color;
                }
            }
            done = true;
        }
    }
}
