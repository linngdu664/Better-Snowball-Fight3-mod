package com.linngdu664.bsf.util;

import com.linngdu664.bsf.event.BSFGui;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Vector2i;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class BSFCommonUtil {
    private static final Random random = new Random();

    // Calculate the cosine of the angle between 2 vectors.
    public static double vec2AngleCos(double x1, double y1, double x2, double y2) {
        return Mth.invSqrt(lengthSqr(x1, y1)) * Mth.invSqrt(lengthSqr(x2, y2)) * (x1 * x2 + y1 * y2);
    }

    public static double vec3AngleCos(Vec3 a, Vec3 b) {
        return Mth.invSqrt(a.lengthSqr()) * Mth.invSqrt(b.lengthSqr()) * a.dot(b);
    }

    /**
     * a向量在b向量方向上的投影
     */
    public static double vec3Projection(Vec3 a, Vec3 b) {
        return vec3AngleCos(a,b)*a.length();
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
     * @param r     radius
     * @param theta yaw in radian [0, 2*pi]
     * @param phi   pitch in radian [-pi/2, pi/2]
     * @return      xyz vector
     */
    public static Vec3 radRotationToVector(double r, double theta, double phi) {
        return new Vec3(r * Mth.cos((float) phi) * Mth.cos((float) theta), r * Mth.sin((float) phi), r * Mth.cos((float) phi) * Mth.sin((float) theta));
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

    /**
     * 自定义二次函数
     * @param x x值
     * @param maxX x最大值
     * @param maxY y最大值
     * @return y值
     */
    public static double quadraticFunction(double x, double maxX, double maxY) {
        return 4*x*maxY*(maxX-x)/(maxX*maxX);
    }

    public static boolean pointOnTheFrontConeArea(Vec3 p1v, Vec3 p1, Vec3 p2, double pointToVectorMaxDistance, double pointToVectorNormalPlaneMaxDistance) {
        Vec3 p1p2v = p2.add(p1.reverse());
        double b = p1p2v.dot(p1v) / p1v.length();
        double a = Math.sqrt(p1p2v.lengthSqr() - b * b);
        return a < pointToVectorMaxDistance && b < pointToVectorNormalPlaneMaxDistance;
    }

    /**
     * 在onHit里使用，返回真实击中点位，或者实体的碰撞箱中心点
     * @return 撞击点位
     */
    public static Vec3 getRealHitPosOnMoveVecWithHitResult(Entity pProjectile, HitResult pResult){
        if (pResult.getType()==HitResult.Type.ENTITY){
            return getRealEntityHitPosOnMoveVecWithHitResult(pProjectile,(EntityHitResult) pResult);
        }else{
            return pResult.getLocation();
        }
    }

    /**
     * 在onEntityHit里自动判断如果获取真实撞击点位失败则获取EntityHitResult实体的碰撞箱中心点
     * @return 撞击点位
     */
    public static Vec3 getRealEntityHitPosOnMoveVecWithHitResult(Entity pProjectile, EntityHitResult pResult){
        Vec3 vec3 = getRealEntityHitPosOnMoveVec(pProjectile);
        if (vec3 == null) {
            vec3=pResult.getEntity().getBoundingBox().getCenter();
        }
        return vec3;
    }

    /**
     * 直接通过传入实体位置和速度获取实体在当前tick撞击实体的真实点位
     * @return 撞击点位，有可能为null
     */
    public static Vec3 getRealEntityHitPosOnMoveVec(Entity pProjectile) {
        Vec3 vec3 = pProjectile.getDeltaMovement();
        Level level = pProjectile.level();
        Vec3 vec31 = pProjectile.position();
        return getRealEntityHitPos(level, pProjectile,vec31, vec3, Entity::canBeHitByProjectile);
    }
    @Nullable
    public static Vec3 getRealEntityHitPos(Level pLevel, Entity pProjectile, Vec3 pStartVec, Vec3 pEndVecOffset, Predicate<Entity> pFilter) {
        Vec3 pEndVec=pStartVec.add(pEndVecOffset);
        AABB pBoundingBox = pProjectile.getBoundingBox().expandTowards(pEndVecOffset).inflate(1.0);
        double d0 = Double.MAX_VALUE;
        Vec3 vec3 = null;
        for (Entity entity1 : pLevel.getEntities(pProjectile, pBoundingBox, pFilter)) {
            AABB aabb = entity1.getBoundingBox().inflate(0.3f);
            Optional<Vec3> optional = aabb.clip(pStartVec, pEndVec);
            if (optional.isPresent()) {
                Vec3 vec31 = optional.get();
                double d1 = pStartVec.distanceToSqr(vec31);
                if (d1 < d0) {
                    d0 = d1;
                    vec3 = vec31;
                }
            }
        }
        return vec3;
    }
//    public static EntityHitResult pickEntity(Entity entity ,Minecraft minecraft,float pPartialTicks) {
//        double d0 = (double)minecraft.gameMode.getPickRange();
//        double entityReach = minecraft.player.getEntityReach(); // Note - MC-76493 - We must validate players cannot click-through objects.
//        minecraft.hitResult = entity.pick(Math.max(d0, entityReach), pPartialTicks, false); // Run pick() with the max of the two, so we can prevent click-through.
//        Vec3 vec3 = entity.getEyePosition(pPartialTicks);
//        boolean flag = false;
//        int i = 3;
//        double d1 = d0;
//        if (d0 > 3.0D) {
//            flag = true;
//        }
//
//        d0 = d0;
//        d0 = d1 = Math.max(d0, entityReach); // Pick entities with the max of both for the same reason.
//
//        d1 *= d1;
//        // If we picked a block, we need to ignore entities past that block. Added != MISS check to not truncate on failed picks.
//        // Also fixes MC-250858
//        if (minecraft.hitResult != null && minecraft.hitResult.getType() != HitResult.Type.MISS) {
//            d1 = minecraft.hitResult.getLocation().distanceToSqr(vec3);
//            double blockReach = minecraft.player.getBlockReach();
//            // Discard the result as a miss if it is outside the block reach.
//            if (d1 > blockReach * blockReach) {
//                Vec3 pos = minecraft.hitResult.getLocation();
//                minecraft.hitResult = BlockHitResult.miss(pos, Direction.getNearest(vec3.x, vec3.y, vec3.z), BlockPos.containing(pos));
//            }
//        }
//
//        Vec3 vec31 = entity.getViewVector(1.0F);
//        Vec3 vec32 = vec3.add(vec31.x * d0, vec31.y * d0, vec31.z * d0);
//        float f = 1.0F;
//        AABB aabb = entity.getBoundingBox().expandTowards(vec31.scale(d0)).inflate(1.0D, 1.0D, 1.0D);
//        return ProjectileUtil.getEntityHitResult(entity, vec3, vec32, aabb, (p_234237_) -> {
//            return !p_234237_.isSpectator() && p_234237_.isPickable();
//        }, d1);
//    }

    public static double vec3GetYaw(Vec3 vec) {
        return Math.atan2(vec.z, vec.x);
    }

    public static double vec3GetPitch(Vec3 vec) {
        return Math.atan2(vec.y, Math.sqrt(vec.x * vec.x + vec.z * vec.z));
    }
    public static MutableComponent getTransMc(String transKey, Object... args){
        return MutableComponent.create(new TranslatableContents(transKey, null, args));
    }
    public static String getTransStr(String transKey, Object... args){
        return getTransMc(transKey,args).getString();
    }
    public static Component getTransCp(String transKey, ChatFormatting format, Object... args){
        return getTransMc(transKey,args).withStyle(format);
    }
    public static void addTrans(List<Component> pTooltipComponents,String transKey, ChatFormatting format, Object... args){
        pTooltipComponents.add(getTransCp(transKey,format,args));
    }
    public static class TipBuilder{
        public List<Component> tooltipComponents;

        public TipBuilder(List<Component> tooltipComponents) {
            this.tooltipComponents = tooltipComponents;
        }
        public TipBuilder add(String transKey, ChatFormatting format, Object... args){
            BSFCommonUtil.addTrans(this.tooltipComponents,transKey,format,args);
            return this;
        }
    }
}
