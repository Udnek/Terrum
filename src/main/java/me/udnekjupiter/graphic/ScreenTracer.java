package me.udnekjupiter.graphic;

import me.udnekjupiter.graphic.object.fixedsize.FixedSizeObject;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScreenTracer {

    private Camera camera;
    private GraphicFrame frame;

    public ScreenTracer(){
    }

    public void renderFrame(GraphicFrame frame, List<FixedSizeObject> objects, Camera camera){
        this.camera = camera;
        this.frame = frame;

        for (FixedSizeObject object : objects) {
            proceedObject(object);
        }
    }

    public void proceedObject(FixedSizeObject object){
        Vector3d cameraPosition = camera.getPosition();
        Vector3d position = object.getPosition();
        List<Point> foundPoints = new ArrayList<>();

        for (Vector3d pointPosition : object.getPoints()) {
            pointPosition.add(position).sub(cameraPosition);

            Point foundPoint = pointPositionOnFrame(pointPosition);
            foundPoints.add(foundPoint);
        }

        object.foundOnFrameAt(foundPoints, frame);
    }

    public Point pointPositionOnFrame(Vector3d vertex){
        camera.rotateBackVector(vertex.normalize());

        Vector3d defaultCameraDirection = new Vector3d(0, 0, 1);
        double angle = defaultCameraDirection.dot(vertex);

        vertex.div(angle);

        int width = frame.getWidth();
        int height = frame.getHeight();
        int screenX = (int) (vertex.x * width + width/2);
        int screenY = (int) (vertex.y * height + height/2);

        return new Point(screenX, screenY);
    }
}
