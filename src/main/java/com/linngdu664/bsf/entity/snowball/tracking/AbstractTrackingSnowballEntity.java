package com.linngdu664.bsf.entity.snowball.tracking;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.util.BSFCommonUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTrackingSnowballEntity extends AbstractBSFSnowballEntity {
    private Entity target;

    public AbstractTrackingSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractTrackingSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(pEntityType, pShooter, pLevel, launchAdjustment);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            missilesTracking();
        }
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            this.discard();
        }
    }

    public double getRange() {
        return 10;
    }

    public abstract Entity getTarget();

    public abstract boolean isLockFeet();

    /**
     * This method is designed for the tracking snowball. If the target is not null,
     * the snowball will ignore gravity and changing the velocity vector to aim the target. If the
     * target disappears or the snowball is too slow, the snowball will restore gravity and stop tracking.
     */
    protected void missilesTracking() {
        Vec3 velocity = getDeltaMovement();
        if (velocity.lengthSqr() < 0.25) {
            setNoGravity(false);
        } else if (target == null || !target.isAlive()) {
            target = getTarget();
            setNoGravity(false);
        } else {
            setNoGravity(true);
            Vec3 delta;
            if (isLockFeet()) {
                delta = target.getPosition(1).subtract(getPosition(1));
            } else {
                delta = target.getBoundingBox().getCenter().subtract(getPosition(1));
            }
            double cosTheta = BSFCommonUtil.vec2AngleCos(delta.x, delta.z, velocity.x, velocity.z);
            if (cosTheta > 1) {
                cosTheta = 1;
            }
            double sinTheta;
            float maxTurningAngleCos = Mth.cos(7.1619724F * (float) velocity.length() * Mth.DEG_TO_RAD);
            float maxTurningAngleSin = Mth.sin(7.1619724F * (float) velocity.length() * Mth.DEG_TO_RAD);
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
            double vNewX = Math.sqrt(BSFCommonUtil.lengthSqr(vx, vz));
            double deltaNewX = Math.sqrt(BSFCommonUtil.lengthSqr(delta.x, delta.z));
            cosTheta = BSFCommonUtil.vec2AngleCos(vNewX, velocity.y, deltaNewX, delta.y);
            if (cosTheta > 1) {
                cosTheta = 1;
            }
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
            push(vx - velocity.x, vy - velocity.y, vz - velocity.z);
        }
    }
}
