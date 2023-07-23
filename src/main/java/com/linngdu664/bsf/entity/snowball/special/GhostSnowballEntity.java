package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class GhostSnowballEntity extends AbstractBSFSnowballEntity {
    private int timer = 0;

    public GhostSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }

    public GhostSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.GHOST_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
        this.setNoGravity(true);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!level().isClientSide) {
            Entity entity = pResult.getEntity();
            if (entity instanceof LivingEntity livingEntity && !isCaught) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 160, 1));
                level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F / (level().getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            if (timer == 100) {
                discard();
                ((ServerLevel) level).sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0);
            } else {
                timer++;
            }
        }
    }

    @Override
    protected void spawnBasicParticles(Level level) {
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(), this.getZ(), 8, 0, 0, 0, 0);
        }
    }

    @Override
    public boolean canBeCaught() {
        return false;
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
        return 2;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.GHOST_SNOWBALL.get();
    }
}
