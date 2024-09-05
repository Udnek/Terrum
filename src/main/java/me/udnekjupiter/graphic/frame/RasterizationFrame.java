package me.udnekjupiter.graphic.frame;

import org.realityforge.vecmath.Vector3d;

public abstract class RasterizationFrame extends CenteredFrame {
    public abstract void setPixel(int x, int y, int z, int color);
    public void safeSetPixel(int x, int y, int z, int color){
        if (!isInBounds(x, y)) return;
        setPixel(x, y, z, color);
    }

    public void drawLine(Vector3d v0, Vector3d v1, int color){
        drawLine((int) v0.x, (int) v0.y, (int) v0.z, (int) v1.x, (int) v1.y, (int) v1.z, color);
    }
    public void drawLine(int x0, int y0, int z0, int x1, int y1, int z1, int color){
        if (!isInBounds(x0, y0) && !isInBounds(x1, y1)) return;

        float dx = x1 - x0;
        float dy = y1 - y0;
        float dz = z1 - z0;
        int steps;
        if (Math.abs(dx) > Math.abs(dy)) {
            steps = (int) Math.abs(dx);
        } else{
            steps = (int) Math.abs(dy);
        }
        dx /= steps;
        dy /= steps;
        dz /= steps;

        // UNSAFE DRAWING
        if (isInBounds(x0, y0) && isInBounds(x1, y1)){
            for (int i = 0; i < steps; i++) {
                setPixel((int) (x0 + dx*i), (int) (y0 + dy*i), (int) (z0 + dz*i), color);
            }
        }
        // SAFE DRAWING
        else {
            for (int i = 0; i < steps; i++) {
                safeSetPixel((int) (x0 + dx*i), (int) (y0 + dy*i), (int) (z0 + dz*i), color);
            }
        }
    }
    public void drawTriangleWireframe(Vector3d v0, Vector3d v1, Vector3d v2, int color){
        drawLine(v0, v1, color);
        drawLine(v1, v2, color);
        drawLine(v2, v0, color);
    }
    public void drawTriangle(Vector3d v0, Vector3d v1, Vector3d v2, int color){
        Vector3d lowest = v0.dup();
        Vector3d middle = v1.dup();
        Vector3d highest = v2.dup();

        if (lowest.y > middle.y) swapVectors(lowest, middle);
        if (middle.y > highest.y){
            swapVectors(middle, highest);
            if (lowest.y > middle.y) swapVectors(lowest, middle);
        }

        double dxLeft = -(highest.x - lowest.x) / (highest.y - lowest.y);
        double dxRightH = -(highest.x - middle.x) / (highest.y - middle.y);
        double dxRightL = -(middle.x - lowest.x) / (middle.y - lowest.y);

        double dzLeft = -(highest.z - lowest.z) / (highest.y - lowest.y);
        double dzRightH = -(highest.z - middle.z) / (highest.y - middle.y);
        double dzRightL = -(middle.z - lowest.z) / (middle.y - lowest.y);

        int ys = (int) (highest.y - middle.y);
        for (int i = 0; i < ys; i++) {
            int y = (int) (highest.y - i);
            drawLine(
                    (int) (highest.x + dxLeft * i), y, (int) (highest.z + dzLeft * i),
                    (int) (highest.x + dxRightH * i), y, (int) (highest.z + dzRightH * i),
                    color
            );
        }

        highest.x += (int) (dxLeft * ys);
        highest.z += (int) (dzLeft * ys);

        for (int i = 0; i < middle.y - lowest.y; i++) {
            int y = (int) (middle.y - i);
            drawLine(
                    (int) (highest.x + dxLeft * i), y, (int) (highest.z + dzLeft * i),
                    (int) (middle.x + dxRightL * i), y, (int) (middle.z + dzRightL * i),
                    color)
            ;
        }
    }

    protected void swapVectors(Vector3d v0, Vector3d v1) {
        double x = v0.x;
        double y = v0.y;
        double z = v0.z;

        v0.x = v1.x;
        v0.y = v1.y;
        v0.z = v1.z;

        v1.x = x;
        v1.y = y;
        v1.z = z;
    }
}
