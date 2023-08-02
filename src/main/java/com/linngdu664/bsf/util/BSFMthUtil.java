package com.linngdu664.bsf.util;

import net.minecraft.core.Vec3i;
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

    public static double randDoubleWithInfer(double a, double b) {
        return a > b ? random.nextDouble() * (a - b) + b : random.nextDouble() * (b - a) + a;
    }

    public static int randInt(int a, int b) {
        return random.nextInt(b - a) + a;
    }

    public static int randIntWithInfer(int a, int b) {
        return a > b ? random.nextInt() * (a - b) + b : random.nextInt() * (b - a) + a;
    }

    public static double randNormalDouble(double mu, double sigma) {
        return random.nextGaussian(mu, sigma);
    }

    public static Vec3 rotationToVector(double r, double theta, double phi) {
        return new Vec3(r * Math.sin(phi) * Math.cos(theta), r * Math.cos(phi), r * Math.sin(phi) * Math.sin(theta));
    }

    public static Vec3i vec3ToI(Vec3 vec3) {
        return new Vec3i(Mth.floor(vec3.x), Mth.floor(vec3.y), Mth.floor(vec3.z));
    }
}
