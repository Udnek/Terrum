package me.udnekjupiter.util;

import me.udnekjupiter.physic.engine.ConstantValues;
import me.udnekjupiter.util.vector.Vector3d;
import me.udnekjupiter.util.vector.VectorUtils;
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

    public static int color(int r, int g, int b, int a){
        return  ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF));
    }

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
        F = Cd * p * (v^2)/2 * A, where:
        [F] (N) is the force applied to an object
        [Cd] (dimensionless) is the shape's drag coefficient (constant variable for a specific shape)
        [p] (kg/m^3) is the density of the medium (air, in our case)
        [v] (m/s) is the velocity of an object
        [A] (m^2) is the cross-sectional area of an object (PI*R^2 for sphere, in our case)
        Force is applied as a vector, opposite to velocity (therefore it's a normalized velocity vector multiplied by -1)
        */
        return velocity.dup().normalize().mul((-1) * (ConstantValues.SPHERE_DRAG_COEFFICIENT * ConstantValues.AIR_DENSITY * (Math.pow(velocity.length(), 2)/2) * (Math.PI * Math.pow(radius, 2))));
    }

    public static double getHookeForce(double stiffness, double relaxedLength, double length){
        /*
        F = k * (l - l0), where:
        [F] (N) is the force that imaginary spring is applying to both of its ends
        [k] (N/m) is the hooke's coefficient or imaginary spring's stiffness
        [l] (m) is the imaginary spring's current extension
        [l0] (m) is the imaginary spring's relaxed condition, so if (l = l0) - no force is applied
        */
        return stiffness * (length - relaxedLength);
    }

    public static double getSphereWeight(double density, double radius){
        return 4*Math.PI*Math.pow(radius, 3)/3 * density;
    }

    public static double getHollowSphereWeight(double density, double radius, double thickness){
        return getSphereWeight(density, radius) - getSphereWeight(density, radius - thickness);
    }
}
