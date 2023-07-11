package com.linngdu664.bsf.entity.snowball;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.entity.MovingAlgorithm;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractForceSnowballEntity extends BSFSnowballEntity {
    public boolean isStart = false;
    private int timer = 0;
    private Class<? extends Entity> targetClass;
    private double range;
    private double GM;
    private double boundaryR2;

    public AbstractForceSnowballEntity(LivingEntity livingEntity, Level level) {
        super(livingEntity, level);
    }

    @Override
    public void tick() {
        Level level = level();
        super.tick();
        if (!level.isClientSide) {
            if (isStart) {
                Vec3 vec3 = this.getDeltaMovement();
                this.push(-vec3.x, -vec3.y, -vec3.z);
                ((ServerLevel) level).sendParticles(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0.06);
                if (timer > 200) {
                    this.discard();
                }
            }
            MovingAlgorithm.forceEffect(this, targetClass, range, GM, boundaryR2);
        }
        timer++;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        isStart = true;
        Vec3 vec3 = this.getDeltaMovement();
        this.push(-vec3.x, -vec3.y, -vec3.z);
        this.setNoGravity(true);
    }

    public AbstractForceSnowballEntity setRange(double range) {
        this.range = range;
        return this;
    }

    public AbstractForceSnowballEntity setTargetClass(Class<? extends Entity> targetClass) {
        this.targetClass = targetClass;
        return this;
    }

    public AbstractForceSnowballEntity setGM(double GM) {
        this.GM = GM;
        return this;
    }

    public AbstractForceSnowballEntity setBoundaryR2(double boundaryR2) {
        this.boundaryR2 = boundaryR2;
        return this;
    }
/*
    public void setStart(boolean start) {
        isStart = start;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }*/
}
