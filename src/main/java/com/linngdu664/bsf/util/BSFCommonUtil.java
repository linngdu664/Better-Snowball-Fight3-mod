package com.linngdu664.bsf.util;

import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Random;

public class BSFCommonUtil {
    private static final Random random = new Random();

    // Calculate the cosine of the angle between 2 vectors.
    public static double vec2AngleCos(double x1, double y1, double x2, double y2) {
        return Mth.invSqrt(lengthSqr(x1, y1)) * Mth.invSqrt(lengthSqr(x2, y2)) * (x1 * x2 + y1 * y2);
    }

    public static double vec3AngleCos(Vec3 a, Vec3 b) {
        return Mth.invSqrt(a.lengthSqr()) * Mth.invSqrt(b.lengthSqr()) * a.dot(b);
    }

    // Calculate the square of the modulus(length) of a vector.
    public static double lengthSqr(double x, double y) {
        return x * x + y * y;
    }

    public static double lengthSqr(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    /**
     * 使用RandomSource生成[a, b)的随机双精度浮点数，对mojang方法的扩充
     */
    public static double randDouble(RandomSource randomSource, double a, double b) {
        return randomSource.nextDouble() * (b - a) + a;
    }

    /**
     * 使用RandomSource生成[min(a, b), max(a, b))的随机双精度浮点数
     */
    public static double randDoubleWithInfer(RandomSource randomSource, double a, double b) {
        return a > b ? randomSource.nextDouble() * (a - b) + b : randomSource.nextDouble() * (b - a) + a;
    }

    public static double randNormalDouble(RandomSource randomSource, double mu, double sigma) {
        return mu + sigma * randomSource.nextGaussian();
    }

    public static int randIntWithInfer(RandomSource randomSource, int a, int b) {
        return a > b ? randomSource.nextInt(b, a) : randomSource.nextInt(a, b);
    }

    /**
     * 不要在能获取到RandomSource对象时用这个方法！
     */
    public static double staticRandDouble(double a, double b) {
        return random.nextDouble(a, b);
    }

    /**
     * 不要在能获取到RandomSource对象时用这个方法！
     */
    public static int staticRandInt(int a, int b) {
        return random.nextInt(a, b);
    }

    /**
     * 注意：这玩意的phi的取值是[0, pi]而不是[-pi/2, pi/2]！
     * @param r
     * @param theta
     * @param phi
     * @return
     */
    public static Vec3 radRotationToVector(double r, double theta, double phi) {
        return new Vec3(r * Mth.sin((float) phi) * Mth.cos((float) theta), r * Mth.cos((float) phi), r * Mth.sin((float) phi) * Mth.sin((float) theta));
    }

    public static Vec3i vec3ToI(Vec3 vec3) {
        return new Vec3i(Mth.floor(vec3.x), Mth.floor(vec3.y), Mth.floor(vec3.z));
    }

    public static boolean eq(double a, double b) {
        return Math.abs(a - b) < 1e-10;
    }

    public static boolean eq(float a, float b) {
        return Math.abs(a - b) < 1e-5;
    }

    public static void putVec3(CompoundTag compoundTag, String key, Vector3f vec3) {
        compoundTag.putFloat(key + "X", vec3.x);
        compoundTag.putFloat(key + "Y", vec3.y);
        compoundTag.putFloat(key + "Z", vec3.z);
    }

    public static Vector3f getVec3(CompoundTag compoundTag, String name) {
        float x = compoundTag.getFloat(name + "X");
        float y = compoundTag.getFloat(name + "Y");
        float z = compoundTag.getFloat(name + "Z");
        return new Vector3f(x, y, z);
    }

    public static boolean pointOnTheFrontConeArea(Vec3 p1v, Vec3 p1, Vec3 p2, double pointToVectorMaxDistance, double pointToVectorNormalPlaneMaxDistance) {
        Vec3 p1p2v = p2.add(p1.reverse());
        double b = p1p2v.dot(p1v) / p1v.length();
        double a = Math.sqrt(p1p2v.lengthSqr() - b * b);
        return a < pointToVectorMaxDistance && b < pointToVectorNormalPlaneMaxDistance;
    }
}
