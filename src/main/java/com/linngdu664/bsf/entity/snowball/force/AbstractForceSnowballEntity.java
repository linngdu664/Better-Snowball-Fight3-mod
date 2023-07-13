package com.linngdu664.bsf.entity.snowball.force;

import com.linngdu664.bsf.entity.AbstractBSFSnowballEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractForceSnowballEntity extends AbstractBSFSnowballEntity {
    public boolean isStart = false;
    private int timer = 0;
//    private Class<? extends Entity> targetClass;
//    private double range;
//    private double GM;
//    private double boundaryR2;

    public AbstractForceSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractForceSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
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
            forceEffect(getTargetClass(), getRange(), getBoundaryR2(), getGM());
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

    abstract double getRange();
    abstract Class<? extends Entity> getTargetClass();
    abstract double getGM();
    abstract double getBoundaryR2();

//    public AbstractForceSnowballEntity setRange(double range) {
//        this.range = range;
//        return this;
//    }
//
//    public AbstractForceSnowballEntity setTargetClass(Class<? extends Entity> targetClass) {
//        this.targetClass = targetClass;
//        return this;
//    }
//
//    public AbstractForceSnowballEntity setGM(double GM) {
//        this.GM = GM;
//        return this;
//    }
//
//    public AbstractForceSnowballEntity setBoundaryR2(double boundaryR2) {
//        this.boundaryR2 = boundaryR2;
//        return this;
//    }
/*
    public void setStart(boolean start) {
        isStart = start;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }*/
}
