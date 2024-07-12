package me.udnekjupiter.graphic.object.fixedsize;

import me.udnekjupiter.graphic.frame.GraphicFrame;
import org.realityforge.vecmath.Vector3d;

import java.awt.*;
import java.util.List;

public class AxisCrosshair extends FixedSizeObject{

    private static final double size = 0.1;

    private final Vector3d center = new Vector3d();
    private final Vector3d x = new Vector3d(size, 0, 0);
    private final Vector3d y = new Vector3d(0, size, 0);
    private final Vector3d z = new Vector3d(0, 0, size);

    public AxisCrosshair() {
        super(new Vector3d());
    }

    @Override
    public void foundOnFrameAt(List<Point> points, GraphicFrame frame) {
        if (points.size() != 4) return;
        FixedSizeLine.drawLine(points.get(0), points.get(1), Color.RED.getRGB(), frame);
        FixedSizeLine.drawLine(points.get(0), points.get(2), Color.GREEN.getRGB(), frame);
        FixedSizeLine.drawLine(points.get(0), points.get(3), Color.BLUE.getRGB(), frame);
    }

    @Override
    public Vector3d[] getPoints() {
        return new Vector3d[]{
                center.dup(),
                x.dup(),
                y.dup(),
                z.dup()
        };
    }
}
