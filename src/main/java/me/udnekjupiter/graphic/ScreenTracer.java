package me.udnekjupiter.graphic;

import me.udnekjupiter.graphic.frame.GraphicFrame;
import me.udnekjupiter.graphic.object.fixedsize.FixedSizeObject3d;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


// TODO: 7/12/2024 REFACTOR OR REMOVAL???
public class ScreenTracer {

    private Camera camera;
    private GraphicFrame frame;

    public ScreenTracer(){
    }

    public void renderFrame(GraphicFrame frame, List<FixedSizeObject3d> objects, Camera camera){
        this.camera = camera;
        this.frame = frame;

        for (FixedSizeObject3d object : objects) {
            proceedObject(object);
        }
    }

    public void proceedObject(FixedSizeObject3d object){
        Vector3d cameraPosition = camera.getPosition();
        Vector3d position = object.getPosition();
        List<Point> foundPoints = new ArrayList<>();

        for (Vector3d pointPosition : object.getPoints()) {
            pointPosition.add(position).sub(cameraPosition);

            Point foundPoint = pointPositionOnFrame(pointPosition);
            if (foundPoint == null) continue;
            foundPoints.add(foundPoint);
        }

        object.foundOnFrameAt(foundPoints, frame);
    }

    public Point pointPositionOnFrame(Vector3d vertex){
        camera.rotateBackVector(vertex.normalize());

        Vector3d defaultCameraDirection = new Vector3d(0, 0, 1).mul(camera.getFov());
        double angle = defaultCameraDirection.dot(vertex);
        if (angle < 0) return null;

        vertex.div(angle);

        int width = frame.getWidth();
        int height = frame.getHeight();
        int screenX = (int) (vertex.x * width + width/2);
        int screenY = (int) (vertex.y * height + height/2);

        return new Point(screenX, screenY);
    }
}
