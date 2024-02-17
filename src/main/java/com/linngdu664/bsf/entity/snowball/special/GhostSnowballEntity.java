package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Timer", timer);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        timer = pCompound.getInt("Timer");
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!level.isClientSide) {
            Entity entity = pResult.getEntity();
            if (entity instanceof LivingEntity livingEntity && !isCaught) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 160, 1));
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WITHER_SPAWN, SoundSource.NEUTRAL, 0.3F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
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
    public float getSubspacePower() {
        return 2;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.GHOST_SNOWBALL.get();
    }
}
