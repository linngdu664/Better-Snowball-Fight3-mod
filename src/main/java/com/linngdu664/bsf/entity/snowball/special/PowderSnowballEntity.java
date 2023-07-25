package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.ParticleRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PowderSnowballEntity extends AbstractBSFSnowballEntity {
    private boolean isStart = false;
    private int timer = 0;

    public PowderSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PowderSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.POWDER_SNOWBALL.get(), pX, pY, pZ, pLevel);
    }

    public PowderSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.POWDER_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult p_37258_) {
        super.onHitBlock(p_37258_);
        Level level = level();
        isStart = true;
        if (!level.isClientSide) {
            Vec3 vec3 = this.getDeltaMovement();
            this.push(-vec3.x, -vec3.y, -vec3.z);
            this.setNoGravity(true);
            ((ServerLevel) level).sendParticles(ParticleRegister.BIG_LONG_TIME_SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), 25, 0, 0, 0, 0.4);
            level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundRegister.POWDER_SNOWBALL.get(), SoundSource.PLAYERS, 0.3F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        }
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            if (isStart) {
                Vec3 vec3 = this.getDeltaMovement();
                this.push(-vec3.x, -vec3.y, -vec3.z);
                ((ServerLevel) level).sendParticles(ParticleRegister.BIG_LONG_TIME_SNOWFLAKE.get(), this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0.2);
                if (timer > 200) {
                    this.discard();
                }
            }
        }
        timer++;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (isCaught) {
            this.discard();
        }
    }

    @Override
    public boolean canBeCaught() {
    return true;
}

    @Override
    public float getBasicDamage() {
        return Float.MIN_NORMAL;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 3;
    }

    @Override
    public int getBasicWeaknessTicks() {
        return 0;
    }

    @Override
    public int getBasicFrozenTicks() {
        return 0;
    }

    @Override
    public double getBasicPunch() {
        return 0;
    }

    @Override
    public float getSubspacePower() {
        return 1;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.POWDER_SNOWBALL.get();
    }
}
