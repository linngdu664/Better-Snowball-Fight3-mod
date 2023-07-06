package com.linngdu664.bsf.entity.snowball;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.util.MovingAlgorithm;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractTrackingSnowballEntity extends BSFSnowballEntity {
    private float maxTurningAngleCos;
    private float maxTurningAngleSin;
    private Class<? extends Entity> targetClass;
    private double range;
    private boolean lockFeet;
    private boolean init;

    public AbstractTrackingSnowballEntity(LivingEntity livingEntity, Level level) {
        super(livingEntity, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!init) {
            Vec3 vec3 = this.getDeltaMovement();
            float v0 = (float) vec3.length();
            maxTurningAngleCos = Mth.cos(7.1619724F * v0 * Mth.DEG_TO_RAD);
            maxTurningAngleSin = Mth.sin(7.1619724F * v0 * Mth.DEG_TO_RAD);
            init = true;
        }
        if (!level().isClientSide) {
            MovingAlgorithm.missilesTracking(this, targetClass, range, true, maxTurningAngleCos, maxTurningAngleSin, lockFeet);
        }
    }

    public AbstractTrackingSnowballEntity setRange(double range) {
        this.range = range;
        return this;
    }

    public AbstractTrackingSnowballEntity setTargetClass(Class<? extends Entity> targetClass) {
        this.targetClass = targetClass;
        return this;
    }

    public AbstractTrackingSnowballEntity setLockFeet(boolean lockFeet) {
        this.lockFeet = lockFeet;
        return this;
    }
}
