package me.udnek.scene;

import me.udnek.Main;
import me.udnek.objects.PointObject;
import me.udnek.objects.PolygonObject;
import me.udnek.objects.SceneObject;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Scene{

    private Camera camera;
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();


    public Scene(){
        camera = new Camera();
        //sceneObjects.add(new PointObject(0.2, 0, 0.5));
        //sceneObjects.add(new PointObject(-0.5, 0.5, 0.5));
        sceneObjects.add(new PointObject(1, 1, 0.5));
        //sceneObjects.add(new PointObject(0, 1, 0.5));
        //sceneObjects.add(new PointObject(3, 0, 0.5));
        sceneObjects.add(new PolygonObject(
                new Vector3d(0, 0, 0),
                new Vector3d(0, 0, 0.5),
                new Vector3d(1, -0.4, 0.5),
                new Vector3d(0, 1, 0.5))
        );
    }

    public BufferedImage renderFrame(int width, int height){
/*        objectZPos += 0.1;
        sceneObjects.get(0).setPosition(new Vector3d(0, 1, objectZPos));
        System.out.println(objectZPos);*/

        Vector3d cameraDirection = camera.getDirection();

        float xOffset = -width/2f;
        float yOffset = -height/2f;
        float fovMultiplayer = 10f;


        fovMultiplayer = fovMultiplayer/width;

        fovMultiplayer = 0.05f;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {

                Vector3d direction = new Vector3d(
                        (i+xOffset) * fovMultiplayer,
                        (j+yOffset) * fovMultiplayer,
                        -1
                );


                //double angle = 1;
                //double angle = cameraDirection.dot(direction)/cameraDirection.length()/direction.length();
                //System.out.println(angle);

                int color = this.rayCast(direction);
                bufferedImage.setRGB(i, j, color);
            }
        }
        return bufferedImage;
    }

    private int rayCast(Vector3d direction){
        for (SceneObject object : sceneObjects) {
            if (rayCastObject(direction, object)) return Color.WHITE.getRGB();
        }

        return 0;
    }

    public boolean rayCastObject(Vector3d direction, SceneObject sceneObject){
        if (sceneObject instanceof PolygonObject){
            return triangleRayIntersection(direction, (PolygonObject) sceneObject);
        }
        else {
            double distance = distanceFromLineToPoint(direction, sceneObject.getPosition().sub(camera.getPosition()));
            return distance < 0.1;
        }
    }


    public Vector3d rotateFromCamera(Vector3d direction){
/*        Vector3d cameraDirection = camera.getDirection();
        double angle = 1;

        direction = new Vector3d(
                direction.x * Math.cos(angle) + direction.x *
        )*/

        return direction;
    }


    public static double distanceFromLineToPoint(Vector3d direction, Vector3d point){
        return new Vector3d().cross(direction, point).length()/direction.length();
    }

    public static boolean triangleRayIntersection(Vector3d direction, PolygonObject polygonObject) {
        final float EPSILON = 0.00001f;

        Vector3d vertex0 = polygonObject.getVertex0();
        Vector3d vertex1 = polygonObject.getVertex1();
        Vector3d vertex2 = polygonObject.getVertex2();

        Vector3d normal = polygonObject.getNormal();
        double perpendicularity = new Vector3d().cross(normal, direction).length();
        //ystem.out.println(perpendicularity);
        if (-EPSILON <= perpendicularity && perpendicularity <= EPSILON){
            //System.out.println("parl");
            return false;
        }

        double distanceToPlane = normal.dot(vertex0);
        Vector3d onPlanePosition = direction.dup().mul(distanceToPlane / normal.dot(direction));
        double actualArea = polygonObject.getArea();

        Vector3d pointToVertex0 = vertex0.sub(onPlanePosition);
        Vector3d pointToVertex1 = vertex1.sub(onPlanePosition);
        Vector3d pointToVertex2 = vertex2.sub(onPlanePosition);

        double area0 = getAreaOfTriangle(pointToVertex0, pointToVertex1);
        double area1 = getAreaOfTriangle(pointToVertex1, pointToVertex2);
        double area2 = getAreaOfTriangle(pointToVertex2, pointToVertex0);

        if (area0 + area1 + area2 > actualArea+0.00001) return false;

        return true;
    }

    public static double getAreaOfTriangle(Vector3d edge0, Vector3d edge1){
        return getAreaOfParallelogram(edge0, edge1)/2;
    }

    public static double getCrossProductLength(Vector3d edge0, Vector3d edge1){
        return new Vector3d().cross(edge0, edge1).length();
    }

    public static double getAreaOfParallelogram(Vector3d edge0, Vector3d edge1){
        return getCrossProductLength(edge0, edge1);
    }
}
