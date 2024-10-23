package me.udnekjupiter.graphic.engine.raytrace;

import me.udnekjupiter.Main;
import me.udnekjupiter.app.ApplicationSettings;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.frame.GraphicFrame;
import me.udnekjupiter.graphic.object.light.LightSource;
import me.udnekjupiter.graphic.polygonholder.PolygonHolder;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.Triangle;
import me.udnekjupiter.util.Utils;
import me.udnekjupiter.util.VectorUtils;
import org.jetbrains.annotations.NotNull;
import org.realityforge.vecmath.Vector3d;

public class RayTracer {

    private Camera camera;
    private Vector3d cameraPosition;
    private int width, height;
    private double fovMultiplier;

    private GraphicFrame frame;

    private PolygonHolder polygonHolder;
    private LightSource lightSource;
    private Vector3d lightPosition;

    private boolean doLight;
    // TODO: 9/5/2024 DEBUG COLORIZE DOESNT WORK
    private boolean debugColorizePlanes;

    public RayTracer(@NotNull LightSource lightSource){
        this.doLight = Main.getMain().getApplication().getSettings().doLight;
        this.lightSource = lightSource;
    }


    ///////////////////////////////////////////////////////////////////////////
    // TRACING
    ///////////////////////////////////////////////////////////////////////////

    public int rayTrace(@NotNull Vector3d direction){
        Vector3d nearestHitPosition = null;
        RenderableTriangle nearestPlane = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (RenderableTriangle plane : polygonHolder.getCachedPlanes(direction)) {
            Vector3d hitPosition = VectorUtils.triangleRayIntersection(direction, plane);

            if (hitPosition == null) continue;
            if (hitPosition.lengthSquared() < nearestDistance) {
                nearestHitPosition = hitPosition;
                nearestPlane = plane;
                nearestDistance = hitPosition.lengthSquared();
            }
        }

        if (nearestPlane == null) return 0;
        return colorizeRayTrace(nearestHitPosition, nearestPlane);
    }

    public void renderFrame(@NotNull GraphicFrame frame, @NotNull PolygonHolder polygonHolder, @NotNull Camera camera){;
        ApplicationSettings settings = Main.getMain().getApplication().getSettings();
        this.frame = frame;
        this.width = frame.getWidth();
        this.height = frame.getHeight();

        this.camera = camera;
        this.polygonHolder = polygonHolder;
        this.cameraPosition = camera.getPosition();
        this.fovMultiplier = height/camera.getFov();
        this.debugColorizePlanes = settings.debugColorizePlanes;
        this.doLight = settings.doLight;
        if (doLight) lightPosition = lightSource.getPosition();

        int cores = settings.cores;
        if (cores != 1){

            Thread[] threads = new Thread[cores];
            int threadXStep = width / cores;
            for (int i = 0; i < cores; i++) {
                Thread thread = new Thread(new RayTracerRunnable(threadXStep * i, threadXStep * (i + 1), 0, height));
                threads[i] = thread;
                thread.start();
            }
            try {
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {throw new RuntimeException(e);}


        } else {
            RayTracerRunnable runnable = new RayTracerRunnable(0, width, 0, height);
            runnable.run();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // COLORIZING
    ///////////////////////////////////////////////////////////////////////////

    private double positionLighted(@NotNull Vector3d position, @NotNull RenderableTriangle plane){

        // to absolute position;
        position.add(cameraPosition);
        // to light relative position
        position.sub(lightPosition);
        // from light to point direction
        Vector3d direction = position;

        final float EPSILON = 0.0001f;

        for (Triangle TraceableTriangle : polygonHolder.getLightCachedPlanes(direction)) {
            Vector3d hitPosition = VectorUtils.triangleRayIntersection(direction, TraceableTriangle);
            if (hitPosition != null){
                if (direction.lengthSquared() - EPSILON > hitPosition.lengthSquared()){
                    return 0;
                }
            }
        }
        double perpendicularity = 1 - new Vector3d().cross(plane.getNormal().normalize(), direction.normalize()).length();
        return perpendicularity;
    }

    private int colorizeRayTrace(@NotNull Vector3d hitPosition, @NotNull RenderableTriangle plane){

        int color =  plane.getTraceColor(hitPosition);

        if (doLight){
            float light = (float) positionLighted(hitPosition, plane);
            light += 0.1f;
            if (light < 0.15) light = 0.15f;
            else if (light > 1) light = 1;

            color = Utils.multiplyColor(color, light);
        }

        return color;
    }

    ///////////////////////////////////////////////////////////////////////////
    // THREAD
    ///////////////////////////////////////////////////////////////////////////

    public class RayTracerRunnable implements Runnable{
        private final int xFrom, xTo, yFrom, yTo;
        public RayTracerRunnable(int xFrom, int xTo, int yFrom, int yTo){
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

                    camera.rotateVector(direction);

                    int color = rayTrace(direction);
                    frame.setPixel(x, y, color);
                }
            }
        }
    }
}
