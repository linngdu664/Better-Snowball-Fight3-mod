package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.registry.EntityRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpectralSnowballEntity extends AbstractBSFSnowballEntity {
    public SpectralSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SpectralSnowballEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegister.SPECTRAL_SNOWBALL.get(), pX, pY, pZ, pLevel);
    }

    public SpectralSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment) {
        super(EntityRegister.SPECTRAL_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        Level level = level();
        if (!level.isClientSide) {
            if (!isCaught) {
                ((ServerLevel) level).sendParticles(ParticleTypes.FIREWORK, this.getX(), this.getY(), this.getZ(), 60, 0, 0, 0, 0.12);
                List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(4), EntitySelector.NO_SPECTATORS.and(EntitySelector.withinDistance(getX(), getY(), getZ(), 3.5)));
                for (LivingEntity livingEntity : list) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 70, 0));
                }
                if (!list.isEmpty()) {
                    level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.NEUTRAL, 1F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BELL_RESONATE, SoundSource.NEUTRAL, 1F, 0.66666667F);
                }
            }
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.SPECTRAL_SNOWBALL.get();
    }
}
