package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.TargetGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
        super(EntityRegister.SPECTRAL_SNOWBALL.get(), pShooter, pLevel);
        this.launchAdjustment = launchAdjustment;
    }
//    public SpectralSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
//        super(livingEntity, level);
//        this.setLaunchFrom(launchFunc.getLaunchFrom());
//        launchFunc.launchProperties(this);
//        this.setItem(new ItemStack(ItemRegister.SPECTRAL_SNOWBALL.get()));
//    }
//
//    //This is only used for dispenser
//    public SpectralSnowballEntity(Level level, double x, double y, double z) {
//        super(level, x, y, z);
//        this.setItem(new ItemStack(ItemRegister.SPECTRAL_SNOWBALL.get()));
//    }
//
//    @Override
//    public Item getCorrespondingItem() {
//        return ItemRegister.SPECTRAL_SNOWBALL.get();
//    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        Level level = level();
        if (!level().isClientSide) {
            if (!isCaught) {
                boolean flag = false;
                ((ServerLevel) level).sendParticles( ParticleTypes.FIREWORK, this.getX(), this.getY(), this.getZ(), 60, 0, 0, 0, 0.1);
                List<LivingEntity> list = TargetGetter.getTargetList(this, LivingEntity.class, 2.5);
                for (LivingEntity livingEntity : list) {
                    if (this.distanceTo(livingEntity) < 2.5) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0));
                        flag = true;
                    }
                }
                if (flag) {
                    level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.NEUTRAL, 1F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BELL_RESONATE, SoundSource.NEUTRAL, 1F, 0.66666667F);
                }
            }
            this.discard();
        }
    }

//    @Override
//    protected void onHitEntity(EntityHitResult pResult) {
//        super.onHitEntity(pResult);
//        Entity entity = pResult.getEntity();
//        if (entity instanceof LivingEntity livingEntity && !isCaught) {
//            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
//        }
//    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
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
        return ItemRegister.SPECTRAL_SNOWBALL.get();
    }
}
