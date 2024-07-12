package me.udnekjupiter.graphic.object.fixedsize;

import me.udnekjupiter.graphic.frame.GraphicFrame;
import org.realityforge.vecmath.Vector2d;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.List;

public class FixedSizeLine extends FixedSizeObject{

    private final Vector3d point0;
    private final Vector3d point1;

    public FixedSizeLine(Vector3d point0, Vector3d point1){
        super(new Vector3d());
        this.point0 = point0;
        this.point1 = point1;
    }

    @Override
    public void foundOnFrameAt(List<Point> points, GraphicFrame frame) {
        if (points.size() != 2) return;
        drawLine(points.get(0), points.get(1), Color.GREEN.getRGB(), frame);
    }

    public static void drawLine(Point point0, Point point1, int color, GraphicFrame frame){
        int x0 = point0.x;
        int y0 = point0.y;
        int x1 = point1.x;
        int y1 = point1.y;

        Vector2d direction = new Vector2d(x1-x0, y1-y0);
        double length = direction.length();
        direction.normalize();
        double xStep = direction.x;
        double yStep = direction.y;

        for (int i = 1; i < length; i++) {
            direction.mul((double) (i+1)/i);
            frame.safeSetPixel(
                    (int) (x0+xStep*i),
                    (int) (y0+yStep*i),
                    color);
        }

        frame.safeSetPixel(x0, y0, Color.BLUE.getRGB());
        frame.safeSetPixel(x1, y1, Color.BLUE.getRGB());
    }

    @Override
    public Vector3d[] getPoints() {
        return new Vector3d[]{point0.dup(), point1.dup()};
    }
}
