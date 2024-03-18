package me.udnek.scene;


import me.udnek.objects.PolygonObject;
import me.udnek.objects.SceneObject;
import me.udnek.objects.TetrahedronObject;
import me.udnek.utils.Triangle;
import org.realityforge.vecmath.Vector3d;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Scene{

    private Camera camera;
    private ArrayList<SceneObject> sceneObjects = new ArrayList<>();


    public Scene(){
        camera = new Camera();
        //sceneObjects.add(new PointObject(0.2, 0, 0.5));
        //sceneObjects.add(new PointObject(-0.5, 0.5, 0.5));
        //sceneObjects.add(new PointObject(1, 1, 0.5));
        //sceneObjects.add(new PointObject(0, 1, 0.5));
        //sceneObjects.add(new PointObject(3, 0, 0.5));
        sceneObjects.add(
                new TetrahedronObject(
                        //pos
                        new Vector3d(0, 0, 3),
                        //up
                        new Vector3d(0.5, 1, 0.5),
                        //bottom
                        new Vector3d(-1, -0.5, 1),
                        new Vector3d(0.6, 0, 0),
                        new Vector3d(-0.1, 0, 0)

                )
        );
/*        sceneObjects.add(
                new PolygonObject(
                        new Vector3d(0, 0, 0),
                        new Triangle(
                                new Vector3d(0, 0, 0.5),
                                new Vector3d(2, 0, 0.5),
                                new Vector3d(0, 1, 0.5)
*//*                               new Vector3d(0, 0, 1),
                                new Vector3d(0.4, 1, 1),
                                new Vector3d(0.4, 0, 0.5)*//*
                        )
                )
        );*/
    }

    public BufferedImage renderFrame(int width, int height){

        RayTracer rayTracer = new RayTracer(camera.getPosition(), sceneObjects);

        Vector3d cameraDirection = camera.getDirection();

        //sceneObjects.get(0).move(new Vector3d(0, -0.04, 0));
        sceneObjects.get(0).move(new Vector3d(0, 0, -0.07));
        //System.out.println(sceneObjects.get(0).getPosition().asString());
        //System.out.println(sceneObjects.get(0).getRenderTriangles()[0].asString());

        float xOffset = -width/2f;
        float yOffset = -height/2f;


/*        float fovMultiplayer = 10f;
        fovMultiplayer = fovMultiplayer/width;*/
        float fovMultiplayer = 0.05f;


        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                Vector3d direction = new Vector3d(
                        (i+xOffset) * fovMultiplayer,
                        (j+yOffset) * fovMultiplayer,
                        -10
                );

                int color = rayTracer.rayTrace(direction).getSuggestedColor();
                bufferedImage.setRGB(i, j, color);
            }
        }
        return bufferedImage;
    }
}
