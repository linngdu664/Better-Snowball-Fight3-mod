package com.linngdu664.bsf.util;

import com.mojang.math.Vector3f;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class BSFCommonUtil {
    private static final Random random = new Random();

    // Calculate the cosine of the angle between 2 vectors.
    public static double vec2AngleCos(double x1, double y1, double x2, double y2) {
        return Mth.fastInvSqrt(lengthSqr(x1, y1)) * Mth.fastInvSqrt(lengthSqr(x2, y2)) * (x1 * x2 + y1 * y2);
    }

    public static double vec3AngleCos(Vec3 a, Vec3 b) {
        return Mth.fastInvSqrt(a.lengthSqr()) * Mth.fastInvSqrt(b.lengthSqr()) * a.dot(b);
    }

    public static double vec3Angle(Vec3 a, Vec3 b) {
        double angleCos = vec3AngleCos(a, b);
        if (angleCos > 1) {
            angleCos = 1;
        } else if (angleCos < -1) {
            angleCos = -1;
        }
        return Math.acos(angleCos);
    }

    // Calculate the square of the modulus(length) of a vector.
    public static double lengthSqr(double x, double y) {
        return x * x + y * y;
    }

    public static double lengthSqr(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    // These are random number helpers
    // Please use Mojang's RandomSource for better performance.
    public static double randDouble(Random randomSource, double a, double b) {
        return randomSource.nextDouble() * (b - a) + a;
    }

    public static double staticRandDouble(double a, double b) {
        return random.nextDouble(a, b);
    }

    public static double randDoubleWithInfer(Random randomSource, double a, double b) {
        return a > b ? randomSource.nextDouble() * (a - b) + b : randomSource.nextDouble() * (b - a) + a;
    }

    public static int staticRandInt(int a, int b) {
        return random.nextInt(a, b);
    }

    public static int randIntWithInfer(Random randomSource, int a, int b) {
        return a > b ? randomSource.nextInt() * (a - b) + b : randomSource.nextInt() * (b - a) + a;
    }

    public static double randNormalDouble(Random randomSource, double mu, double sigma) {
        return mu + sigma * randomSource.nextGaussian();
    }

    public static Vec3 rotationToVector(double r, double theta, double phi) {
        return new Vec3(r * Mth.sin((float) phi) * Mth.cos((float) theta), r * Mth.cos((float) phi), r * Mth.sin((float) phi) * Mth.sin((float) theta));
    }

    public static Vec3i vec3ToI(Vec3 vec3) {
        return new Vec3i(Mth.floor(vec3.x), Mth.floor(vec3.y), Mth.floor(vec3.z));
    }

    public static boolean eq(double a, double b) {
        return Math.abs(a - b) < 1e-9;
    }

    public static boolean eq(float a, float b) {
        return Math.abs(a - b) < 1e-9;
    }

    public static void putVector3f(CompoundTag compoundTag, String key, Vector3f vec3) {
        compoundTag.putFloat(key + "X", vec3.x());
        compoundTag.putFloat(key + "Y", vec3.y());
        compoundTag.putFloat(key + "Z", vec3.z());
    }

    public static Vector3f getVector3f(CompoundTag compoundTag, String name) {
        float x = compoundTag.getFloat(name + "X");
        float y = compoundTag.getFloat(name + "Y");
        float z = compoundTag.getFloat(name + "Z");
        return new Vector3f(x, y, z);
    }
}
