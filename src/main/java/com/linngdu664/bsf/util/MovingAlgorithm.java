package com.linngdu664.bsf.util;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.entity.BSFSnowballEntity;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MovingAlgorithm {
    /**
     * This method uses the "inverse square-const-zero" model to simulate gravity/repulsion. If we only use inverse-square
     * law, the dt (0.05s) is too long, resulting in huge velocity errors when the distance is short (acceleration is huge),
     * so we limit the acceleration and even force it to be 0 when the distance is shorter than 0.5m to avoid abnormal
     * movements or game crashes.
     *
     * @param snowball    The snowball entity.
     * @param targetClass The class of specific targets.
     * @param range       Only calculate the velocity of targets within the range.
     * @param GM          The magnitude of force has direct ratio with this param.
     * @param boundaryR2  If the square of distance is smaller than this param, the force will be a const and will not follow an inverse-square law.
     * @param <T>         Extends entity class.
     */
    public static <T extends Entity> void forceEffect(BSFSnowballEntity snowball, Class<T> targetClass, double range, double GM, double boundaryR2) {
        List<T> list = TargetGetter.getTargetList(snowball, targetClass, range);
        for (T entity : list) {
            Vec3 rVec = new Vec3(snowball.getX() - entity.getX(), snowball.getY() - entity.getEyeY(), snowball.getZ() - entity.getZ());
            double r2 = rVec.lengthSqr();
            double ir2 = Mth.invSqrt(r2);
            double a;
            if (r2 > boundaryR2) {
                a = GM / r2;
            } else if (r2 > 0.25) {
                a = GM / boundaryR2;
            } else {
                a = 0;
            }
            entity.push(a * rVec.x * ir2, a * rVec.y * ir2, a * rVec.z * ir2);
            //Tell client that player should move because client handles player's movement.
            if (entity instanceof ServerPlayer player && !player.getAbilities().instabuild) {
                player.connection.send(new ClientboundSetEntityMotionPacket(entity));
            }
        }
    }

    /**
     * This method is designed for the tracking snowball. First, it will try to find the nearest available target. Then,
     * if it finds a target, the snowball will ignore gravity and changing the velocity vector to aim the target. If the
     * target disappears or the snowball is too slow, the snowball will restore gravity and try to find a new target.
     *
     * @param snowball           The snowball entity.
     * @param targetClass        The class of specific targets.
     * @param trackingRange      Only tracks targets within the range.
     * @param angleRestriction   Whether only tracks targets within 60 degrees.
     * @param maxTurningAngleCos the cosine of max turning angle per tick.
     * @param maxTurningAngleSin the sine of max turning angle per tick.
     * @param lockFeet           If true, the snowball will track entity's feet to maximum the explosion damage.
     * @param <T>                Extends entity class.
     */
    public static <T extends Entity> void missilesTracking(BSFSnowballEntity snowball, Class<T> targetClass, double trackingRange, boolean angleRestriction, double maxTurningAngleCos, double maxTurningAngleSin, boolean lockFeet) {
        Level level = snowball.level();
        Entity target = TargetGetter.getTarget(snowball, targetClass, angleRestriction, trackingRange);
        if (target == null && targetClass == Player.class) {
            target = TargetGetter.getTarget(snowball, BSFSnowGolemEntity.class, true, trackingRange);
        }
        Vec3 velocity = snowball.getDeltaMovement();
        if (target == null || !target.isAlive() || velocity.lengthSqr() < 0.25) {
            snowball.setNoGravity(false);
        } else if (!level.isClientSide) {
            snowball.setNoGravity(true);
            Vec3 delta;
            if (lockFeet) {
                delta = new Vec3(target.getX() - snowball.getX(), target.getY() - snowball.getY(), target.getZ() - snowball.getZ());
            } else {
                delta = new Vec3(target.getX() - snowball.getX(), target.getEyeY() - snowball.getY(), target.getZ() - snowball.getZ());
            }
            double cosTheta = BSFMthUtil.vec2AngleCos(delta.x, delta.z, velocity.x, velocity.z);
            double sinTheta;
            if (cosTheta < maxTurningAngleCos) {
                cosTheta = maxTurningAngleCos;
                sinTheta = maxTurningAngleSin;
            } else {
                sinTheta = Math.sqrt(1 - cosTheta * cosTheta);
            }
            double vx, vz, vy;
            double d0 = velocity.x * cosTheta - velocity.z * sinTheta;
            double d1 = velocity.x * sinTheta + velocity.z * cosTheta;
            double d2 = velocity.x * cosTheta + velocity.z * sinTheta;
            double d3 = -velocity.x * sinTheta + velocity.z * cosTheta;
            if (d0 * delta.x + d1 * delta.z > d2 * delta.x + d3 * delta.z) {
                vx = d0;
                vz = d1;
            } else {
                vx = d2;
                vz = d3;
            }
            double vNewX = Math.sqrt(BSFMthUtil.modSqr(vx, vz));
            double deltaNewX = Math.sqrt(BSFMthUtil.modSqr(delta.x, delta.z));
            cosTheta = BSFMthUtil.vec2AngleCos(vNewX, velocity.y, deltaNewX, delta.y);
            if (cosTheta < maxTurningAngleCos) {
                cosTheta = maxTurningAngleCos;
                sinTheta = maxTurningAngleSin;
            } else {
                sinTheta = Math.sqrt(1 - cosTheta * cosTheta);
            }
            d0 = vNewX * cosTheta - velocity.y * sinTheta;
            d1 = vNewX * sinTheta + velocity.y * cosTheta;
            d2 = vNewX * cosTheta + velocity.y * sinTheta;
            d3 = -vNewX * sinTheta + velocity.y * cosTheta;
            double adjusted;
            if (d0 * deltaNewX + d1 * delta.y > d2 * deltaNewX + d3 * delta.y) {
                adjusted = d0;
                vy = d1;
            } else {
                adjusted = d2;
                vy = d3;
            }
            vx *= adjusted / vNewX;
            vz *= adjusted / vNewX;
            //Do not directly use "setDeltaMovement" because it will cause the lagging of texture. Use "push" may avoid this.
            snowball.push(vx - velocity.x, vy - velocity.y, vz - velocity.z);
        }
    }
}
