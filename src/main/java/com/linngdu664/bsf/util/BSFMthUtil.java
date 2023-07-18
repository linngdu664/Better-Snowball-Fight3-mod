package com.linngdu664.bsf.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class BSFMthUtil {
    private static final Random random = new Random();

    // Calculate the cosine of the angle between 2 vectors.
    public static double vec2AngleCos(double x1, double y1, double x2, double y2) {
        return Mth.invSqrt(modSqr(x1, y1)) * Mth.invSqrt(modSqr(x2, y2)) * (x1 * x2 + y1 * y2);
    }

    public static double vec3AngleCos(Vec3 a, Vec3 b) {
        return Mth.invSqrt(a.lengthSqr()) * Mth.invSqrt(b.lengthSqr()) * a.dot(b);
    }

    // Calculate the square of the modulus(length) of a vector.
    public static double modSqr(double x, double y) {
        return x * x + y * y;
    }

    public static double modSqr(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    // These are random number helpers
    public static double randDouble(double a, double b) {
        return random.nextDouble() * (b - a) + a;
    }

    public static int randInt(int a, int b) {
        return random.nextInt(b - a) + a;
    }
}
