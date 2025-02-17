package me.udnekjupiter.util;

import me.udnekjupiter.physic.engine.PhysicEngine3d;
import org.jcodec.containers.mp4.SampleOffsetUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;

public class Utils {

    public static int NANOS_IN_SECOND = (int) Math.pow(10, 9);

    ///////////////////////////////////////////////////////////////////////////
    // GENERAL
    ///////////////////////////////////////////////////////////////////////////
    public static @NotNull String roundToPrecision(double number, int precision){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(precision);
        return numberFormat.format(number);
    }

    public static boolean roughlyEquals(double a, double b, double maxDistance){
        return Math.abs(a + b) <= maxDistance;
    }

    public static @NotNull Point clamp(@NotNull Point point, int min, int max){
        point.x = Math.clamp(point.x, min, max);
        point.y = Math.clamp(point.y, min, max);
        return point;
    }

    ///////////////////////////////////////////////////////////////////////////
    // IMAGE
    ///////////////////////////////////////////////////////////////////////////
    public static BufferedImage resizeImage(@NotNull BufferedImage image, int newWidth, int newHeight){
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
        newImage.getGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);
        return newImage;
    }
    public static void resizeImage(@NotNull BufferedImage image, int newWidth, int newHeight, BufferedImage output){
        output.getGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // COLOR
    ///////////////////////////////////////////////////////////////////////////

    public static Color vectorToColor(@NotNull Vector3d color){
        return new Color((float) color.x, (float) color.y, (float) color.z);
    }

    public static int multiplyColor(int intColor, float n){
        Color color = new Color(intColor);
        return new Color(
                Math.max(color.getRed()/255f * n, 1),
                Math.max(color.getGreen()/255f * n, 1),
                Math.max(color.getBlue()/255f * n, 1)
        ).getRGB();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ROTATION
    ///////////////////////////////////////////////////////////////////////////

    public static float normalizeYaw(float angle){
        angle = angle % 360f;
        if (angle > 180) angle = -360 + angle;
        else if (angle < -180) angle = 360 + angle;
        return angle;
    }


    public static float normalizePitch(float angle){
        if (angle > 90) {return 90;}
        if (angle < -90) {return -90;}
        return angle;
    }

    ///////////////////////////////////////////////////////////////////////////
    // PHYSICS
    ///////////////////////////////////////////////////////////////////////////

    public static @NotNull Vector3d getMouseDragAcceleration(@NotNull Vector3d target, @NotNull Vector3d source){
        double stiffnessConstant = 10_000;
        double relaxedLengthConstant = 0;
        Vector3d normalizedDirection = VectorUtils.getNormalizedDirection(target, source);
        double elasticForce = stiffnessConstant * (VectorUtils.distance(target, source) - relaxedLengthConstant);
        return normalizedDirection.mul(elasticForce);
    }

    @NotNull @Deprecated
    public static Vector3d getStokesDragForce(double radius, Vector3d velocity){
        // Force = vector velocity * dynamic viscosity * radius * -6pi
        return velocity.dup().mul(18.27*Math.pow(10, -6)).mul(radius).mul(-6*Math.PI);
    }

    @NotNull
    public static Vector3d getSphereDragForce(double radius, Vector3d velocity){
        /*
        F = (-1) * Cd * p * (v^2)/2 * A, where:
        [Cd] (dimensionless) is the shape's drag coefficient (constant variable for a specific shape)
        [p] (kg/m^3) is the density of the medium (air, in our case)
        [v] (m/s) is the velocity of an object
        [A] (m^2) is the cross-sectional area of an object (PI*R^2 for sphere, in our case)
        */
        return velocity.dup().normalize().mul((-1) * (PhysicEngine3d.SPHERE_DRAG_COEFFICIENT * 1.225 * (Math.pow(velocity.length(), 2)/2) * (Math.PI * Math.pow(radius, 2))));
    }


}
